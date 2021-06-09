package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.command.CommandSender;

public class SaveCommand extends PluginCommand {

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            PluginData.save();
        }
    }
}
