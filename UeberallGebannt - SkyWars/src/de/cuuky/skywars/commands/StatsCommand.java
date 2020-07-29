package de.cuuky.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.entity.player.SkyWarsPlayer;

public class StatsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			System.out.println(Main.getConsolePrefix() + "Fuck off (Not for you)");
			return false;
		}

		SkyWarsPlayer player = SkyWarsPlayer.getPlayer((Player) sender);
		player.getPlayer().sendMessage(Main.getPrefix() + "§8----- §7Deine §eStats §8-----");
		player.getPlayer().sendMessage(Main.getPrefix() + "Kills: §e" + (player.getStats().getKills() + player.getStats().getLocalKills()));
		player.getPlayer().sendMessage(Main.getPrefix() + "Tode: §e" + player.getStats().getDeaths());
		player.getPlayer().sendMessage(Main.getPrefix() + "KDR: §e" + player.getStats().getKDR());
		player.getPlayer().sendMessage(Main.getPrefix() + "Gespielte Spiele: §e" + player.getStats().getPlayedGames());
		player.getPlayer().sendMessage(Main.getPrefix() + "Siege: §e" + player.getStats().getWins());
		player.getPlayer().sendMessage(Main.getPrefix() + "Sieges Chance: §e" + Math.round((player.getStats().getWinChance() * 100d)) + "%");
		return false;
	}
}