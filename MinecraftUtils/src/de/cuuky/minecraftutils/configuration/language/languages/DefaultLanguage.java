package de.cuuky.minecraftutils.configuration.language.languages;

import de.cuuky.minecraftutils.player.CustomPlayer;

public interface DefaultLanguage extends LoadableMessage {
	
	public String getValue();
	public String getValue(CustomPlayer languageHolder);
	public String getValue(CustomPlayer languageHolder, CustomPlayer replace);

}