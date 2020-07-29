package de.cuuky.skywars.entity.player.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.configuration.SkyWarsConfiguration;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.mysql.MySQLClient;

public class SkyWarsPlayerStats {

	private static MySQLClient mysqlClient;

	private SkyWarsPlayer player;
	private int kills, deaths, playedGames, wins;
	private int localKills;

	public SkyWarsPlayerStats(SkyWarsPlayer player) {
		this.player = player;

		loadMySQLStats();
	}

	private double round(double dou) {
		return Math.round(dou * 100D) / 100D;
	}

	private void loadMySQLStats() {
		ResultSet rs = mysqlClient.getQuery("SELECT * FROM " + mysqlClient.getStatsTable() + " WHERE uuid='" + this.player.getUUID() + "'");

		try {
			while (rs.next()) {
				this.kills = rs.getInt("kills");
				this.deaths = rs.getInt("deaths");
				this.playedGames = rs.getInt("playedGames");
				this.wins = rs.getInt("wins");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveMySQLStats() {
		ResultSet rs = mysqlClient.getQuery("SELECT * FROM " + mysqlClient.getStatsTable() + " WHERE uuid='" + this.player.getUUID() + "'");

		try {
			if (!rs.next())
				mysqlClient.update("INSERT INTO " + mysqlClient.getStatsTable() + " (uuid, kills, deaths, playedGames, wins) VALUES ('" + this.player.getUUID() + "', " + (kills + localKills) + ", " + deaths + ", " + playedGames + ", " + wins + ");");
			else
				mysqlClient.update("UPDATE " + mysqlClient.getStatsTable() + " SET kills='" + (kills + localKills) + "', deaths='" + deaths + "', playedGames='" + playedGames + "', wins='" + this.wins + "' WHERE uuid='" + this.player.getUUID() + "';");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SkyWarsPlayer getPlayer() {
		return player;
	}

	public void addKill() {
		this.localKills++;
	}

	public int getKills() {
		return kills;
	}

	public void addDeath() {
		this.deaths++;
	}

	public int getDeaths() {
		return deaths;
	}

	public void addPlayedGame() {
		this.playedGames++;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public void addWin() {
		this.wins++;
	}

	public int getWins() {
		return wins;
	}

	public int getLocalKills() {
		return localKills;
	}

	public double getKDR() {
		return round(deaths == 0 ? this.kills + this.localKills : ((double) (this.kills + this.localKills) / this.deaths));
	}

	public double getWinChance() {
		return round(wins == 0 ? 0 : ((double) (this.wins) / this.playedGames));
	}

	public static void loadMySQL() {
		try {
			SkyWarsConfiguration configuration = Main.getInstance().getSkyWarsGame().getConfig();
			mysqlClient = new MySQLClient(configuration.getString("mysql.host", "localhost"), configuration.getString("mysql.database", "playerstats"), configuration.getString("mysql.table", "skywars"), configuration.getString("mysql.user", "root"), configuration.getString("mysql.password", ""));

			if (mysqlClient.isConnected())
				mysqlClient.update("CREATE TABLE IF NOT EXISTS " + mysqlClient.getStatsTable() + "(uuid VARCHAR(36) NOT NULL, kills int, deaths int, playedGames int, wins int, PRIMARY KEY (uuid));");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}