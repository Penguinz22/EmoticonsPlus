package com.penguinz22.EmoticonsPlus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;

public class EmoteConfiguration {

	private YamlConfiguration emotesConfig;
	private File emotesFile;

	private EmoticonsPlus pl;
	
	public EmoteConfiguration(EmoticonsPlus pl) {
		this.pl = pl;
		if(!pl.getDataFolder().exists())
			pl.getDataFolder().mkdir();
		this.emotesFile = new File(pl.getDataFolder().getPath(), "emotes.yml");
		if(!this.emotesFile.exists()) {
			pl.saveResource("emotes.yml", false);
		}
		System.out.println(emotesFile.getPath());
		this.emotesConfig = YamlConfiguration.loadConfiguration(emotesFile);
		try {
			this.emotesConfig.save(emotesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Emote> load() {
		List<Emote> emotes = new ArrayList<Emote>();
		
		for(String name: emotesConfig.getKeys(false)) {
			System.out.println(name);
			String content = emotesConfig.getString(name+".content");
			boolean enabled = emotesConfig.getBoolean(name+".enabled");
			if(enabled)
				emotes.add(new Emote(name, content));
		}
		return emotes;
	}
	
	public List<Emote> reload() {
		this.emotesFile = new File(pl.getDataFolder().getPath(), "emotes.yml");
		this.emotesConfig = YamlConfiguration.loadConfiguration(emotesFile);
		return load();
	}
	
}
