package de.cuuky.skywars.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.loot.SkyWarsChestType;
import de.cuuky.skywars.chest.loot.SkyWarsLootType;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.player.SkyWarsPlayerState;
import de.cuuky.skywars.entity.team.SkyWarsTeam;
import de.cuuky.skywars.utils.SkyWarsItemUtils;

public class PlayerHandleListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (VersionUtils.getOnlinePlayer().size() >= (Main.getInstance().getSkyWarsGame().getTeamAmount() * Main.getInstance().getSkyWarsGame().getTeamSize())) {
			event.setKickMessage("§7The server is full!");
			event.setResult(Result.KICK_FULL);
			return;
		}

		Player player = event.getPlayer();
		switch (Main.getInstance().getSkyWarsGame().getGameState()) {
		case SETUP:
			if (!player.hasPermission("skywars.setup"))
				event.disallow(Result.KICK_OTHER, "§4Server wird noch aufgesetzt!");
			break;
		case FINISHED:
			event.disallow(Result.KICK_OTHER, "§7Das Spiel ist bereits vor§ber!");
			break;
		default:
			break;
		}

		SkyWarsPlayer mp = SkyWarsPlayer.getPlayer(player);
		if (mp == null)
			mp = new SkyWarsPlayer(player.getUniqueId().toString());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SkyWarsPlayer mp = SkyWarsPlayer.getPlayer(player);

		mp.setPlayer(event.getPlayer());
		event.setJoinMessage(null);

		switch (Main.getInstance().getSkyWarsGame().getGameState()) {
		case SETUP:
			event.setJoinMessage("§6" + mp.getName() + " §7hat das Spiel betreten");

			player.setGameMode(GameMode.CREATIVE);
			SkyWarsItemUtils.giveSetupItems(player);
			break;
		case LOBBY:
			player.setGameMode(GameMode.SURVIVAL);
			SkyWarsItemUtils.giveLobbyItems(player);

			player.getPlayer().teleport(Main.getInstance().getSkyWarsGame().getLobbyLocation());
			break;
		case INGAME:
			SkyWarsItemUtils.giveSpectatorItems(player);

			player.getPlayer().teleport(SkyWarsPlayer.getAliveSkyWarsPlayer().get(0).getPlayer().getLocation());
			break;
		default:
			break;
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		SkyWarsPlayer mp = SkyWarsPlayer.getPlayer(player);
		SkyWarsTeam team = SkyWarsTeam.getTeam(mp);

		event.setQuitMessage(null);

		for (Player pl : VersionUtils.getOnlinePlayer())
			pl.showPlayer(player);

		switch (Main.getInstance().getSkyWarsGame().getGameState()) {
		case LOBBY:
			event.setQuitMessage("§6" + mp.getName() + " §7hat das Spiel verlassen");
			SkyWarsLootType.removeAllVotes(mp);
			SkyWarsChestType.removeAllVotes(mp);

			if (team != null)
				team.removePlayer(mp);
			break;
		case INGAME:
			if (mp.getState() == SkyWarsPlayerState.ALIVE) {
				mp.setState(SkyWarsPlayerState.DEAD);
				mp.getStats().addDeath();
				mp.getStats().addPlayedGame();
				team.checkIfDead(true);
			}
			
			mp.getStats().saveMySQLStats();
			break;
		default:
			break;
		}

		player.setFlying(false);
		player.setAllowFlight(false);
		player.setExp(0);
		player.setLevel(0);
		player.setHealth(20);
		player.setFoodLevel(20);
		mp.setPlayer(null);
	}
}