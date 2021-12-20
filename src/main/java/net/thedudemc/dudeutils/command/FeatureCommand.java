package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.command.exception.InvalidUsageCommandException;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.init.PluginFeatures;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureCommand extends PluginCommand {
    @Override
    public String getName() {
        return "feature";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) throw new InvalidUsageCommandException(this.getName());

        String featureName = args[0];
        Feature feature = PluginFeatures.getFeature(featureName);

        boolean enable = Boolean.parseBoolean(args[1]);
        if (enable) {
            feature.enable();
            sender.sendMessage(featureName + " has been enabled.");
        } else {
            feature.disable();
            sender.sendMessage(featureName + " has been disabled.");
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(PluginFeatures.getNames());
        } else if (args.length == 2) {
            return Arrays.asList("true", "false");
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
