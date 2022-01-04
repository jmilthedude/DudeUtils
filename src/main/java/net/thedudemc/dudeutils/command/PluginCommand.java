package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.init.PluginFeatures;
import net.thedudemc.dudeutils.util.Log;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class PluginCommand implements CommandExecutor, TabCompleter {

    private boolean opOnly = false;
    private boolean playerCommand = false;

    public abstract String getName();

    public PluginCommand() {
    }

    public PluginCommand opOnly() {
        this.opOnly = true;
        return this;
    }

    public PluginCommand playerOnly() {
        this.playerCommand = true;
        return this;
    }

    public boolean isOpOnly() {
        return this.opOnly;
    }

    public boolean canExecute(CommandSender sender) throws CommandException {
        if (this.playerCommand && !(sender instanceof Player)) {
            throw new CommandException("This command can only be run by a player.");
        }
        if (this.opOnly && !sender.isOp()) {
            throw new CommandException("You do not have permission to run this command.");
        }
        return true;
    }

    public abstract void execute(CommandSender sender, String[] args) throws CommandException;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (this.canExecute(sender)) this.execute(sender, args);
        } catch (CommandException | RuntimeException ex) {
            sender.sendMessage(ChatColor.RED + ex.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    protected Feature getFeature() throws CommandException {
        try {
            return PluginFeatures.getFeature(this.getName());
        } catch (Exception ex) {
            Log.error(ex.getMessage());
            throw new CommandException("There was an error running the command. Check the console for details.");
        }
    }

}
