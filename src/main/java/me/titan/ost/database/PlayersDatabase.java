package me.titan.ost.database;

import me.titan.ost.config.MainConfig;
import me.titan.ost.core.OSTPlugin;
import me.titan.ost.player.PlayerCache;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayersDatabase {

	public PlayersDatabase(){

		Bukkit.getScheduler().runTaskAsynchronously(OSTPlugin.instance,() -> initTable());
	}
	public void initTable(){
		OSTPlugin.instance.getLogger().info("Attempting to connect to database...");

		MainConfig config = OSTPlugin.instance.getMainConfig();

		String query = "CREATE TABLE IF NOT EXISTS " + config.getPlayersDatabaseTable() + "(" +
				"uuid CHAR(36)," +
				"bal DOUBLE(20,10) UNSIGNED," +
				"UNIQUE(uuid));";
		try(Connection c = config.getPlayersDatabase().getConnection();
			PreparedStatement st = c.prepareStatement(query)){

			if(!c.isValid(1)){
				OSTPlugin.instance.getLogger().warning("Unable to connect to database.");
				return;
			}

			st.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	public void saveCache(PlayerCache pc){
		MainConfig config = OSTPlugin.instance.getMainConfig();
		String query = "INSERT INTO " + config.getPlayersDatabaseTable() + "(uuid,bal) VALUES(?,?) ON DUPLICATE KEY UPDATE bal=?";
		try(Connection c = OSTPlugin.instance.getMainConfig().getPlayersDatabase().getConnection();
			PreparedStatement st = c.prepareStatement(query)){

			st.setString(1,pc.getUuid().toString());
			st.setDouble(2,pc.getBalance());
			st.setDouble(3,pc.getBalance());
			if(!c.isValid(1)){
				OSTPlugin.instance.getLogger().warning("Unable to connect to database.");
				return;
			}
			st.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void loadCache(UUID id, Consumer<PlayerCache> onFinish){
		MainConfig config = OSTPlugin.instance.getMainConfig();
		String query = "SELECT * FROM " + config.getPlayersDatabaseTable() + " WHERE uuid=?";
		try(Connection c = OSTPlugin.instance.getMainConfig().getPlayersDatabase().getConnection();
			PreparedStatement st = c.prepareStatement(query)){

			st.setString(1,id.toString());
			if(!c.isValid(1)){
				OSTPlugin.instance.getLogger().warning("Unable to connect to database.");
				return;
			}
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				PlayerCache pc = new PlayerCache(id);
				pc.setBalance(rs.getDouble(2));
				onFinish.accept(pc);
				return;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void addBalance(UUID id, double add){
		MainConfig config = OSTPlugin.instance.getMainConfig();
		String query = "INSERT INTO " + config.getPlayersDatabaseTable() + "(uuid,bal) VALUES(?,?) ON DUPLICATE KEY UPDATE bal=bal+?";
		try(Connection c = OSTPlugin.instance.getMainConfig().getPlayersDatabase().getConnection();
			PreparedStatement st = c.prepareStatement(query)){

			st.setString(1,id.toString());
			st.setDouble(2,add);
			st.setDouble(3,add);
			if(!c.isValid(1)){
				OSTPlugin.instance.getLogger().warning("Unable to connect to database.");
				return;
			}
			st.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	public void setBalance(UUID id, double bal){
		MainConfig config = OSTPlugin.instance.getMainConfig();
		String query = "INSERT INTO " + config.getPlayersDatabaseTable() + "(uuid,bal) VALUES(?,?) ON DUPLICATE KEY UPDATE bal=?";
		try(Connection c = OSTPlugin.instance.getMainConfig().getPlayersDatabase().getConnection();
			PreparedStatement st = c.prepareStatement(query)){

			st.setString(1,id.toString());
			st.setDouble(2,bal);
			st.setDouble(3,bal);
			if(!c.isValid(1)){
				OSTPlugin.instance.getLogger().warning("Unable to connect to database.");
				return;
			}
			st.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}


}
