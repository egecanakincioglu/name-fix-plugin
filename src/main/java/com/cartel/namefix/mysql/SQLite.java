package com.cartel.namefix.mysql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

public class SQLite extends Database {
    private final String dbLocation;

    public SQLite(Plugin plugin, String dbLocation) {
        super(plugin);
        this.dbLocation = dbLocation;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (this.checkConnection()) {
            return this.connection;
        } else {
            if (!this.plugin.getDataFolder().exists()) {
                this.plugin.getDataFolder().mkdirs();
            }

            File file = new File(this.plugin.getDataFolder(), this.dbLocation);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException var3) {
                    this.plugin.getLogger().log(Level.SEVERE, "Veritabanı oluşturulamıyor!");
                }
            }

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.plugin.getDataFolder().toPath().toString() + "/" + this.dbLocation);
            return this.connection;
        }
    }
}
