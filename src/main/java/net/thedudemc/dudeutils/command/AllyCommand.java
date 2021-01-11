package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.features.allies.AllyGroup;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AllyCommand extends PluginCommand {

    @Override
    public String getName() {
        return "ally";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        Player p = (Player) sender;
        AllyGroup group = PluginData.ALLY_DATA.getOrCreateGroup(p.getName());
        if (args.length == 1) {
            if ("list".equalsIgnoreCase(args[0])) {
                sendList(sender, group);
                return;
            }
        } else if (args.length == 2) {
            addOrRemoveAlly(sender, args, group);
            return;
        }
        throw new CommandException("Invalid Usage.. See \"/ally help\" for how to use this command.");
    }

    private void addOrRemoveAlly(CommandSender sender, String[] args, AllyGroup group) {
        String ally = args[1];
        if ("add".equalsIgnoreCase(args[0])) {
            group.addAlliedPlayer(ally);
            sender.sendMessage("Added " + ChatColor.YELLOW + ally + ChatColor.RESET + " to your allies.");
        } else if ("remove".equalsIgnoreCase(args[0])) {
            group.removeAlliedPlayer(ally);
            sender.sendMessage("Removed " + ChatColor.YELLOW + ally + ChatColor.RESET + " from your allies.");
        }
        PluginData.ALLY_DATA.addAllyGroup(group);
    }

    private void sendList(CommandSender sender, AllyGroup group) {
        List<String> allies = group.getAllies();
        if (allies.size() < 1) {
            sender.sendMessage("No Allied Players...");
        } else {
            sender.sendMessage("Allied Players:");
            for (String s : allies)
                sender.sendMessage(ChatColor.YELLOW + s);
        }
    }
}
