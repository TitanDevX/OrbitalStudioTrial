package me.titan.ost.player;

import me.titan.ost.core.OSTPlugin;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerCache {

	public static Map<UUID, PlayerCache> players = new HashMap<>();
	UUID uuid;

	double balance;

	long lastEarn;

	public PlayerCache(UUID id){
		this.uuid = id;
	}

	public static PlayerCache getPlayerCache(UUID id){
		if(players.containsKey(id)) {
			return players.get(id);
		}
		PlayerCache pc = new PlayerCache(id);
		players.put(id,pc);
		return pc;
	}
	public static void loadPlayerCache(UUID id, Consumer<PlayerCache> result){
		if(players.containsKey(id)) {
			result.accept(players.get(id));
			return;
		}
		Bukkit.getScheduler().runTaskAsynchronously(OSTPlugin.instance,() -> {
			OSTPlugin.instance.getPlayersDatabase().loadCache(id,result);
		});

	}
	public void save(){
		Bukkit.getScheduler().runTaskAsynchronously(OSTPlugin.instance,() -> {
			OSTPlugin.instance.getPlayersDatabase().saveCache(PlayerCache.this);
		});
	}


	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		Bukkit.getScheduler().runTaskAsynchronously(OSTPlugin.instance,() -> {
			OSTPlugin.instance.getPlayersDatabase().setBalance(uuid,balance);
		});

		this.balance = balance;
	}
	public void addBalance(double balance) {

		Bukkit.getScheduler().runTaskAsynchronously(OSTPlugin.instance,() -> {
			OSTPlugin.instance.getPlayersDatabase().addBalance(uuid,balance);
		});

		this.balance += balance;
	}
	public void unCache(){
		players.remove(uuid);
	}

	public long getLastEarn() {
		return lastEarn;
	}

	public void setLastEarn(long lastEarn) {
		this.lastEarn = lastEarn;
	}

	public UUID getUuid() {
		return uuid;
	}

}
