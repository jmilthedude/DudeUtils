package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.data.GhastLogData;
import net.thedudemc.dudeutils.init.PluginData;
import net.thedudemc.dudeutils.util.chat.JSONMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GhastLogCommand extends PluginCommand {

    @Override
    public String getName() {
        return "ghast_log";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        List<GhastLogData.GhastSpawnLocation> locations = PluginData.GHAST_LOG.getLocations();
        locations.forEach(ghastSpawnLocation -> {
            JSONMessage message = ghastSpawnLocation.getCommandString(player.getName());
            message.send(player);
        });
    }


}
