package de.cuuky.skywars.menu;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.minecraftutils.item.ItemBuilder;
import de.cuuky.minecraftutils.menu.SuperInventory;
import de.cuuky.minecraftutils.menu.utils.PageAction;
import de.cuuky.minecraftutils.utils.JavaUtils;
import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;
import de.cuuky.skywars.entity.team.SkyWarsTeam;

public class TeamMenu extends SuperInventory {

	private static ArrayList<TeamMenu> inventories;

	static {
		inventories = new ArrayList<>();
	}

	public TeamMenu(Player opener) {
		super("§1Team auswählen", opener, JavaUtils.getNextToNine(SkyWarsTeam.getTeams().size() * 2), true);

		this.setModifier = false;
		Main.getInstance().getMinecraftUtils().getInventoryManager().registerInventory(this);
		
		open();

		inventories.add(this);
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {
		inventories.remove(this);
	}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		ArrayList<SkyWarsTeam> teams = new ArrayList<SkyWarsTeam>(SkyWarsTeam.getTeams());
		int i = 1;
		for(int teamnumber = 0; teamnumber < teams.size(); teamnumber++) {
			SkyWarsTeam team = teams.get(teamnumber);

			ArrayList<String> lore = new ArrayList<>();
			lore.add("§7Farbe: " + team.getTeamcolor().getFullColor() + team.getName());
			lore.add(" ");
			for(int member = 0; member < Main.getInstance().getSkyWarsGame().getTeamSize(); member++)
				lore.add("§7» " + team.getTeamcolor().getFullColor() + (team.getPlayers().size() > member ? team.getPlayers().get(member).getName() : "-"));

			if(!team.isFull()) {
				lore.add(" ");
				lore.add("§7Klicke, um " + team.getTeamcolor().getFullColor() + "Team " + team.getName() + " §7beizutreten!");
			}

			linkItemTo(i, new ItemBuilder().displayname(team.getTeamcolor().getFullColor() + "Team " + team.getName()).lore(lore).itemstack(new ItemStack(Material.IRON_SWORD)).build(), new Runnable() {

				@Override
				public void run() {
					if(team.isFull())
						return;

					SkyWarsPlayer player = SkyWarsPlayer.getPlayer(opener);
					SkyWarsTeam playerteam = SkyWarsTeam.getTeam(player);

					if(playerteam != null) {
						if(playerteam.equals(team))
							return;
						else
							playerteam.removePlayer(player);
					}

					team.addPlayer(SkyWarsPlayer.getPlayer(opener));
					reopenSoon();
				}
			});
			i+=2;
		}
		return true;
	}

	public static void updateInventories() {
		for(TeamMenu menu : new ArrayList<>(inventories))
			menu.updateInventory();
	}

	public static ArrayList<TeamMenu> getInventories() {
		return inventories;
	}
}