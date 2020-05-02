package de.cuuky.skywars.game.thread.threads;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.cuuky.cfw.hooking.hooks.HookEntityType;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.SkyWarsChest;
import de.cuuky.skywars.chest.loot.SkyWarsChestType;
import de.cuuky.skywars.chest.loot.SkyWarsLootType;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.team.SkyWarsTeam;
import de.cuuky.skywars.game.SkyWarsGame;
import de.cuuky.skywars.game.thread.SkyWarsThread;
import de.cuuky.skywars.kit.SkyWarsKit;

public class SkyWarsIngameThread extends SkyWarsThread {

	private static Method setIngameMethod;

	static {
		try {
			Class<?> api = Class.forName("me.bukkitbuilder.cloudy.api.minecraft.CloudyMinecraftApi");
			setIngameMethod = api.getDeclaredMethod("setIngame", Boolean.class);
		} catch(Exception e) {
			System.out.println(Main.getConsolePrefix() + "No Cloudy found");
		}
	}

	private SkyWarsLootType lootType;
	private SkyWarsChestType chestType;

	public SkyWarsIngameThread(SkyWarsGame game) {
		super(game);

		Main.getInstance().getCuukyFrameWork().getInventoryManager().closeInventories();
		Main.getInstance().getCuukyFrameWork().getHookManager().clearHooks(HookEntityType.ITEM);

		preparePlayers();
		detectChestType();
		detectLootType();

		try {
			if(setIngameMethod != null)
				setIngameMethod.invoke(null, true);
		} catch(Exception e) {
			e.printStackTrace();
		}

		Bukkit.broadcastMessage(Main.getPrefix() + "Loot Type: §e" + lootType.toString());
		Bukkit.broadcastMessage(Main.getPrefix() + "Chest Type: §e" + chestType.toString());

		for(SkyWarsChest chest : SkyWarsChest.getChests())
			if(chest.getLocation().getWorld().equals(Main.getInstance().getSkyWarsGame().getGameworld()))
				chest.fillChest(lootType);

		this.timer = 0;
	}

	private void preparePlayers() {
		for(SkyWarsPlayer player : SkyWarsPlayer.getOnlineSkyWarsPlayer()) {
			player.getPlayer().setExp(0);
			player.getPlayer().setLevel(0);

			if(player.getSelectedKit() == null) {
				ArrayList<SkyWarsKit> kits = SkyWarsKit.getAvialableKitsFor(player);
				if(kits.size() != 0)
					kits.get(JavaUtils.randomInt(0, kits.size() - 1)).restoreInventory(player.getPlayer());
			} else
				player.getSelectedKit().restoreInventory(player.getPlayer());

			SkyWarsTeam team = SkyWarsTeam.getTeam(player);
			if(team == null) {
				for(SkyWarsTeam teams : SkyWarsTeam.getTeams()) {
					if(teams.getPlayers().size() != 0)
						continue;

					teams.addPlayer(player);
					team = teams;
					break;
				}

				if(team == null)
					for(SkyWarsTeam teams : SkyWarsTeam.getTeams()) {
						if(teams.isFull())
							continue;

						teams.addPlayer(player);
						team = teams;
						break;
					}
			}

			if(team == null) {
				player.getPlayer().kickPlayer("No team found!");
				break;
			}

			player.getPlayer().teleport(team.getTeamcolor().getSpawnLocations().get(Main.getInstance().getSkyWarsGame().getGameworld()));
		}
	}

	private void detectLootType() {
		for(SkyWarsLootType type : SkyWarsLootType.values()) {
			if(!type.isVotable())
				continue;

			if(lootType == null)
				lootType = type;
			else if(lootType.getVotes().size() < type.getVotes().size())
				lootType = type;
			else if(lootType.getVotes().size() == type.getVotes().size())
				lootType = JavaUtils.randomInt(0, 1) == 0 ? lootType : type;
		}
	}

	private void detectChestType() {
		for(SkyWarsChestType type : SkyWarsChestType.values()) {
			if(chestType == null)
				chestType = type;
			else if(chestType.getVotes().size() < type.getVotes().size())
				chestType = type;
			else if(chestType.getVotes().size() == type.getVotes().size())
				chestType = JavaUtils.randomInt(0, 1) == 0 ? chestType : type;
		}
	}

	@Override
	protected void doThreadHeartbeat() {
		timer++;
	}

	public SkyWarsChestType getChestType() {
		return chestType;
	}

	public SkyWarsLootType getLootType() {
		return lootType;
	}
}