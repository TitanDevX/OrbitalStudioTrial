package me.titan.ost.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import me.titan.ost.config.lib.SimpleConfig;
import me.titan.ost.core.OSTPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MainConfig extends SimpleConfig {


	MysqlDataSource playersDatabase;
	String playersDatabaseTable;


	public MainConfig(JavaPlugin plugin) {
		super("config.yml", plugin);

		init();
	}

	@Override
	protected void init() {
		OSTPlugin.instance.getLogger().info("Loading main config...");

		playersDatabase = getMySqlDataSource("database");
		playersDatabaseTable = config.getString("database.table");

		OSTPlugin.instance.getLogger().info("successfully loaded main config.");
	}

	public MysqlDataSource getPlayersDatabase() {
		return playersDatabase;
	}

	public String getPlayersDatabaseTable() {
		return playersDatabaseTable;
	}

}
