package com.penguinz22.EmoticonsPlus;

import java.util.regex.Pattern;

public class Emote {

	private String name;
	private Pattern pattern;
	private String unicode;
	
	public Emote(String name, String unicode) {
		this.name = name;
		this.pattern = Pattern.compile("(:)("+name+")(:)");
		this.unicode = unicode;
	}
	
	public String getName() {
		return name;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public String getUnicode() {
		return unicode;
	}
	
}
