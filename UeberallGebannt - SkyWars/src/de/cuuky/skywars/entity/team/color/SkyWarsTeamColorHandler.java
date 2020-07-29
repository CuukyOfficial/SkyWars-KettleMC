package de.cuuky.skywars.entity.team.color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.serialization.CompatibleLocation;

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

		for (String configcolor : this.configuration.getKeys(true)) {
			if (!this.configuration.isConfigurationSection(configcolor))
				continue;

			SkyWarsTeamColor color = SkyWarsTeamColor.getTeamColor(configcolor);
			if (color == null)
				continue;

			ArrayList<CompatibleLocation> locations = (ArrayList<CompatibleLocation>) this.configuration.get(color.toString() + ".locations");
			for (CompatibleLocation location : locations)
				color.addSpawnLocation(location.getLocation());
		}
	}

	private void saveConfig() {
		try {
			this.configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		for (String path : this.configuration.getKeys(true))
			this.configuration.set(path, null);

		for (SkyWarsTeamColor teamcolor : SkyWarsTeamColor.values()) {
			ArrayList<CompatibleLocation> locations = new ArrayList<>();
			for (Location loc : teamcolor.getSpawnLocations().values())
				locations.add(new CompatibleLocation(loc));

			this.configuration.set(teamcolor.toString() + ".locations", locations);
		}

		saveConfig();
	}
}