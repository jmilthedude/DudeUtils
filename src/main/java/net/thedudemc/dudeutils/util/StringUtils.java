package net.thedudemc.dudeutils.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class StringUtils {


    public static String getCoordinateString(Location location) {

        StringBuilder sb = new StringBuilder();

        sb.append("" +
                ChatColor.YELLOW + location.getBlockX() +
                ChatColor.GRAY + "x" +
                ChatColor.RESET + ", " +
                ChatColor.YELLOW + location.getBlockY() +
                ChatColor.GRAY + "y" +
                ChatColor.RESET + ", " +
                ChatColor.YELLOW + location.getBlockZ() +
                ChatColor.GRAY + "z");

        return sb.toString();

    }

    public static String getDimensionName(World.Environment environment) {
        switch (environment) {
            case NETHER:
                return ChatColor.DARK_RED + "Nether" + ChatColor.RESET;
            case THE_END:
                return ChatColor.DARK_PURPLE + "End" + ChatColor.RESET;
            default:
                return ChatColor.DARK_GREEN + "Overworld" + ChatColor.RESET;
        }
    }

}
