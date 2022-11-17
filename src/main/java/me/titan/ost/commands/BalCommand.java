package me.titan.ost.commands;

import me.titan.ost.player.PlayerCache;
import me.titan.ost.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

		OfflinePlayer op = null;
		if(args.length == 0){
			if(!(s instanceof Player p)){
				Util.tell(s,"You must be a player to perform this command, or specify a player name in the first argument.");
				return false;
			}
			op = p;
		}else{
			op = Bukkit.getOfflinePlayer(args[0]);
		}
		if(!op.hasPlayedBefore()){
			Util.tell(s,"&cThis player has never been on the server.");
			return false;
		}
		OfflinePlayer finalOp = op;
		PlayerCache.loadPlayerCache(op.getUniqueId(),(pc) -> {

		Util.tell(s,"&aBalance of " + finalOp.getName() + " is " + pc.getBalance());

		});

		return true;
	}
}
