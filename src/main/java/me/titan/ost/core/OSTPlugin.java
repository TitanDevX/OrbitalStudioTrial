package me.titan.ost.core;

import me.titan.ost.commands.BalCommand;
import me.titan.ost.commands.EarnCommand;
import me.titan.ost.commands.GiveBalCommand;
import me.titan.ost.commands.SetBalCommand;
import me.titan.ost.config.MainConfig;
import me.titan.ost.database.PlayersDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public class OSTPlugin extends JavaPlugin {

	public static OSTPlugin instance;


	MainConfig mainConfig;
	PlayersDatabase playersDatabase;

	@Override
	public void onEnable() {
		instance = this;

		getLogger().info("Starting plugin...");

		mainConfig = new MainConfig(this);

		playersDatabase = new PlayersDatabase();

		getCommand("balance").setExecutor(new BalCommand());
		getCommand("earn").setExecutor(new EarnCommand());
		getCommand("givebal").setExecutor(new GiveBalCommand());
		getCommand("setbalance").setExecutor(new SetBalCommand());


	}

	@Override
	public void onDisable() {

		instance = null;
	}

	public PlayersDatabase getPlayersDatabase() {
		return playersDatabase;
	}

	public MainConfig getMainConfig() {
		return mainConfig;
	}
}
