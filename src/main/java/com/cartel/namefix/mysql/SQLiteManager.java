package com.cartel.namefix.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.cartel.namefix.Core;
import org.bukkit.entity.Player;

public class SQLiteManager {
    public static SQLite Cartel;

    public SQLiteManager(Core Core) {
    }

    public static void setupDB() throws SQLException {
        Cartel = new SQLite(Core.instance, "Cartelizm.db");

        try {
            Cartel.openConnection();
        } catch (ClassNotFoundException var1) {
            var1.printStackTrace();
        }

        Statement statement = Cartel.getConnection().createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `CartelNameFix` (`uuid` varchar(36) , `name` varchar(17), PRIMARY KEY (uuid))");
        statement.close();
    }

    public void closeDB() {
        try {
            Cartel.closeConnection();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public static void GetAllNames() throws ClassNotFoundException, SQLException {
        if (!Cartel.checkConnection()) {
            Cartel.openConnection();
        }

        Statement statement = Cartel.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `CartelNameFix`;");

        while(rs.next()) {
            String name = rs.getString("name");
            Core.map.put(name.toLowerCase(), name);
        }

        statement.close();
    }

    public static void recordNew(Player player) throws ClassNotFoundException, SQLException {
        UUID PlayerUUID = player.getUniqueId();
        if (!Cartel.checkConnection()) {
            Cartel.openConnection();
        }

        Statement statement = Cartel.getConnection().createStatement();
        statement.executeUpdate("INSERT INTO `CartelNameFix` (`uuid`, `name`) VALUES ('" + PlayerUUID + "','" + player.getName() + "');");
        statement.close();
    }

    public static String DiffrentName(Player player) throws ClassNotFoundException, SQLException {
        UUID PlayerUUID = player.getUniqueId();
        if (!Cartel.checkConnection()) {
            Cartel.openConnection();
        }

        Statement statement = Cartel.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM `CartelNameFix` WHERE LOWER(`name`)= LOWER('" + player.getName() + "');");
        if (rs.next()) {
            String name = rs.getString("name");
            if (!name.equals(player.getName()) && name.equalsIgnoreCase(player.getName())) {
                return name;
            } else if (name.equalsIgnoreCase(player.getName())) {
                return null;
            } else {
                statement.close();
                return null;
            }
        } else {
            statement.executeUpdate("INSERT INTO `CartelNameFix` (`uuid`, `name`) VALUES ('" + PlayerUUID + "','" + player.getName() + "');");
            return null;
        }
    }

    public static String Delete(String name) throws ClassNotFoundException, SQLException {
        if (Core.map.containsKey(name.toLowerCase())) {
            String Originalname = (String)Core.map.get(name.toLowerCase());
            if (!Cartel.checkConnection()) {
                Cartel.openConnection();
            }

            PreparedStatement prest = Cartel.getConnection().prepareStatement("DELETE FROM `CartelNameFix` WHERE `name` = ?;");
            prest.setString(1, Originalname);
            prest.execute();
            prest.close();
            return Originalname;
        } else {
            return null;
        }
    }
}
