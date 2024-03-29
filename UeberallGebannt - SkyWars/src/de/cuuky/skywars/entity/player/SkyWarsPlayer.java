package de.cuuky.skywars.entity.player;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.cuuky.cfw.clientadapter.ClientAdapterManager;
import de.cuuky.cfw.clientadapter.board.nametag.CustomNametag;
import de.cuuky.cfw.clientadapter.board.scoreboard.CustomScoreboard;
import de.cuuky.cfw.clientadapter.board.tablist.CustomTablist;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.cfw.player.clientadapter.BoardUpdateHandler;
import de.cuuky.cfw.player.connection.NetworkManager;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.clientadapter.CustomBoardUpdateHandler;
import de.cuuky.skywars.entity.SkyWarsEntity;
import de.cuuky.skywars.entity.player.stats.SkyWarsPlayerStats;
import de.cuuky.skywars.kit.SkyWarsKit;

public class SkyWarsPlayer implements CustomPlayer, SkyWarsEntity {

	private static ArrayList<SkyWarsPlayer> skywarsplayer;

	static {
		skywarsplayer = new ArrayList<>();
	}

	private String uuid, name;
	private SkyWarsPlayerStats stats;
	private SkyWarsPlayerState state;
	private SkyWarsKit selectedKit;

	private NetworkManager networkmanager;
	private CustomNametag<SkyWarsPlayer> nametag;
	private CustomScoreboard<SkyWarsPlayer> scoreboard;
	private CustomTablist<SkyWarsPlayer> tablist;
	private CustomBoardUpdateHandler updateHandler;
	private Player player;

	public SkyWarsPlayer(String uuid) {
		this.uuid = uuid;
		this.stats = new SkyWarsPlayerStats(this);
		this.state = SkyWarsPlayerState.ALIVE;

		skywarsplayer.add(this);
	}

	public void update() {
		nametag.update();
		scoreboard.update();
		tablist.update();
	}

	@Override
	public String getUUID() {
		return this.uuid;
	}

	@Override
	public NetworkManager getNetworkManager() {
		return networkmanager;
	}

	public CustomNametag<SkyWarsPlayer> getNametag() {
		return nametag;
	}

	public CustomScoreboard<SkyWarsPlayer> getScoreboard() {
		return scoreboard;
	}

	public CustomTablist<SkyWarsPlayer> getTablist() {
		return tablist;
	}

	public SkyWarsPlayerStats getStats() {
		return stats;
	}

	public void setState(SkyWarsPlayerState state) {
		this.state = state;
	}

	public SkyWarsPlayerState getState() {
		return state;
	}

	public void setSelectedKit(SkyWarsKit selectedKit) {
		this.selectedKit = selectedKit;
	}

	public SkyWarsKit getSelectedKit() {
		return selectedKit;
	}

	public void setPlayer(Player player) {
		this.player = player;

		if (player != null) {
			this.name = player.getName();
			this.networkmanager = new NetworkManager(player);

			ClientAdapterManager<SkyWarsPlayer> utils = Main.getInstance().getCuukyFrameWork().getClientAdapterManager();
			this.scoreboard = (CustomScoreboard<SkyWarsPlayer>) utils.registerBoard(new CustomScoreboard<SkyWarsPlayer>(this));
			this.nametag = (CustomNametag<SkyWarsPlayer>) utils.registerBoard(new CustomNametag<SkyWarsPlayer>(this));
			this.tablist = (CustomTablist<SkyWarsPlayer>) utils.registerBoard(new CustomTablist<SkyWarsPlayer>(this));
		} else {
			this.nametag.remove();
			this.scoreboard.remove();
			this.tablist.remove();

			this.networkmanager = null;
			this.nametag = null;
			this.scoreboard = null;
			this.tablist = null;
		}
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public int getKills() {
		return stats.getKills();
	}

	@Override
	public String getName() {
		return name == null ? uuid : name;
	}

	@Override
	public String getLocale() {
		return this.networkmanager.getLocale();
	}

	@Override
	public BoardUpdateHandler<SkyWarsPlayer> getUpdateHandler() {
		return this.updateHandler == null ? this.updateHandler = new CustomBoardUpdateHandler(this) : this.updateHandler;
	}

	public static SkyWarsPlayer getPlayer(Player player) {
		return getPlayer(player.getUniqueId().toString());
	}

	public static SkyWarsPlayer getPlayer(String uuid) {
		for (SkyWarsPlayer player : skywarsplayer)
			if (player.getUUID().equals(uuid))
				return player;

		return null;
	}

	public static ArrayList<SkyWarsPlayer> getOnlineSkyWarsPlayer() {
		ArrayList<SkyWarsPlayer> online = new ArrayList<>();
		for (SkyWarsPlayer player : skywarsplayer)
			if (player.getPlayer() != null)
				online.add(player);

		return online;
	}

	public static ArrayList<SkyWarsPlayer> getAliveSkyWarsPlayer() {
		ArrayList<SkyWarsPlayer> alive = new ArrayList<>();
		for (SkyWarsPlayer player : skywarsplayer)
			if (player.getState() == SkyWarsPlayerState.ALIVE)
				alive.add(player);

		return alive;
	}

	public static ArrayList<SkyWarsPlayer> getSkyWarsPlayer() {
		return skywarsplayer;
	}
}