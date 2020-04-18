package de.cuuky.skywars.chest.loot;

import java.util.ArrayList;

import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public enum SkyWarsChestType {

	NORMAL, SPEED;
	
	private ArrayList<SkyWarsPlayer> votes;
	private SkyWarsChestType() {
		votes = new ArrayList<>();
	}
	
	public void addVote(SkyWarsPlayer player) {
		votes.add(player);
	}
	
	public void removeVote(SkyWarsPlayer player) {
		votes.remove(player);
	}
	
	public ArrayList<SkyWarsPlayer> getVotes() {
		return votes;
	}
	
	public static void removeAllVotes(SkyWarsPlayer player) {
		for(SkyWarsChestType type : values())
			type.removeVote(player);
	}
}