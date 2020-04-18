package de.cuuky.skywars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.game.SkyWarsGamestate;

public class CancelListener implements Listener {
	
	private boolean shouldCancel() {
		return Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME && Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.SETUP;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!shouldCancel())
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!shouldCancel())
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(!shouldCancel())
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if(!shouldCancel())
			return;

		event.setFoodLevel(40);
	}

	@EventHandler
	public void onAchievement(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}
}