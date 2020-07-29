package de.cuuky.skywars.chest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.serialization.CompatibleLocation;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.skywars.chest.loot.SkyWarsLootType;

public class SkyWarsChest implements ConfigurationSerializable {

	private static ArrayList<SkyWarsChest> chests;

	static {
		chests = new ArrayList<>();
	}

	private int id;
	private Location location;

	private SkyWarsChest(int id, Location location) {
		this.id = id;
		this.location = location;

		if (getBlock() == null || !(getBlock().getState() instanceof Chest))
			return;

		chests.add(this);
	}

	public SkyWarsChest(Location location) {
		this.id = generateId();
		this.location = location;

		chests.add(this);
	}

	private int generateId() {
		int id = chests.size();
		while (getChest(id) != null)
			id++;

		return id;
	}

	public void fillChest(SkyWarsLootType type) {
		try {
			Block block = getBlock();
			Chest chest = (Chest) block.getState();

			chest.getInventory().clear();
			int toFill = chest.getInventory().getSize() * JavaUtils.randomInt(28, 45) / 100;
			for (int i = 0; i < toFill; i++) {
				int slot = JavaUtils.randomInt(0, chest.getInventory().getSize() - 1);
				while (chest.getInventory().getItem(slot) != null)
					slot = JavaUtils.randomInt(0, chest.getInventory().getSize() - 1);

				SkyWarsLootType setType = i >= toFill / 2 ? SkyWarsLootType.BASIC : type;
				chest.getInventory().setItem(slot, setType.getRandomItem());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void explode() {
		try {
			Block block = getBlock();
			Chest chest = (Chest) block.getState();
			for (ItemStack stack : chest.getInventory().getContents())
				if (stack != null && stack.getType() != Material.AIR)
					location.getWorld().dropItemNaturally(location, stack);

			location.getWorld().playEffect(location, Effect.EXPLOSION_LARGE, 2);

			block.setType(Material.AIR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove() {
		chests.remove(this);
	}

	public Block getBlock() {
		return location.getBlock();
	}

	public int getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> serialize = new LinkedHashMap<>();

		serialize.put("id", this.id);
		serialize.put("location", new CompatibleLocation(this.location));

		return serialize;
	}

	public static SkyWarsChest deserialize(Map<String, Object> args) {
		return new SkyWarsChest((int) args.get("id"), ((CompatibleLocation) args.get("location")).getLocation());
	}

	public static SkyWarsChest getChest(Block block) {
		for (SkyWarsChest swc : chests)
			if (swc.getBlock().equals(block))
				return swc;

		return null;
	}

	public static SkyWarsChest getChest(int id) {
		for (SkyWarsChest swc : chests)
			if (swc.getId() == id)
				return swc;

		return null;
	}

	public static ArrayList<SkyWarsChest> getChests() {
		return chests;
	}
}