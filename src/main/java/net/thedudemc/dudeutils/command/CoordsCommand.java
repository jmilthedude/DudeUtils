package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordsCommand extends PluginCommand {

    @Override
    public String getName() {
        return "coords";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        Player player = (Player) sender;
        Location location = player.getLocation();
        if (location.getWorld() == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD).append(location.getBlockX());
        sb.append(ChatColor.WHITE).append(", ");
        sb.append(ChatColor.GOLD).append(location.getBlockY());
        sb.append(ChatColor.WHITE).append(", ");
        sb.append(ChatColor.GOLD).append(location.getBlockZ());
        sb.append(ChatColor.WHITE).append(" in ");
        sb.append(StringUtils.getDimensionName(location.getWorld().getEnvironment()));

        player.chat(sb.toString());
    }
}
