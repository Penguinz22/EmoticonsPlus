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
	
	private final int amountPerPage = 10;
	
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
					if(args.length >= 2) {
						int page = 1;
						try {
							page = Integer.valueOf(args[1]);
						} catch(NumberFormatException e) {
							sender.sendMessage(ChatColor.RED+"Incorrect Usage, Please do /emote list <page>");
							return false;
						}
						sendEmotePage(sender, page);
					} else {
						sendEmotePage(sender, 1);
					}
					return true;
				}
			}
			
		}
		return false;
	}
	
	private void sendEmotePage(CommandSender sender, int page) {
		int maxPages = (int) Math.ceil((double) emotes.size()/amountPerPage);
		if(page > maxPages) {
			if(maxPages == 0) {
				sender.sendMessage(ChatColor.RED+"There are not emotes currently!");
				return;
			}
			sender.sendMessage(ChatColor.RED+"There is no page "+page+"! There are only "+maxPages+" pages!");
		} else if(page <= 0) {
			sender.sendMessage(ChatColor.RED+"Please start at page 1!");
		} else {
			sender.sendMessage(ChatColor.GREEN+"Showing emotes, page "+page+" of "+maxPages+"!");
			for (int i = 0; i < amountPerPage; i++) {
				if((emotes.size() == (page-1)*amountPerPage+i))
					break;
				Emote emote = emotes.get((page-1)*amountPerPage+i);
				sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.LIGHT_PURPLE+":"+emote.getName()+":"+ChatColor.GRAY+" - "+ChatColor.LIGHT_PURPLE+emote.getUnicode());
			}
			sender.sendMessage("");
			sender.sendMessage(ChatColor.RED+"Do \"/emote list <page>\" to go to other pages!");
		}
	}
	
	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN+"Emote Commands:");
		sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.GREEN+"/emote list"+ChatColor.GRAY+" - "+ChatColor.GREEN+"List all emotes you can use");
		if(sender instanceof ConsoleCommandSender || sender.hasPermission("emotes.reload"))
			sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.GREEN+"/emote reload"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Reloads the emotes config file");
		sender.sendMessage(ChatColor.DARK_GRAY+" - "+ChatColor.GREEN+"/emote help"+ChatColor.GRAY+" - "+ChatColor.GREEN+"Shows this list");
	}
	
	
}
