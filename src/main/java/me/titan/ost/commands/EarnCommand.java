package me.titan.ost.commands;

import me.titan.ost.player.PlayerCache;
import me.titan.ost.util.Util;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class EarnCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

		if(!(s instanceof Player p)){
				Util.tell(s,"You must be a player to perform this command, or specify a player name in the first argument.");
				return false;
		}

		PlayerCache pc = PlayerCache.getPlayerCache(p.getUniqueId());
		if(pc.getLastEarn() > 0){

			long dif = (System.currentTimeMillis() - pc.getLastEarn()) /1000;
			if(dif < 60){
				Util.tell(s,"&cPlease wait " + dif + " seconds before performing this command again.");
				return false;
			}
		}
		pc.setLastEarn(System.currentTimeMillis());
		double random = new Random().nextDouble(5)+1;
		pc.addBalance(random);
		Util.tell(s,"&a&lYou earned " + random + "$!!!!!");
		for(int i = 0;i<4;i++){
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,0.1f);
		}

		return true;
	}
}
