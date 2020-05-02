package de.cuuky.skywars.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.game.SkyWarsGamestate;

public class ForceMapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("skywars.forcemap")) {
			sender.sendMessage(Main.getPrefix() + "Nein ;)");
			return false;
		}
		
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "/forcemap <Map>");
			return false;
		}
		
		if(Main.getInstance().getSkyWarsGame().getGameState() != SkyWarsGamestate.LOBBY) {
			sender.sendMessage(Main.getPrefix() + "Nur in der Lobbyphase m§glich!");
			return false;
		}
		
		World world = Bukkit.getWorld(args[0]);
		if(world == null) {
			sender.sendMessage(Main.getPrefix() + "Map §e" + args[1] + " §fnicht gefunden!");
			return false;
		}
		
		Main.getInstance().getSkyWarsGame().setGameworld(world);
		sender.sendMessage(Main.getPrefix() + "Die Map lautet nun: §e" + world.getName());
		return false;
	}
}