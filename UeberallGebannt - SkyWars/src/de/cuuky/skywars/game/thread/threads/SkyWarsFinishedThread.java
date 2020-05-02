package de.cuuky.skywars.game.thread.threads;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.player.SkyWarsPlayerState;
import de.cuuky.skywars.game.SkyWarsGame;
import de.cuuky.skywars.game.thread.SkyWarsThread;

public class SkyWarsFinishedThread extends SkyWarsThread {

	public SkyWarsFinishedThread(SkyWarsGame game) {
		super(game);

		for(SkyWarsPlayer player : SkyWarsPlayer.getOnlineSkyWarsPlayer()) {
			if(player.getState() == SkyWarsPlayerState.ALIVE) {
				player.getStats().addWin();
				player.getStats().addPlayedGame();
			}

			player.getPlayer().setHealth(20);
			player.getPlayer().setFoodLevel(20);
			player.getPlayer().setGameMode(GameMode.SURVIVAL);
			player.getPlayer().setExp(0);
			player.getPlayer().setLevel(0);
			player.getPlayer().teleport(Main.getInstance().getSkyWarsGame().getLobbyLocation());
			player.getPlayer().getInventory().clear();
			player.getPlayer().getInventory().setArmorContents(new ItemStack[] {});

			player.getPlayer().updateInventory();

			for(Player pl : VersionUtils.getOnlinePlayer())
				pl.showPlayer(player.getPlayer());
		}

		timer = 15;
	}

	@Override
	protected void doThreadHeartbeat() {
		timer--;
		
		for(Player player : VersionUtils.getOnlinePlayer()) {
			player.getPlayer().setExp(0);
			player.getPlayer().setLevel(timer);
		}

		if(timer == 0) {
			Bukkit.broadcastMessage(Main.getPrefix() + "Server f§hrt nun herunter...");
			Bukkit.getServer().shutdown();
		} else if(timer % 5 == 0 || timer < 5) 
			Bukkit.broadcastMessage(Main.getPrefix() + "Der Server f§hrt in §e" + timer + " §7Sekunde(n) herunter...");
	}
}