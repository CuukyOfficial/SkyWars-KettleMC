package de.cuuky.skywars.listener.hit;

import org.bukkit.entity.Player;

public class PlayerHit {
	
	private long timestamp;
	private Player damager, player;
	
	public PlayerHit(Player player, Player damager) {
		this.player = player;
		this.damager = damager;
		this.timestamp = System.currentTimeMillis();
	}
	
	public Player getPlayer() {
		return player;
	}

	public Player getDamager() {
		return damager;
	}
	
	public long getMilliSecondsSince() {
		return System.currentTimeMillis() - timestamp;
	}
}