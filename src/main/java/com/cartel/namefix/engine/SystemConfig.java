package com.cartel.namefix.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import com.cartel.namefix.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SystemConfig {
    Core Plugin;
    public String fileName;
    private JavaPlugin plugin;
    public File ConfigFile;
    private FileConfiguration Configuration;

    public SystemConfig(Core Plugin) {
        this.Plugin = Plugin;
    }

    public SystemConfig(JavaPlugin plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin boş olamaz");
        } else {
            this.plugin = plugin;
            this.fileName = fileName;
            File dataFolder = plugin.getDataFolder();
            if (dataFolder == null) {
                throw new IllegalStateException();
            } else {
                this.ConfigFile = new File(dataFolder.toString() + File.separatorChar + this.fileName);
            }
        }
    }

    public void reloadConfig() {
        this.Configuration = YamlConfiguration.loadConfiguration(this.ConfigFile);
    }

    public FileConfiguration getConfig() {
        if (this.Configuration == null) {
            this.reloadConfig();
        }

        return this.Configuration;
    }

    public void saveConfig() {
        if (this.Configuration != null && this.ConfigFile != null) {
            try {
                this.getConfig().save(this.ConfigFile);
            } catch (IOException Cartel) {
                this.plugin.getLogger().log(Level.SEVERE, "Yapılandırma şuraya kaydedilemedi: " + this.ConfigFile, Cartel);
            }
        }
    }

    public void saveDefaultConfig() {
        if (!this.ConfigFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
    }
}
