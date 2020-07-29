package de.cuuky.skywars.chest;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class SkyWarsChestHandler {

	static {
		ConfigurationSerialization.registerClass(SkyWarsChest.class);
	}

	private File file;
	private YamlConfiguration configuration;

	public SkyWarsChestHandler() {
		this.file = new File("plugins/SkyWars/chests.yml");

		load();
	}

	private void load() {
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}

	public void save() {
		for (String path : this.configuration.getKeys(true))
			this.configuration.set(path, null);

		for (SkyWarsChest chest : SkyWarsChest.getChests())
			this.configuration.set(String.valueOf(chest.getId()), chest);

		try {
			this.configuration.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}