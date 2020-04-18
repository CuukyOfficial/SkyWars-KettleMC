package de.cuuky.skywars.entity.team;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.SkyWarsEntity;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.player.SkyWarsPlayerState;
import de.cuuky.skywars.entity.team.color.SkyWarsTeamColor;
import de.cuuky.skywars.game.SkyWarsGamestate;
import de.cuuky.skywars.menu.TeamMenu;

public class SkyWarsTeam implements SkyWarsEntity {

	private static ArrayList<SkyWarsTeam> teams;

	static {
		teams = new ArrayList<>();
	}

	private SkyWarsTeamColor color;
	private ArrayList<SkyWarsPlayer> players;

	public SkyWarsTeam(int id) {
		this.color = SkyWarsTeamColor.values()[id];
		this.players = new ArrayList<>();

		teams.add(this);
	}

	public boolean checkIfDead(boolean checkWin) {
		for(SkyWarsPlayer player : players)
			if(player.getState() == SkyWarsPlayerState.ALIVE)
				return false;

		if(checkWin) {
			Bukkit.broadcastMessage(Main.getPrefix() + "§7Team " + this.color.getFullColor() + this.color.getName() + " §7wurde ausgelöscht!");
			Main.getInstance().getSkyWarsGame().checkForWin();
		}

		return true;
	}

	public boolean isFull() {
		return players.size() >= Main.getInstance().getSkyWarsGame().getTeamSize();
	}

	@Override
	public int getKills() {
		int kills = 0;
		for(SkyWarsPlayer player : players)
			kills += player.getKills();

		return kills;
	}

	@Override
	public String getName() {
		return color.getName();
	}

	public SkyWarsTeamColor getTeamcolor() {
		return color;
	}

	public void addPlayer(SkyWarsPlayer player) {
		this.players.add(player);

		TeamMenu.updateInventories();
	}

	public void removePlayer(SkyWarsPlayer player) {
		this.players.remove(player);

		checkIfDead(Main.getInstance().getSkyWarsGame().getGameState() == SkyWarsGamestate.INGAME);
	}

	public ArrayList<SkyWarsPlayer> getPlayers() {
		return players;
	}

	public static void clearTeams() {
		teams.clear();
	}

	public static SkyWarsTeam getTeam(SkyWarsPlayer player) {
		for(SkyWarsTeam team : teams)
			if(team.getPlayers().contains(player))
				return team;

		return null;
	}

	public static ArrayList<SkyWarsTeam> getTeams() {
		return teams;
	}
}