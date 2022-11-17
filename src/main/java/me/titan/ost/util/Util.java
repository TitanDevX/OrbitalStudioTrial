package me.titan.ost.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {

	public static String colorize(String msg){
		return ChatColor.translateAlternateColorCodes('&',msg);
	}
	public static void tell(CommandSender s, String msg){
		s.sendMessage(colorize(msg));
	}

}
