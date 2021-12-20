package net.thedudemc.dudeutils.util;

import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import org.apache.commons.lang.time.DurationFormatUtils;
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

    public static String getCoordinateString(DeathLocation location) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD).append(location.getX());
        sb.append(ChatColor.WHITE).append(", ");
        sb.append(ChatColor.GOLD).append(location.getY());
        sb.append(ChatColor.WHITE).append(", ");
        sb.append(ChatColor.GOLD).append(location.getZ());
        sb.append(ChatColor.WHITE).append(" in ");
        sb.append(StringUtils.getDimensionName(World.Environment.valueOf(location.getDimension().toUpperCase())));
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

    public static String formatDuration(long time) {
        return DurationFormatUtils.formatDuration(time, "dd:HH:mm:ss");
    }

}
