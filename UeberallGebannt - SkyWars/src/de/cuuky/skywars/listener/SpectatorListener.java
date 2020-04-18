package de.cuuky.skywars.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.game.SkyWarsGamestate;

public class SpectatorListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity entityDamager = event.getDamager();
		Entity entityDamaged = event.getEntity();

		if(cancelEvent(event.getEntity())) {
			if(entityDamager instanceof Arrow) {
				if(((Arrow) entityDamager).getShooter() instanceof Player) {
					Arrow arrow = (Arrow) entityDamager;

					Player shooter = (Player) arrow.getShooter();
					Player damaged = (Player) entityDamaged;

					damaged.teleport(entityDamaged.getLocation().add(0, 5, 0));

					Arrow newArrow = (Arrow) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.ARROW);
					newArrow.setShooter(shooter);
					newArrow.setVelocity(arrow.getVelocity());
					newArrow.setBounce(arrow.doesBounce());

					event.setCancelled(true);
					arrow.remove();
				}
			}
		}

		if(cancelEvent(entityDamager))
			event.setCancelled(true);
	}

	@EventHandler
	public void onEntityTarget(EntityTargetLivingEntityEvent event) {
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME && Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.SETUP)
			event.setCancelled(true);

		if(cancelEvent(event.getTarget()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLose(FoodLevelChangeEvent event) {
		if(cancelEvent(event.getEntity()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onHealthLose(EntityDamageEvent event) {
		if(cancelEvent(event.getEntity()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME && Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.SETUP)
			event.setCancelled(true);

		if(cancelEvent(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryMove(InventoryDragEvent event) {
		if(cancelEvent(event.getWhoClicked()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onItemDrop(PlayerPickupItemEvent event) {
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME && Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.SETUP)
			event.setCancelled(true);

		if(cancelEvent(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onItemPickup(PlayerDropItemEvent event) {
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME)
			event.setCancelled(true);

		if(cancelEvent(event.getPlayer()))
			event.setCancelled(true);
	}

	private static boolean cancelEvent(Entity interact) {
		if(!(interact instanceof Player))
			return false;

		Player player = (Player) interact;

		if(player.getGameMode() != GameMode.ADVENTURE)
			return false;

		return true;
	}
}