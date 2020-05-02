package de.cuuky.skywars.entity.team.color;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import de.cuuky.skywars.Main;

public enum SkyWarsTeamColor {

	RED("Rot", ChatColor.RED),
	YELLOW("Gelb", ChatColor.YELLOW),
	GREEN("Gr§n", ChatColor.GREEN),
	BLUE("Blau", ChatColor.BLUE),
	ORANGE("Orange", ChatColor.GOLD),
	LIGHT_BLUE("Hellblau", ChatColor.AQUA),
	PINK("Pink", ChatColor.LIGHT_PURPLE),
	AQUA("Aqua", ChatColor.DARK_AQUA),
	DARK_RED("Dunkelrot", ChatColor.DARK_RED),
	DARK_GREEN("Dunkelgr§n", ChatColor.DARK_GREEN),
	DARK_BLUE("Dunkelblau", ChatColor.DARK_BLUE),
	PURPLE("Lila", ChatColor.DARK_PURPLE),
	BLACK("Schwarz", ChatColor.BLACK),
	GRAY("Grau", ChatColor.GRAY),
	WHITE("Wei§", ChatColor.WHITE),
	DARK_GRAY("Dunkelgrau", ChatColor.DARK_GRAY);

	private String name;
	private ChatColor color;
	private HashMap<World, Location> spawnLocations;

	private SkyWarsTeamColor(String name, ChatColor color) {
		this.name = name;
		this.color = color;
		this.spawnLocations = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public String getFullColor() {
		return "§" + color.getChar();
	}

	public void addSpawnLocation(Location location) {
		this.spawnLocations.put(location.getWorld(), location);
	}

	public HashMap<World, Location> getSpawnLocations() {
		return spawnLocations;
	}

	public static ArrayList<SkyWarsTeamColor> getAvailableTeamColors() {
		ArrayList<SkyWarsTeamColor> available = new ArrayList<>();
		for(SkyWarsTeamColor color : values())
			if(color.getSpawnLocations().containsKey(Main.getInstance().getSkyWarsGame().getGameworld()))
				available.add(color);

		return available;
	}

	public static SkyWarsTeamColor getTeamColor(String name) {
		for(SkyWarsTeamColor color : values())
			if(color.toString().equals(name))
				return color;

		return null;
	}
}