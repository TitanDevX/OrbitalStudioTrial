package me.titan.ost.commands;

import me.titan.ost.player.PlayerCache;
import me.titan.ost.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveBalCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

		if(args.length < 2) {

			Util.tell(s, "&cUsage: /givebal <player> <balance>");

			return false;
		}
		if(!(s instanceof Player p)){
			Util.tell(s,"You must be a player to perform this command, or specify a player name in the first argument.");
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

		PlayerCache.loadPlayerCache(p.getUniqueId(),(pc) -> {

			if(pc.getBalance() < amount){
				Util.tell(p,"&cYou don't have enough money to give.");
				return;
			}
			OfflinePlayer finalOp = op;
			PlayerCache oc = PlayerCache.getPlayerCache(finalOp.getUniqueId());

			oc.addBalance(amount);
			pc.addBalance(-amount);

			Util.tell(s,"&aYou given " + finalOp.getName() + " " + amount + " balance.");
			if(finalOp.isOnline()){
				Util.tell(finalOp.getPlayer(), "&a" + p.getName() + " gave you " + amount + " balance." );
			}

		});


		return true;
	}
}
