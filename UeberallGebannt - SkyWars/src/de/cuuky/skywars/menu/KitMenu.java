package de.cuuky.skywars.menu;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.minecraftutils.item.ItemBuilder;
import de.cuuky.minecraftutils.menu.SuperInventory;
import de.cuuky.minecraftutils.menu.utils.PageAction;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.kit.SkyWarsKit;

public class KitMenu extends SuperInventory {

	public KitMenu(Player opener) {
		super("§eKits", opener, 54, true);

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
		SkyWarsPlayer player = SkyWarsPlayer.getPlayer(opener);
		ArrayList<SkyWarsKit> boughtItems = SkyWarsKit.getAvialableKitsFor(player);

		int slot = 0;
		for(SkyWarsKit boughtKit : boughtItems) {
			linkItemTo(slot, new ItemBuilder().itemstack(boughtKit.getIcon()).lore("§aIn Besitz").build(), new Runnable() {

				@Override
				public void run() {
					player.setSelectedKit(boughtKit);
					closeInventory();
				}
			});

			slot++;
		}

		for(SkyWarsKit kit : SkyWarsKit.getKits()) {
			if(boughtItems.contains(kit))
				continue;

			linkItemTo(slot, new ItemBuilder().itemstack(kit.getIcon()).lore("§cNicht in Besitz").build(), null);
			slot++;
		}
		return true;
	}
}