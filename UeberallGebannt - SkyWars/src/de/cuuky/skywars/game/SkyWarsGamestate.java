package de.cuuky.skywars.game;

import java.util.ArrayList;
import java.util.Collections;

import de.cuuky.cfw.utils.JavaUtils;

public enum SkyWarsGamestate {

	SETUP(new String[] {"§f", "§fSETUP", "§e"}), 
	
	LOBBY(new String[] {"§f", 
			"&fMap:",
			" &6%map%", 
			"§e", 
			"&fKit:", 
			" &6%kit%"}), 
	
	INGAME(new String[] {"&f",
			"&fMap:",
			" &6%map%", 
			"&e",
			"&fVerbleibend:", 
			" &6%remaining%", 
			"&1", 
			"&fKills:", 
			" &6%kills%"}), 
	
	FINISHED(new String[] {"§f", 
			"&fMap:",
			" &6%map%", 
			"§e",
			"&fSpielzeit:",
			" &6%time%",
			"§1",
			"&fKills:",
			" &6%kills%"});
	
	private ArrayList<String> scoreboard;
	private SkyWarsGamestate(String[] scoreboard) {
		this.scoreboard = JavaUtils.collectionToArray(scoreboard);
		
		Collections.reverse(this.scoreboard);
	}
	
	public ArrayList<String> getScoreboard() {
		return new ArrayList<>(scoreboard);
	}
}