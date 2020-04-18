package de.cuuky.skywars.menu.vote;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.loot.SkyWarsLootType;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public class LootVoteMenu extends SuperInventory {
	
	private static ArrayList<LootVoteMenu> inventories;

	static {
		inventories = new ArrayList<>();
	}

	public LootVoteMenu(Player opener) {
		super("§6Loot", opener, 18, true);

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
		linkItemTo(11, new ItemBuilder().displayname("§1Common").itemstack(new ItemStack(Material.APPLE)).lore("§7Votes: §e" + SkyWarsLootType.COMMON.getVotes().size()).build(), new Runnable() {

			@Override
			public void run() {
				SkyWarsLootType.removeAllVotes(swp);
				SkyWarsLootType.COMMON.addVote(swp);
				reopenSoon();
				updateInventories();
			}
		});

		linkItemTo(13, new ItemBuilder().displayname("§6Uncommon").itemstack(new ItemStack(Material.GOLDEN_APPLE)).lore("§7Votes: §e" + SkyWarsLootType.UNCOMMON.getVotes().size()).build(), new Runnable() {

			@Override
			public void run() {
				SkyWarsLootType.removeAllVotes(swp);
				SkyWarsLootType.UNCOMMON.addVote(swp);
				reopenSoon();
				updateInventories();
			}
		});

		linkItemTo(15, new ItemBuilder().displayname("§5Rare").itemstack(new ItemStack(Material.GOLDEN_APPLE)).addEnchantment(Enchantment.DAMAGE_ALL, 1).lore("§7Votes: §e" + SkyWarsLootType.RARE.getVotes().size()).deleteDamageAnnotation().build(), new Runnable() {

			@Override
			public void run() {
				SkyWarsLootType.removeAllVotes(swp);
				SkyWarsLootType.RARE.addVote(swp);
				reopenSoon();
				updateInventories();
			}
		});
		return true;
	}
	
	private static void updateInventories() {
		for(LootVoteMenu menu : new ArrayList<>(inventories))
			menu.updateInventory();
	}
}