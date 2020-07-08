package de.cuuky.skywars.clientadapter;

import java.util.ArrayList;

import de.cuuky.cfw.player.clientadapter.BoardUpdateHandler;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.team.SkyWarsTeam;
import de.cuuky.skywars.game.SkyWarsGame;
import de.cuuky.skywars.game.SkyWarsGamestate;

public class CustomBoardUpdateHandler extends BoardUpdateHandler<SkyWarsPlayer> {

	public CustomBoardUpdateHandler(SkyWarsPlayer player) {
		super(player);
	}

	@Override
	public String getScoreboardTitle() {
		return "§e§lSkyWars";
	}

	@Override
	public ArrayList<String> getScoreboardEntries() {
		ArrayList<String> scoreboard = Main.getInstance().getSkyWarsGame().getGameState().getScoreboard();
		SkyWarsTeam sTeam = SkyWarsTeam.getTeam(player);

		for (int i = 0; i < scoreboard.size(); i++) {
			String line = scoreboard.get(i);

			line = line.replace("&", "§");
			line = line.replace("%localKills%", String.valueOf(player.getStats().getKills()));
			line = line.replace("%kills%", String.valueOf(player.getStats().getLocalKills()));
			line = line.replace("%team%", sTeam != null ? sTeam.getName() : "-");
			line = line.replace("%kit%", (player.getSelectedKit() != null ? player.getSelectedKit().getName() : "-"));

			SkyWarsGame game = Main.getInstance().getSkyWarsGame();
			if (game.getGameState() != SkyWarsGamestate.SETUP) {
				line = line.replace("%map%", game.getGameworld().getName());
				line = line.replace("%time%", String.valueOf(Main.getInstance().getSkyWarsGame().getCurrentThread().getTimer()));
				line = line.replace("%remaining%", String.valueOf(SkyWarsPlayer.getAliveSkyWarsPlayer().size()));
			}

			scoreboard.set(i, line);
		}

		return scoreboard;
	}

	@Override
	public String getNametagName() {
		SkyWarsTeam sTeam = SkyWarsTeam.getTeam(player);

		String name = GroupRank.getGroupRank(player.getPlayer()).getSortPriority() + player.getName();
		if (sTeam != null)
			name = name + sTeam.getTeamcolor().getFullColor().replace("§", "");

		return name;
	}

	@Override
	public String getNametagPrefix() {
		SkyWarsTeam sTeam = SkyWarsTeam.getTeam(player);
		return sTeam == null ? GroupRank.getGroupRank(player.getPlayer()).getPrefix() : sTeam.getTeamcolor().getFullColor() + sTeam.getName() + " §8| " + sTeam.getTeamcolor().getFullColor();
	}

	@Override
	public String getNametagSuffix() {
		return Main.getInstance().getSkyWarsGame().getGameState() == SkyWarsGamestate.INGAME ? " §c" + player.getKills() : "";
	}

	@Override
	public String getTablistName() {
		return getNametagName() + player.getName() + getNametagSuffix();
	}
}