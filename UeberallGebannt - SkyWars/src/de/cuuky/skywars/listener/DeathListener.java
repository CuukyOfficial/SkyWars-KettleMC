package de.cuuky.skywars.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.player.SkyWarsPlayerState;
import de.cuuky.skywars.entity.team.SkyWarsTeam;
import de.cuuky.skywars.game.SkyWarsGamestate;
import de.cuuky.skywars.listener.hit.PlayerHit;
import de.cuuky.skywars.utils.SkyWarsItemUtils;

public class DeathListener implements Listener {

	private HashMap<Player, PlayerHit> hits;

	public DeathListener() {
		hits = new HashMap<>();
	}

	private void playerDeath(SkyWarsPlayer player) {
		player.getStats().addDeath();
		player.getStats().addPlayedGame();
		player.setState(SkyWarsPlayerState.SPECTATOR);
		SkyWarsTeam.getTeam(player).checkIfDead(true);

		if(Main.getInstance().getSkyWarsGame().getGameState() == SkyWarsGamestate.INGAME) {
			player.getPlayer().setVelocity(new Vector(2, 1, 2));

			for(ItemStack stack : player.getPlayer().getInventory().getContents())
				if(stack != null && stack.getType() != Material.AIR)
					player.getPlayer().getWorld().dropItemNaturally(player.getPlayer().getLocation(), stack);

			for(ItemStack stack : player.getPlayer().getInventory().getArmorContents())
				if(stack != null && stack.getType() != Material.AIR)
					player.getPlayer().getWorld().dropItemNaturally(player.getPlayer().getLocation(), stack);

			SkyWarsItemUtils.giveSpectatorItems(player.getPlayer());
		}
	}
	
	private void killedBy(SkyWarsPlayer player, SkyWarsPlayer killer) {
		SkyWarsTeam team = SkyWarsTeam.getTeam(player);
		SkyWarsTeam killerTeam = SkyWarsTeam.getTeam(killer);
		killer.getStats().addKill();

		Bukkit.broadcastMessage(Main.getPrefix() + team.getTeamcolor().getFullColor() + player.getName() + " §7wurde von " + killerTeam.getTeamcolor().getFullColor() + killer.getName() + "§7 getötet!");
		playerDeath(player);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
	}

	@EventHandler
	public void onPlayerDeathByPlayer(EntityDamageByEntityEvent event) {
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME || !(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
			return;

		SkyWarsPlayer player = SkyWarsPlayer.getPlayer((Player) event.getEntity());
		if((VersionUtils.getHearts(player.getPlayer()) - event.getFinalDamage()) > 0) {
			hits.put((Player) event.getEntity(), new PlayerHit((Player) event.getEntity(), (Player) event.getDamager()));
			return;
		}

		killedBy(player, SkyWarsPlayer.getPlayer((Player) event.getEntity()));
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.INGAME || !(event.getEntity() instanceof Player))
			return;

		SkyWarsPlayer player = SkyWarsPlayer.getPlayer((Player) event.getEntity());
		SkyWarsTeam team = SkyWarsTeam.getTeam(player);
		if((VersionUtils.getHearts(player.getPlayer()) - event.getFinalDamage()) > 0)
			return;

		PlayerHit hit = hits.get((Player) event.getEntity());
		if(hit != null) {
			if(hit.getMilliSecondsSince() <= 3000) {
				killedBy(player, SkyWarsPlayer.getPlayer(hit.getDamager()));
				return;
			}
		}
		
		Bukkit.broadcastMessage(Main.getPrefix() + team.getTeamcolor().getFullColor() + player.getName() + " §7ist gestorben!");
		playerDeath(player);
	}
}