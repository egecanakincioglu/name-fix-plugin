package com.cartel.namefix;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import com.cartel.namefix.engine.CommandTrigger;
import com.cartel.namefix.engine.PlayerListener;
import com.cartel.namefix.mysql.SQLiteManager;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private CommandTrigger myExecutor;
    public final Logger logger = Logger.getLogger("Minecraft");
    public static Core plugin;
    public static Plugin instance;
    public final PlayerListener pl = this.PlayerListener(this);
    public final SQLiteManager SQLite = new SQLiteManager(this);
    public String PluginName = this.getDescription().getName();
    public String PluginVersion = this.getDescription().getVersion();
    public static boolean ConnectedSQLite = true;
    public static HashMap<String, String> map = new HashMap();

    private PlayerListener PlayerListener(Core core) {
        return null;
    }

    public void onEnable() {
        instance = this;
        this.myExecutor = new CommandTrigger(this);
        this.getCommand("cartelnamefix").setExecutor(this.myExecutor);
        ConsoleCommandSender console = this.getServer().getConsoleSender();
        PluginDescriptionFile pdfFile = this.getDescription();
        console.sendMessage(ChatColor.RED + pdfFile.getName() + " Versiyon " + pdfFile.getVersion() + " başarıyla başlatıldı!");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);

        try {
            SQLiteManager.setupDB();
            console.sendMessage(ChatColor.GREEN + "SQLite veritabanı bağlantısı kuruluyor...");
        } catch (SQLException var7) {
            ConnectedSQLite = false;
            console.sendMessage(ChatColor.DARK_RED + "SQLite veritabanına bağlanılamadı");
        }

        try {
            SQLiteManager.GetAllNames();
        } catch (ClassNotFoundException var5) {
            var5.printStackTrace();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }

    public void onDisable() {
        this.SQLite.closeDB();
        this.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "CartelNameFix devre dışı bırakıldı");
    }
}
