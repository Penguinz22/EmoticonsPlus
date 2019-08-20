package com.penguinz22.Emotes;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class EmoteConfiguration {

	private YamlConfiguration emotesConfig;
	private File emotesFile;
	
	public EmoteConfiguration() {
		this.emotesFile = new File("emotes.yml");
		this.emotesConfig = YamlConfiguration.loadConfiguration(emotesFile);
	}
	
	
	
}
