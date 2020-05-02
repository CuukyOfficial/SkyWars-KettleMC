package de.cuuky.skywars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.skywars.Main;
import de.cuuky.skywars.kit.SkyWarsKit;

public class KitCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("skywars.setupkits")) {
			sender.sendMessage(Main.getPrefix() + "Nutze die Kiste um dein Kit auszuw§hlen!");
			return false;
		}

		if(!(sender instanceof Player)) {
			System.out.println(Main.getConsolePrefix() + "Fuck off (Not for you)");
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "/kit add <Displayname> [Permission] §8- §7Added Kit mit derzeitigem Inventar und Statuseffekten");
			sender.sendMessage(Main.getPrefix() + "/kit icon <KitID> §8- §7Setzt das Icon des Kits auf das in der Hand");
			sender.sendMessage(Main.getPrefix() + "/kit remove <KitID>");
			sender.sendMessage(Main.getPrefix() + "/kit load <KitID>");
			sender.sendMessage(Main.getPrefix() + "/kit list");
			return false;
		}

		if(args[0].equalsIgnoreCase("add")) {
			if(args.length < 2) {
				sender.sendMessage(Main.getPrefix() + "/kit add <Displayname> [Permission] §8- §7Added Kit mit derzeitigem Inventar und Statuseffekten");
				return false;
			}

			SkyWarsKit kit = new SkyWarsKit(args[1], args.length > 2 ? args[2] : null, (Player) sender);
			sender.sendMessage(Main.getPrefix() + "Kit mit der ID §e" + kit.getId() + " §ferfolgreich erstellt!");
		} else if(args[0].equalsIgnoreCase("icon")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "/kit icon <KitID>");
				return false;
			}

			int kitid = 0;
			try {
				kitid = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + "Keine Zahl angegeben");
				return false;
			}

			SkyWarsKit kit = SkyWarsKit.getKit(kitid);
			if(kit == null) {
				sender.sendMessage(Main.getPrefix() + "Kit nicht gefunden!");
				return false;
			}
			
			ItemStack item = ((Player) sender).getItemInHand();
			if(item == null) {
				sender.sendMessage(Main.getPrefix() + "Item nicht gefunden!");
				return false;
			}

			kit.setIcon(item);
			sender.sendMessage(Main.getPrefix() + "Icon von Kit §e" + kitid + " §ferfolgreich gesetzt!");
		} else if(args[0].equalsIgnoreCase("remove")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "/kit remove <KitID>");
				return false;
			}

			int kitid = 0;
			try {
				kitid = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + "Keine Zahl angegeben");
				return false;
			}

			SkyWarsKit kit = SkyWarsKit.getKit(kitid);
			if(kit == null) {
				sender.sendMessage(Main.getPrefix() + "Kit nicht gefunden!");
				return false;
			}

			kit.remove();
			sender.sendMessage(Main.getPrefix() + "Kit §e" + kitid + " §ferfolgreich entfernt!");
		} else if(args[0].equalsIgnoreCase("list")) {
			if(SkyWarsKit.getKits().size() == 0) {
				sender.sendMessage(Main.getPrefix() + "Keine Kits gefunden!");
				return false;
			}

			for(SkyWarsKit kit : SkyWarsKit.getKits())
				sender.sendMessage(Main.getPrefix() + "Kit §e" + kit.getId() + "§8: §7" + kit.getName());
		} else if(args[0].equalsIgnoreCase("load")) {
			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "/kit load <KitID>");
				return false;
			}

			int kitid = 0;
			try {
				kitid = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + "Keine Zahl angegeben");
				return false;
			}

			SkyWarsKit kit = SkyWarsKit.getKit(kitid);
			if(kit == null) {
				sender.sendMessage(Main.getPrefix() + "Kit nicht gefunden!");
				return false;
			}

			kit.restoreInventory((Player) sender);
			sender.sendMessage(Main.getPrefix() + "Kit §e" + kitid + " §ferfolgreich geladen!");
		} else
			sender.sendMessage(Main.getPrefix() + "/kit");
		return false;
	}
}