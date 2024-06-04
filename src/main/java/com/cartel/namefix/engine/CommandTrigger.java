package com.cartel.namefix.engine;

import java.sql.SQLException;

import com.cartel.namefix.Core;
import com.cartel.namefix.mysql.SQLiteManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTrigger implements CommandExecutor {
    public CommandTrigger(Core plugin) {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("cartelnamefix") && args.length == 1 && sender.hasPermission("cartel.namefix.admin")) {
            String removed = null;

            try {
                removed = SQLiteManager.Delete(args[0]);
            } catch (SQLException | ClassNotFoundException var7) {
                var7.printStackTrace();
                return true;
            }

            if (removed != null) {
                Core.map.remove(removed.toLowerCase());
                sender.sendMessage(removed + " Silindi!");
            }
        }

        return false;
    }
}
