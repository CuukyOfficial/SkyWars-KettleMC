package de.cuuky.skywars.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.item.ItemHook;
import de.cuuky.cfw.hooking.hooks.item.ItemHookHandler;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PlayerChooseInventory;
import de.cuuky.cfw.menu.utils.PlayerChooseInventory.PlayerChooseEvent;
import de.cuuky.cfw.menu.utils.PlayerChooseInventory.PlayerChooseHandler;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.chest.SkyWarsChest;
import de.cuuky.skywars.menu.KitMenu;
import de.cuuky.skywars.menu.TeamMenu;
import de.cuuky.skywars.menu.VoteMainMenu;
import de.cuuky.skywars.menu.setup.SetSpawnMenu;

public class SkyWarsItemUtils {

	private static void addSetupHook(ItemHook hook) {
		((ItemHook) Main.getInstance().getCuukyFrameWork().getHookManager().registerHook(hook)).setDropable(true);
	}

	public static void giveSetupItems(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] {});
		addSetupHook(new ItemHook(player, new ItemBuilder().displayname("§eLobby setzen").material(Material.EMERALD).build(), 0, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
					return;

				Main.getInstance().getSkyWarsGame().setLobbyLocation(event.getPlayer().getLocation());
				event.getPlayer().sendMessage(Main.getPrefix() + "Lobby erfolgreich gesetzt!");
				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		addSetupHook(new ItemHook(player, new ItemBuilder().displayname("§eKiste §7(§cremove=Links§7/§aadd=Rechts§7)§eKlick").material(Material.STICK).build(), 1, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					SkyWarsChest chest = SkyWarsChest.getChest(event.getClickedBlock());

					if (event.getClickedBlock().getType() != Material.CHEST)
						return;

					if (chest != null)
						chest.remove();

					event.getPlayer().sendMessage(Main.getPrefix() + "Kiste erfolgreich entfernt!");

					event.setCancelled(true);
				} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					SkyWarsChest chest = SkyWarsChest.getChest(event.getClickedBlock());

					if (event.getClickedBlock().getType() != Material.CHEST)
						return;

					if (chest != null) {
						event.getPlayer().sendMessage(Main.getPrefix() + "Diese Kiste ist bereits hinzugef§gt!");
						return;
					}

					new SkyWarsChest(event.getClickedBlock().getLocation());
					event.getPlayer().sendMessage(Main.getPrefix() + "Kiste erfolgreich hinzugef§gt!");

					event.setCancelled(true);
				}
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		addSetupHook(new ItemHook(player, new ItemBuilder().displayname("§eSpawns setzen").material(Material.IRON_HELMET).build(), 2, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
					return;

				new SetSpawnMenu(player);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));
	}

	public static void giveLobbyItems(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] {});

		Main.getInstance().getCuukyFrameWork().getHookManager().registerHook(new ItemHook(player, new ItemBuilder().displayname("§bW§hle dein Kit").material(Material.CHEST).build(), 0, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
					return;

				new KitMenu(player);

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		Main.getInstance().getCuukyFrameWork().getHookManager().registerHook(new ItemHook(player, new ItemBuilder().displayname("§eW§hle dein Team").material(Material.BED).build(), 1, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
					return;

				new TeamMenu(player);

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		Main.getInstance().getCuukyFrameWork().getHookManager().registerHook(new ItemHook(player, new ItemBuilder().displayname("§cAbstimmungen").material(Material.ANVIL).build(), 2, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
					return;

				new VoteMainMenu(player);

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));

		Main.getInstance().getCuukyFrameWork().getHookManager().registerHook(new ItemHook(player, new ItemBuilder().displayname("§1Stats").material(Material.BOOK).build(), 8, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
					return;

				player.performCommand("stats");

				event.setCancelled(true);
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));
	}

	public static void giveSpectatorItems(Player player) {
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] {});

		for (Player pl : VersionUtils.getOnlinePlayer())
			pl.hidePlayer(player);

		Main.getInstance().getCuukyFrameWork().getHookManager().registerHook(new ItemHook(player, new ItemBuilder().displayname("§bTeleporter").itemstack(new ItemStack(Material.COMPASS)).build(), 0, new ItemHookHandler() {

			@Override
			public void onInteractEntity(PlayerInteractEntityEvent event) {}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				new PlayerChooseInventory(player, (Player[]) VersionUtils.getOnlinePlayer().toArray(), new PlayerChooseHandler() {

					@Override
					public void onPlayerChoose(PlayerChooseEvent event) {
						player.teleport(event.getChoosen());
					}
				}, Main.getInstance().getCuukyFrameWork());
			}

			@Override
			public void onEntityHit(EntityDamageByEntityEvent event) {}
		}));
	}
}