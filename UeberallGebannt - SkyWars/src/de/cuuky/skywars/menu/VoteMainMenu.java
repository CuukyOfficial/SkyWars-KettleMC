package de.cuuky.skywars.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.minecraftutils.item.ItemBuilder;
import de.cuuky.minecraftutils.menu.SuperInventory;
import de.cuuky.minecraftutils.menu.utils.PageAction;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.menu.vote.ChestVoteMenu;
import de.cuuky.skywars.menu.vote.LootVoteMenu;

public class VoteMainMenu extends SuperInventory {

	public VoteMainMenu(Player opener) {
		super("§5Vote", opener, 18, true);

		this.setModifier = false;
		
		Main.getInstance().getMinecraftUtils().getInventoryManager().registerInventory(this);
		open();
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
		linkItemTo(12, new ItemBuilder().displayname("§eChests").itemstack(new ItemStack(Material.CHEST)).build(), new Runnable() {

			@Override
			public void run() {
				new ChestVoteMenu(opener);
			}
		});

		linkItemTo(14, new ItemBuilder().displayname("§6Loot").itemstack(new ItemStack(Material.GOLDEN_APPLE)).build(), new Runnable() {

			@Override
			public void run() {
				new LootVoteMenu(opener);
			}
		});
		return true;
	}
}