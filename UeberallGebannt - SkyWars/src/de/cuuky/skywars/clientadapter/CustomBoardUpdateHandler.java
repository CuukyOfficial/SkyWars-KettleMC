package de.cuuky.skywars.clientadapter;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.cuuky.minecraftutils.clientadapter.board.BoardUpdateHandler;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.team.SkyWarsTeam;
import de.cuuky.skywars.game.SkyWarsGame;
import de.cuuky.skywars.game.SkyWarsGamestate;

public class CustomBoardUpdateHandler implements BoardUpdateHandler {

	@Override
	public ArrayList<String> getTablistHeader(Player player) {
		return null;
	}

	@Override
	public ArrayList<String> getTablistFooter(Player player) {
		return null;
	}

	@Override
	public String getScoreboardTitle(Player player) {
		return "§e§lSkyWars";
	}

	@Override
	public ArrayList<String> getScoreboardEntries(Player player) {
		ArrayList<String> scoreboard = Main.getInstance().getSkyWarsGame().getGameState().getScoreboard();
		SkyWarsPlayer sPlayer = SkyWarsPlayer.getPlayer(player);
		SkyWarsTeam sTeam = SkyWarsTeam.getTeam(sPlayer);

		for(int i = 0; i < scoreboard.size(); i++) {
			String line = scoreboard.get(i);
			
			line = line.replace("&", "§");
			line = line.replace("%localKills%", String.valueOf(sPlayer.getStats().getKills()));
			line = line.replace("%kills%", String.valueOf(sPlayer.getStats().getLocalKills()));
			line = line.replace("%team%", sTeam != null ? sTeam.getName() : "-");
			line = line.replace("%kit%", (sPlayer.getSelectedKit() != null ? sPlayer.getSelectedKit().getName() : "-"));

			SkyWarsGame game = Main.getInstance().getSkyWarsGame();
			if(game.getGameState() != SkyWarsGamestate.SETUP) {
				line = line.replace("%map%", game.getGameworld().getName());
				line = line.replace("%time%", String.valueOf(Main.getInstance().getSkyWarsGame().getCurrentThread().getTimer()));
				line = line.replace("%remaining%", String.valueOf(SkyWarsPlayer.getAliveSkyWarsPlayer().size()));
			}

			scoreboard.set(i, line);
		}

		return scoreboard;
	}

	@Override
	public boolean showHeartsBelowName(Player player) {
		return false;
	}

	@Override
	public String getNametagName(Player player) {
		SkyWarsPlayer sPlayer = SkyWarsPlayer.getPlayer(player);
		SkyWarsTeam sTeam = SkyWarsTeam.getTeam(sPlayer);

		String name = GroupRank.getGroupRank(player).getSortPriority() + player.getName();
		if(sTeam != null)
			name = name + sTeam.getTeamcolor().getFullColor().replace("§", "");

		return name;
	}

	@Override
	public String getNametagPrefix(Player player) {
		SkyWarsPlayer sPlayer = SkyWarsPlayer.getPlayer(player);
		SkyWarsTeam sTeam = SkyWarsTeam.getTeam(sPlayer);

		return sTeam == null ? GroupRank.getGroupRank(player).getPrefix() : sTeam.getTeamcolor().getFullColor() + sTeam.getName() + " §8| " + sTeam.getTeamcolor().getFullColor();
	}

	@Override
	public String getNametagSuffix(Player player) {
		SkyWarsPlayer sPlayer = SkyWarsPlayer.getPlayer(player);
		return Main.getInstance().getSkyWarsGame().getGameState() == SkyWarsGamestate.INGAME ? " §c" + sPlayer.getKills() : "";
	}

	@Override
	public boolean isNametagVisible(Player player) {
		return false;
	}
}