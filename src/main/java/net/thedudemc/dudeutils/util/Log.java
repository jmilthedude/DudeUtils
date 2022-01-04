package net.thedudemc.dudeutils.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {
    public static void info(String msg) {
        Bukkit.getLogger().info(ChatColor.AQUA + "[DudeUtils] " + ChatColor.RESET + msg);
    }

    public static void error(String msg) {
        Bukkit.getLogger().info(ChatColor.AQUA + "[DudeUtils] " + ChatColor.RED + msg);
    }
}
