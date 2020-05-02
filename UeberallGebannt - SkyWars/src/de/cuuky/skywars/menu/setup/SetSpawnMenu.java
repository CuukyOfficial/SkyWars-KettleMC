package de.cuuky.skywars.menu.setup;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.team.color.SkyWarsTeamColor;

public class SetSpawnMenu extends SuperInventory {

	public SetSpawnMenu(Player opener) {
		super("§7Team ausw§hlen", opener, 27, true);

		open();
		
		Main.getInstance().getCuukyFrameWork().getInventoryManager().registerInventory(this);
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		for(int i = 0; i < SkyWarsTeamColor.values().length; i++) {
			SkyWarsTeamColor teamcolor = SkyWarsTeamColor.values()[i];
			ArrayList<String> lore = new ArrayList<>();
			lore.add("§fDerzeitge Spawns:");
			for(Location location : teamcolor.getSpawnLocations().values())
				lore.add("§7Welt: §e" + location.getWorld().getName() + "§8, §7X: §e" + location.getBlockX() + "§8, §7Y: §e" + location.getBlockY() + "§8, §7Z:§e" + location.getBlockZ());

			linkItemTo(i, new ItemBuilder().displayname(teamcolor.getFullColor() + "Team " + teamcolor.getName()).lore(lore).itemstack(new ItemStack(Material.IRON_HELMET)).build(), new Runnable() {

				@Override
				public void run() {
					teamcolor.addSpawnLocation(opener.getLocation());
					opener.sendMessage(Main.getPrefix() + "Spawn erfolgreich gesetzt!");
					closeInventory();
				}
			});
		}

		return true;
	}
}