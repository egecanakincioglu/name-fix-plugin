package com.cartel.namefix.engine;

import java.sql.SQLException;

import com.cartel.namefix.Core;
import com.cartel.namefix.mysql.SQLiteManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerListener implements Listener {
    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onPlayerLogin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        if (Core.map.containsKey(player.getName().toLowerCase())) {
            String name = (String)Core.map.get(player.getName().toLowerCase());
            if (!name.equals(player.getName())) {
                event.disallow(Result.KICK_OTHER, ChatColor.DARK_AQUA + player.getName() + ChatColor.GREEN + "ismiyle sunucuya bağlanmaya çalışıyorsunuz fakat orjinal isim şu şekilde: " + ChatColor.DARK_RED + name);
            }
        } else {
            Core.map.put(player.getName().toLowerCase(), player.getName());
            Bukkit.getScheduler().runTaskAsynchronously(Core.instance, new Runnable() {
                public void run() {
                    try {
                        SQLiteManager.recordNew(player);
                    } catch (ClassNotFoundException var2) {
                        var2.printStackTrace();
                    } catch (SQLException var3) {
                        var3.printStackTrace();
                    }

                }
            });
        }

    }
}
