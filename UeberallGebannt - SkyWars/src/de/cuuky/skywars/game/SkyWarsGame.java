package de.cuuky.skywars.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.SkyWarsChest;
import de.cuuky.skywars.chest.SkyWarsChestHandler;
import de.cuuky.skywars.configuration.SkyWarsConfiguration;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.team.SkyWarsTeam;
import de.cuuky.skywars.entity.team.color.SkyWarsTeamColorHandler;
import de.cuuky.skywars.game.thread.SkyWarsThread;
import de.cuuky.skywars.game.thread.threads.SkyWarsFinishedThread;
import de.cuuky.skywars.game.thread.threads.SkyWarsIngameThread;
import de.cuuky.skywars.game.thread.threads.SkyWarsLobbyThread;
import de.cuuky.skywars.kit.SkyWarsKit;
import de.cuuky.skywars.kit.SkyWarsKitHandler;
import de.cuuky.skywars.menu.TeamMenu;

public class SkyWarsGame {

	private int teamAmount, teamSize;
	private Location lobbyLocation;
	private ArrayList<World> gameworlds;
	private World gameworld;

	private SkyWarsKitHandler kitHandler;
	private SkyWarsTeamColorHandler teamcolorHandler;
	private SkyWarsChestHandler chestHandler;

	private SkyWarsConfiguration config;
	private SkyWarsGamestate state;
	private SkyWarsThread currentThread;

	public SkyWarsGame() {
		loadData();

		if(lobbyLocation != null && SkyWarsKit.getKits().size() != 0 && SkyWarsChest.getChests().size() != 0)
			setState(SkyWarsGamestate.LOBBY);
		else
			setState(SkyWarsGamestate.SETUP);
	}

	@SuppressWarnings("unchecked")
	private void loadData() {
		this.config = new SkyWarsConfiguration();
		this.teamAmount = config.getInt("game.teams.amount", 4);
		this.teamSize = config.getInt("game.teams.size", 2);
		this.lobbyLocation = config.getValue("game.lobbyLocation", "nullReplace") instanceof String ? null : (Location) config.getValue("game.lobbyLocation", "nullReplace");
		
		ArrayList<String> gameworlds = new ArrayList<>();
		gameworlds.add("testmap");
		gameworlds = (ArrayList<String>) this.config.getValue("game.worlds", gameworlds);
		
		this.gameworlds = new ArrayList<>();
		for(String worldname : gameworlds) 
			this.gameworlds.add(Bukkit.getWorld(worldname) != null ? Bukkit.getWorld(worldname) : Bukkit.createWorld(new WorldCreator(worldname)));

		this.kitHandler = new SkyWarsKitHandler();
		this.teamcolorHandler = new SkyWarsTeamColorHandler();
		this.chestHandler = new SkyWarsChestHandler();

		if(lobbyLocation == null)
			return;

		prepareWorld(this.lobbyLocation.getWorld());
		try {
			ArrayList<World> worlds = this.gameworlds;
			World gameworld = null;
			if(worlds.size() > 1)
				gameworld = worlds.get(JavaUtils.randomInt(0, worlds.size() - 1));
			else
				gameworld = worlds.get(0);

			setGameworld(gameworld);
		} catch(Throwable e) {
			e.printStackTrace();
			System.out.println(Main.getConsolePrefix() + "Failed to load any GameWorld!");
			Bukkit.getServer().shutdown();
		}
	}

	private void prepareWorld(World world) {
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setTime(1000);
		world.setThundering(false);
		world.setStorm(false);
		world.setWeatherDuration(Integer.MAX_VALUE);
	}

	public void checkForWin() {
		SkyWarsTeam alive = null;
		for(SkyWarsTeam teams : SkyWarsTeam.getTeams()) {
			if(teams.checkIfDead(false))
				continue;

			if(alive == null)
				alive = teams;
			else
				return;
		}

		Bukkit.broadcastMessage(Main.getPrefix() + "§7Das Team " + alive.getTeamcolor().getFullColor() + alive.getName() + " §7hat §eSkyWars §7gewonnen!");
		setState(SkyWarsGamestate.FINISHED);
	}

	public void saveData() {
		this.kitHandler.save();
		this.teamcolorHandler.save();
		this.chestHandler.save();
		
		for(SkyWarsPlayer player : SkyWarsPlayer.getOnlineSkyWarsPlayer())
			player.getStats().saveMySQLStats();
	}

	public void setState(SkyWarsGamestate state) {
		if(this.state == state)
			return;

		this.state = state;

		if(currentThread != null)
			this.currentThread.cancelScheduler();

		switch(state) {
		case SETUP:
			this.currentThread = new SkyWarsThread(this) {

				@Override
				protected void doThreadHeartbeat() {
					timer++;

					if(timer % 120 == 0 || timer == 10)
						Bukkit.broadcastMessage(Main.getPrefix() + "Der Server befindet sich im Setup-Modus, da fehlende Einstellungen vorhanden sind!");
				}
			};
			break;
		case LOBBY:
			this.currentThread = new SkyWarsLobbyThread(this);
			break;
		case INGAME:
			this.currentThread = new SkyWarsIngameThread(this);
			break;
		case FINISHED:
			this.currentThread = new SkyWarsFinishedThread(this);
			break;
		}
	}

	public int getTeamAmount() {
		return teamAmount;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setLobbyLocation(Location lobbyLocation) {
		this.lobbyLocation = lobbyLocation;

		this.config.setValue("game.lobbyLocation", lobbyLocation);
		this.config.save();
	}

	public Location getLobbyLocation() {
		return lobbyLocation;
	}

	public void setGameworld(World gameworld) {
		if(this.gameworld != null)
			Bukkit.broadcastMessage(Main.getPrefix() + "Die Map wurde geändert!");

		this.gameworld = gameworld;

		prepareWorld(gameworld);
		if(this.teamAmount > 16) {
			System.out.println(Main.getConsolePrefix() + "Amount of teams can't be higher than 16!");
			Bukkit.getServer().shutdown();
			return;
		}

		SkyWarsTeam.clearTeams();
		for(int i = 0; i < teamAmount; i++)
			new SkyWarsTeam(i);

		for(TeamMenu menu : TeamMenu.getInventories())
			menu.closeInventory();
	}

	public World getGameworld() {
		return gameworld;
	}

	public SkyWarsConfiguration getConfig() {
		return config;
	}

	public SkyWarsGamestate getGameState() {
		return state;
	}

	public SkyWarsThread getCurrentThread() {
		return currentThread;
	}

	public SkyWarsLobbyThread getAsLobbyThread() {
		return (SkyWarsLobbyThread) currentThread;
	}

	public SkyWarsIngameThread getAsIngameThread() {
		return (SkyWarsIngameThread) currentThread;
	}

	public SkyWarsFinishedThread getAsFinishedThread() {
		return (SkyWarsFinishedThread) currentThread;
	}
}