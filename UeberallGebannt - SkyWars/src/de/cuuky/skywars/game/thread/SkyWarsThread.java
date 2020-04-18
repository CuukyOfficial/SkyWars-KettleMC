package de.cuuky.skywars.game.thread;

import org.bukkit.Bukkit;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.game.SkyWarsGame;

public abstract class SkyWarsThread implements Runnable {

	protected SkyWarsGame game;
	protected int scheduler, timer;

	public SkyWarsThread(SkyWarsGame game) {
		this.game = game;
		this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this, 20, 20);
	}

	private void doHeartbeat() {
		for(SkyWarsPlayer swp : SkyWarsPlayer.getOnlineSkyWarsPlayer())
			swp.update();
	}

	public int getScheduler() {
		return scheduler;
	}

	public void cancelScheduler() {
		Bukkit.getScheduler().cancelTask(scheduler);
	}

	@Override
	public void run() {
		doHeartbeat();
		doThreadHeartbeat();
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getTimer() {
		return timer;
	}

	protected abstract void doThreadHeartbeat();
}