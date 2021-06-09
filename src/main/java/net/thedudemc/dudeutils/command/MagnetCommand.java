package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.features.magnet.MagnetHelper;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MagnetCommand extends PluginCommand {

    @Override
    public String getName() {
        return "magnet";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        Player p = (Player) sender;
        if (args.length == 1) {
            boolean enable = Boolean.parseBoolean(args[0]);
            if (enable) PluginData.MAGNET_DATA.addPlayer(p);
            else PluginData.MAGNET_DATA.removePlayer(p);
            return;
        }
        throw new CommandException("Invalid Usage. See \"/magnet help\" for how to use this command.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return StringUtil.copyPartialMatches(args[0], new ArrayList<String>() {
            {
                add("true");
                add("false");
            }
        }, new ArrayList<>(2));
    }
}
