package de.cuuky.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.game.SkyWarsGamestate;
import de.cuuky.skywars.utils.SkyWarsItemUtils;

public class SetupCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("skywars.setup")) 
			return false;
		
		if(!(sender instanceof Player)) {
			System.out.println(Main.getConsolePrefix() + "Fuck off (Not for you)");
			return false;
		}
		
		Main.getInstance().getSkyWarsGame().setState(SkyWarsGamestate.SETUP);
		SkyWarsItemUtils.giveSetupItems((Player) sender);
		return false;
	}
}