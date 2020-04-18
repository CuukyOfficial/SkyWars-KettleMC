package de.cuuky.skywars.entity.team.color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class SkyWarsTeamColorHandler {

	private File file;
	private YamlConfiguration configuration;

	public SkyWarsTeamColorHandler() {
		this.file = new File("plugins/SkyWars/teams.yml");

		load();
	}

	@SuppressWarnings("unchecked")
	private void load() {
		this.configuration = YamlConfiguration.loadConfiguration(file);

		for(String configcolor : this.configuration.getKeys(true)) {
			if(!this.configuration.isConfigurationSection(configcolor))
				continue;

			SkyWarsTeamColor color = SkyWarsTeamColor.getTeamColor(configcolor);
			if(color == null)
				continue;

			ArrayList<Location> locations = (ArrayList<Location>) this.configuration.get(color.toString() + ".locations");
			for(Location location : locations)
				color.addSpawnLocation(location);
		}
	}

	private void saveConfig() {
		try {
			this.configuration.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		for(String path : this.configuration.getKeys(true))
			this.configuration.set(path, null);

		for(SkyWarsTeamColor teamcolor : SkyWarsTeamColor.values()) {
			ArrayList<Location> locations = new ArrayList<>();
			locations.addAll(teamcolor.getSpawnLocations().values());
			
			this.configuration.set(teamcolor.toString() + ".locations", locations);
		}

		saveConfig();
	}
}