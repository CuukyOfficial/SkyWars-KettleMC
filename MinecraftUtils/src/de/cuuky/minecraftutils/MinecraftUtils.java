package de.cuuky.minecraftutils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.minecraftutils.clientadapter.ClientAdapterManager;
import de.cuuky.minecraftutils.hooking.HookManager;
import de.cuuky.minecraftutils.hooking.listener.HookListener;
import de.cuuky.minecraftutils.menu.SuperInventoryManager;
import de.cuuky.minecraftutils.menu.utils.InventoryListener;

public class MinecraftUtils {

	/*
	 * MinecraftUtils - A Bukkit framework
	 * 
	 * VERSION: 0.1 (2020) 
	 * AUTHOR: Cuuky
	 */

	private JavaPlugin ownerInstance;

	private HookManager hookManager;
	private ClientAdapterManager clientAdapterManager;
	private SuperInventoryManager inventoryManager;

	public MinecraftUtils(JavaPlugin pluginInstance) {
		this.ownerInstance = pluginInstance;

		this.hookManager = new HookManager(this);
		this.clientAdapterManager = new ClientAdapterManager(this);
		this.inventoryManager = new SuperInventoryManager(this);

		registerListener();
	}

	private void registerListener() {
		Bukkit.getPluginManager().registerEvents(new HookListener(this.hookManager), ownerInstance);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(this.inventoryManager), ownerInstance);
	}

	public JavaPlugin getPluginInstance() {
		return this.ownerInstance;
	}

	public HookManager getHookManager() {
		return hookManager;
	}

	public ClientAdapterManager getClientAdapterManager() {
		return clientAdapterManager;
	}

	public SuperInventoryManager getInventoryManager() {
		return inventoryManager;
	}
}