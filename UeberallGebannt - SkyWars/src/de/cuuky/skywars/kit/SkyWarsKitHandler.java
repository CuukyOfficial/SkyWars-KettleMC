package de.cuuky.skywars.kit;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class SkyWarsKitHandler {

	static {
		ConfigurationSerialization.registerClass(SkyWarsKit.class);
	}

	private File file;
	private YamlConfiguration configuration;

	public SkyWarsKitHandler() {
		this.file = new File("plugins/SkyWars/kits.yml");

		load();
	}

	private void load() {
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}

	public void save() {
		for(String path : this.configuration.getKeys(true))
			this.configuration.set(path, null);

		for(SkyWarsKit kit : SkyWarsKit.getKits())
			this.configuration.set(String.valueOf(kit.getId()), kit);

		try {
			this.configuration.save(this.file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}