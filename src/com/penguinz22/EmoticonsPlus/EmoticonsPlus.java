package com.penguinz22.EmoticonsPlus;

import java.util.List;
import java.util.regex.Matcher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EmoticonsPlus extends JavaPlugin implements Listener{

	public List<Emote> emotes;
	private EmoteConfiguration config;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.config = new EmoteConfiguration(this);
		emotes = this.config.load();
		for(Emote emote: emotes) {
			System.out.println(emote.getName()+" - "+emote.getUnicode());
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		
		for(Emote emote: emotes) {
			Matcher matcher = emote.getPattern().matcher(message);
			message = matcher.replaceAll(emote.getUnicode());
		}
		event.setMessage(message);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("emote")) {
			if(args.length == 0) {
				sendHelp(sender);
				return true;
			} else {
				if(args[0].equalsIgnoreCase("help")) {
					sendHelp(sender);
					return true;
				} else if(args[0].equalsIgnoreCase("reload")) {
					emotes = this.config.reload();
					sender.sendMessage(ChatColor.LIGHT_PURPLE+"[Emotes] "+ChatColor.GRAY+"- "+ChatColor.GREEN+"Successfully reloaded the emotes config file!");
					return true;
				} else if(args[0].equalsIgnoreCase("list")) {
					// TODO - Add a list for emotes and create pages based on the amount of emotes
					sender.sendMessage(ChatColor.RED+"This feature will be developed in the future!");
					return false;
				}
			}
			
		}
		return false;
	}
	
	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN+"Emote Commands:");
		sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.GREEN+"/emote list"+ChatColor.GRAY+" - "+ChatColor.GREEN+"List all emotes you can use");
		if(sender instanceof ConsoleCommandSender || sender.hasPermission("emotes.reload"))
			sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.GREEN+"/emote reload"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Reloads the emotes config file");
		sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.GREEN+"/emote help"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Shows this list");
	}
	
	
}
