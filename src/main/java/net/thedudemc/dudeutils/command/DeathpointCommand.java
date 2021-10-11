package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import net.thedudemc.dudeutils.init.PluginData;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathpointCommand extends PluginCommand {
    @Override
    public String getName() {
        return "deathpoint";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        Player player = (Player) sender;
        DeathLocation location = PluginData.DEATHPOINT.getDeathpoint(player);
        if (location == null) {
            sender.sendMessage("There is no recent deathpoint saved.");
            return;
        }
        sender.sendMessage("You died at: " + StringUtils.getCoordinateString(location));
    }
}
