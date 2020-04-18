package de.cuuky.skywars.kit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.cuuky.minecraftutils.item.ItemBuilder;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public class SkyWarsKit implements ConfigurationSerializable {

	private static ArrayList<SkyWarsKit> kits;

	static {
		kits = new ArrayList<>();
	}

	private int id;
	private String name, permission;
	private ItemStack icon;
	private HashMap<String, ItemStack> inventory;
	private ArrayList<ItemStack> armor;
	private HashMap<PotionEffectType, Integer> potioneffects;

	private SkyWarsKit(int id, String name, String permission, ItemStack icon, HashMap<String, ItemStack> inventory, ArrayList<ItemStack> armor, HashMap<PotionEffectType, Integer> potionEffects) {
		this.id = id;
		this.name = name;
		this.permission = permission;
		this.icon = icon;
		this.inventory = inventory;
		this.armor = armor;
		this.potioneffects = potionEffects;

		kits.add(this);
	}

	public SkyWarsKit(String name, String permission, Player player) {
		this.id = generateId();
		this.name = name;
		this.permission = permission;
		this.icon = new ItemBuilder().displayname("§4No-Icon").material(Material.BAKED_POTATO).build();

		loadInventory(player);
		loadPotionEffects(player.getActivePotionEffects());

		kits.add(this);
	}

	private int generateId() {
		int id = kits.size();
		while(getKit(id) != null)
			id++;

		return id;
	}

	private void loadInventory(Player player) {
		this.inventory = new HashMap<>();
		this.armor = new ArrayList<>();

		for(int i = 0; i < player.getInventory().getSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if(stack != null && stack.getType() != Material.AIR)
				this.inventory.put(String.valueOf(i), stack);
		}

		for(ItemStack l : player.getInventory().getArmorContents())
			armor.add(l);
	}

	private void loadPotionEffects(Collection<PotionEffect> effects) {
		this.potioneffects = new HashMap<>();

		for(PotionEffect effect : effects)
			this.potioneffects.put(effect.getType(), effect.getAmplifier());
	}

	public void remove() {
		kits.remove(this);
	}

	public void restoreInventory(Player player) {
		player.getInventory().clear();

		try {
			for(String slot : this.inventory.keySet())
				player.getInventory().setItem(Integer.valueOf(slot), this.inventory.get(slot));
		} catch(ArrayIndexOutOfBoundsException e) {}

		ItemStack[] armorc = new ItemStack[4];
		for(int i = 0; i < armor.size(); i++)
			armorc[i] = armor.get(i);
		player.getInventory().setArmorContents(armorc);
		
		for(PotionEffectType type : this.potioneffects.keySet()) 
			player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, potioneffects.get(type)));

		player.updateInventory();
	}

	public boolean canUse(SkyWarsPlayer player) {
		if(this.permission == null || this.permission.isEmpty())
			return true;

		return player.getPlayer().hasPermission(this.permission);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name.replace("&", "§");
	}

	public String getPermission() {
		return permission;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	public ItemStack getIcon() {
		return new ItemBuilder().itemstack(icon).displayname(getName()).build();
	}

	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> serialize = new LinkedHashMap<>();

		serialize.put("id", this.id);
		serialize.put("name", this.name);
		serialize.put("permission", this.permission);
		serialize.put("icon", this.icon);
		serialize.put("inventory", this.inventory);
		serialize.put("armor", this.armor);
		serialize.put("potioneffects", this.potioneffects);

		return serialize;
	}

	@SuppressWarnings("unchecked")
	public static SkyWarsKit deserialize(Map<String, Object> args) {
		return new SkyWarsKit((int) args.get("id"), (String) args.get("name"), (String) args.get("permission"), (ItemStack) args.get("icon"), (HashMap<String, ItemStack>) args.get("inventory"), (ArrayList<ItemStack>) args.get("armor"), (HashMap<PotionEffectType, Integer>) args.get("potioneffects"));
	}

	public static SkyWarsKit getKit(int id) {
		for(SkyWarsKit kit : kits)
			if(kit.getId() == id)
				return kit;

		return null;
	}

	public static ArrayList<SkyWarsKit> getAvialableKitsFor(SkyWarsPlayer player) {
		ArrayList<SkyWarsKit> availableKits = new ArrayList<>();
		for(SkyWarsKit kit : kits) {
			if(!kit.canUse(player))
				continue;

			availableKits.add(kit);
		}

		return availableKits;
	}

	public static ArrayList<SkyWarsKit> getKits() {
		return kits;
	}
}