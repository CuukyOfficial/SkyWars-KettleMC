package de.cuuky.skywars.game.thread.threads;

import org.bukkit.entity.Player;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.game.SkyWarsGame;
import de.cuuky.skywars.game.SkyWarsGamestate;
import de.cuuky.skywars.game.thread.SkyWarsThread;

public class SkyWarsLobbyThread extends SkyWarsThread {

	public SkyWarsLobbyThread(SkyWarsGame game) {
		super(game);

		this.timer = 60;
	}

	@Override
	protected void doThreadHeartbeat() {
		final int teamAmount = Main.getInstance().getSkyWarsGame().getTeamAmount(), teamSize = Main.getInstance().getSkyWarsGame().getTeamSize(), maxPlayer = teamAmount * teamSize;

		if(maxPlayer == VersionUtils.getOnlinePlayer().size()) {
			if(timer > 16)
				timer = 16;

			timer--;
		} else {
			if(!(teamSize == 1 && teamAmount < 3)) {
				if(((double) ((double) VersionUtils.getOnlinePlayer().size() / (double) maxPlayer)) >= 0.5)
					timer--;
				else
					timer = 60;
			} else if(maxPlayer == VersionUtils.getOnlinePlayer().size())
				timer--;
		}

		for(Player player : VersionUtils.getOnlinePlayer()) {
			player.getPlayer().setExp(0);
			player.getPlayer().setLevel(timer);
		}

		if(timer == 0)
			Main.getInstance().getSkyWarsGame().setState(SkyWarsGamestate.INGAME);
	}
}