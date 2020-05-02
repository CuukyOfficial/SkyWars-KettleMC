package de.cuuky.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.cfw.CuukyFrameWork;
import de.cuuky.cfw.clientadapter.board.CustomBoardType;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.skywars.clientadapter.CustomBoardUpdateHandler;
import de.cuuky.skywars.commands.ForceMapCommand;
import de.cuuky.skywars.commands.KitCommand;
import de.cuuky.skywars.commands.SetupCommand;
import de.cuuky.skywars.commands.StatsCommand;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.player.stats.SkyWarsPlayerStats;
import de.cuuky.skywars.game.SkyWarsGame;
import de.cuuky.skywars.game.SkyWarsGamestate;
import de.cuuky.skywars.listener.CancelListener;
import de.cuuky.skywars.listener.ChestListener;
import de.cuuky.skywars.listener.DeathListener;
import de.cuuky.skywars.listener.PlayerHandleListener;
import de.cuuky.skywars.listener.SpectatorListener;
import de.cuuky.skywars.utils.SkyWarsItemUtils;

public class Main extends JavaPlugin {

	private static final String PREFIX = "§e§lSKYWARS §8§ §7", CONSOLE_PREFIX = "[SkyWars] ";

	private static Main instance;

	private SkyWarsGame skyWarsGame;
	private CuukyFrameWork cuukyFrameWork;

	@Override
	public void onEnable() {
		System.out.println(CONSOLE_PREFIX + "Enabling " + getDescription().getName() + " v" + getDescription().getVersion() + " by " + getDescription().getAuthors().get(0) + "...");
		instance = this;

		System.out.println(CONSOLE_PREFIX + "Initializing framework...");
		this.cuukyFrameWork = new CuukyFrameWork(this);
		this.cuukyFrameWork.getClientAdapterManager().setUpdateHandler(new CustomBoardUpdateHandler());
		this.cuukyFrameWork.getClientAdapterManager().setBoardTypeEnabled(CustomBoardType.TABLIST, false);

		System.out.println(CONSOLE_PREFIX + "Loading MySQL...");
		skyWarsGame = new SkyWarsGame();
		SkyWarsPlayerStats.loadMySQL();

		System.out.println(CONSOLE_PREFIX + "Registering bukkit...");
		registerListener();
		registerCommands();

		for(Player player : VersionUtils.getOnlinePlayer()) {
			new SkyWarsPlayer(player.getUniqueId().toString()).setPlayer(player);
			
			if(skyWarsGame.getGameState() == SkyWarsGamestate.LOBBY)
				SkyWarsItemUtils.giveLobbyItems(player);
			else if(skyWarsGame.getGameState() == SkyWarsGamestate.SETUP && player.hasPermission("skywars.setup"))
				SkyWarsItemUtils.giveSetupItems(player);
		}
		
		System.out.println(CONSOLE_PREFIX + "Enabled!");
	}
	
	@Override
	public void onDisable() {
		System.out.println(CONSOLE_PREFIX + "Disabling...");
		
		System.out.println(CONSOLE_PREFIX + "Saving data...");
		skyWarsGame.saveData();
		
		System.out.println(CONSOLE_PREFIX + "Disabled!");
		super.onDisable();
	}

	private void registerListener() {
		Bukkit.getPluginManager().registerEvents(new PlayerHandleListener(), this);
		Bukkit.getPluginManager().registerEvents(new CancelListener(), this);
		Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new SpectatorListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChestListener(), this);
	}

	private void registerCommands() {
		getCommand("setup").setExecutor(new SetupCommand());
		getCommand("forcemap").setExecutor(new ForceMapCommand());
		getCommand("kit").setExecutor(new KitCommand());
		getCommand("stats").setExecutor(new StatsCommand());
	}

	public SkyWarsGame getSkyWarsGame() {
		return skyWarsGame;
	}
	
	public CuukyFrameWork getCuukyFrameWork() {
		return cuukyFrameWork;
	}

	public static String getConsolePrefix() {
		return CONSOLE_PREFIX;
	}

	public static String getPrefix() {
		return PREFIX;
	}

	public static Main getInstance() {
		return instance;
	}
}