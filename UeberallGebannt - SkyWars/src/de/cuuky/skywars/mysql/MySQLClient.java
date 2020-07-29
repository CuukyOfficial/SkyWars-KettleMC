package de.cuuky.skywars.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.cuuky.skywars.Main;

public class MySQLClient {

	private boolean connected;
	private Connection connection;
	private String host, database, user, password, table;

	public MySQLClient(String host, String database, String table, String user, String password) throws SQLException {
		this.password = password;
		this.user = user;
		this.database = database;
		this.host = host;
		this.table = table;

		connect();
	}

	public void close() {
		if (connection == null)
			return;

		try {
			connection.close();
		} catch (SQLException e) {}

		this.connected = false;
	}

	public void connect() throws SQLException {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", user, password);
			connected = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(Main.getConsolePrefix() + "MYSQL USERNAME, IP ODER PASSWORT FALSCH! -> Disabled");
			throw e;
		}
	}

	public ResultSet getQuery(String qry) {
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(Main.getConsolePrefix() + "Connection to MySQL-Database lost!");
			try {
				connect();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return rs;
	}

	public void update(String qry) {
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(Main.getConsolePrefix() + "Connection to MySQL-Database lost!");
			try {
				connect();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getStatsTable() {
		return table;
	}

	public boolean isConnected() {
		return connected;
	}
}