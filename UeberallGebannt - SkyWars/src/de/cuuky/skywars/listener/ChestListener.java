package de.cuuky.skywars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.SkyWarsChest;
import de.cuuky.skywars.chest.loot.SkyWarsChestType;
import de.cuuky.skywars.game.SkyWarsGamestate;

public class ChestListener implements Listener {

	@EventHandler
	public void onChestOpen(PlayerInteractEvent event) {
		if (Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME || (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK))
			return;

		SkyWarsChest chest = SkyWarsChest.getChest(event.getClickedBlock());
		if (chest == null || Main.getInstance().getSkyWarsGame().getAsIngameThread().getChestType() != SkyWarsChestType.SPEED)
			return;

		chest.explode();
	}
}