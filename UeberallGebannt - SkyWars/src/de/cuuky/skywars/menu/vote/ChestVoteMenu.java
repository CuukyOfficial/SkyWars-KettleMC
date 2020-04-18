package de.cuuky.skywars.menu.vote;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.loot.SkyWarsChestType;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public class ChestVoteMenu extends SuperInventory {
	
	private static ArrayList<ChestVoteMenu> inventories;

	static {
		inventories = new ArrayList<>();
	}

	public ChestVoteMenu(Player opener) {
		super("§eChest", opener, 18, true);
		
		this.setModifier = false;
		Main.getInstance().getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
		
		inventories.add(this);
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
		SkyWarsPlayer swp = SkyWarsPlayer.getPlayer(opener);
		linkItemTo(12, new ItemBuilder().displayname("§1Normal").itemstack(new ItemStack(Material.CHEST)).lore("§7Votes §e" + SkyWarsChestType.NORMAL.getVotes().size()).build(), new Runnable() {

			@Override
			public void run() {
				SkyWarsChestType.removeAllVotes(swp);
				SkyWarsChestType.NORMAL.addVote(swp);
				reopenSoon();
				updateInventories();
			}
		});

		linkItemTo(14, new ItemBuilder().displayname("§5Speed").itemstack(new ItemStack(Material.ENDER_CHEST)).lore("§7Votes: §e" + SkyWarsChestType.SPEED.getVotes().size()).build(), new Runnable() {

			@Override
			public void run() {
				SkyWarsChestType.removeAllVotes(swp);
				SkyWarsChestType.SPEED.addVote(swp);
				reopenSoon();
				updateInventories();
			}
		});
		return true;
	}
	
	private static void updateInventories() {
		for(ChestVoteMenu menu : new ArrayList<>(inventories))
			menu.updateInventory();
	}
}