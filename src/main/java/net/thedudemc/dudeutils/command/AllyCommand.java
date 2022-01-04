package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.data.AllySaveData;
import net.thedudemc.dudeutils.features.ally.AllyFeature;
import net.thedudemc.dudeutils.init.PluginFeatures;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class AllyCommand extends PluginCommand {

    @Override
    public String getName() {
        return "ally";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        AllyFeature feature = (AllyFeature) PluginFeatures.getFeature(this.getName());
        if (!feature.isEnabled()) {
            throw new CommandException("The feature is not enabled on this server: " + this.getName());
        }
        AllySaveData data = (AllySaveData) feature.getSaveData();
        Player p = (Player) sender;
        if (args.length == 1) {
            if ("list".equalsIgnoreCase(args[0])) {
                List<UUID> allies = data.getAllies(p.getUniqueId());
                List<String> playerNames = allies.stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .map(HumanEntity::getName)
                        .collect(Collectors.toList());
                sendList(sender, playerNames);
                return;
            }
        } else if (args.length == 2) {
            Player ally = Bukkit.getPlayer(args[1]);
            if (ally == null) throw new CommandException("There was no player found by that name: " + args[1]);

            if ("add".equalsIgnoreCase(args[0])) {
                if (data.addAlly(p.getUniqueId(), ally.getUniqueId())) {
                    sender.sendMessage("Added " + ChatColor.YELLOW + ally.getName() + ChatColor.RESET + " to your allies.");
                } else {
                    sender.sendMessage("" + ChatColor.YELLOW + ally.getName() + ChatColor.RESET + " is already your ally.");
                }
                return;
            } else if ("remove".equalsIgnoreCase((args[0]))) {
                if (data.removeAlly(p.getUniqueId(), ally.getUniqueId())) {
                    sender.sendMessage("Removed " + ChatColor.YELLOW + ally.getName() + ChatColor.RESET + " from your allies.");
                } else {
                    sender.sendMessage("" + ChatColor.YELLOW + ally.getName() + ChatColor.RESET + " was not found as an ally.");
                }
                return;
            }
        }
        throw new CommandException("Invalid Usage.. See \"/ally help\" for how to use this command.");

    }

    private void sendList(CommandSender sender, List<String> allies) {
        if (allies.size() < 1) {
            sender.sendMessage("No Allied Players...");
        } else {
            sender.sendMessage("Allied Players:");
            for (String s : allies)
                sender.sendMessage(ChatColor.YELLOW + s);
        }
    }
}
