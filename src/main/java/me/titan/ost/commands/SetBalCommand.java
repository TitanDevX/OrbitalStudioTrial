package me.titan.ost.commands;

import me.titan.ost.player.PlayerCache;
import me.titan.ost.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBalCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

		if(args.length < 2) {

			Util.tell(s, "&cUsage: /setbal <player> <balance>");

			return false;
		}
		if(!s.isOp()){
			Util.tell(s,"&cYou don't have permission to execute this command.");
			return false;
		}
		OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
		double amount = Double.parseDouble(args[1]);
		if(amount <= 0){
			Util.tell(s,"&cInvalid number!");
			return false;
		}
		if(!op.hasPlayedBefore()){
			Util.tell(s,"&cThis player has never been on the server.");
			return false;
		}
		PlayerCache pc = PlayerCache.getPlayerCache(op.getUniqueId());
		pc.setBalance(amount);
		Util.tell(s,"&aYou set " + op.getName() + "'s balance to " + amount);
		return true;
	}
}
