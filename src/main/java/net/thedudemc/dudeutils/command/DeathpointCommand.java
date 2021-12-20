package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.command.exception.InvalidUsageCommandException;
import net.thedudemc.dudeutils.data.DeathpointData;
import net.thedudemc.dudeutils.features.deathpoint.DeathHistory;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
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
        DeathpointData data = this.getFeature().getSaveData();

        if (args.length == 0) {
            DeathLocation location = data.getLatestDeathLocation(player);
            if (location == null) {
                sender.sendMessage("There is no recent deathpoint saved.");
                return;
            }
            sender.sendMessage("You last died at: " + StringUtils.getCoordinateString(location));
            return;
        } else if (args.length == 1) {
            if ("list".equalsIgnoreCase(args[0])) {
                DeathHistory history = data.getDeathHistory(player);
                if (history.getDeathpoints().isEmpty()) sender.sendMessage("There is no recent deathpoint saved.");
                sender.sendMessage("Recent Deathpoints:");
                history.getDeathpoints().forEach(location -> sender.sendMessage(location.toString()));
                return;
            }
        }
        throw new InvalidUsageCommandException(this.getName());
    }
}
