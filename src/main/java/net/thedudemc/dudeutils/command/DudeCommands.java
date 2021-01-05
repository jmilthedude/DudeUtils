package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.allies.AllyGroup;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DudeCommands implements CommandExecutor {

    public void initCommands() {
        DudeUtils.INSTANCE.getCommand("dude").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("slime").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("ally").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (command.getName().equalsIgnoreCase(("slime"))) {
                if (args.length == 0) {
                    Chunk chunk = p.getWorld().getChunkAt(p.getLocation());
                    if (chunk.isSlimeChunk()) {
                        p.sendMessage("You " + ChatColor.GREEN + "ARE " + ChatColor.RESET + "standing in a " + ChatColor.GREEN + "SLIME CHUNK!" + ChatColor.RESET);
                    } else {
                        p.sendMessage("You " + ChatColor.RED + "ARE NOT " + ChatColor.RESET + "standing in a " + ChatColor.GREEN + "SLIME CHUNK!" + ChatColor.RESET);
                    }
                }
            } else if (command.getName().equalsIgnoreCase("dude")) {
                if (args.length == 0)
                    sender.sendMessage(ChatColor.GREEN + "Dude!");
                else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("save")) {
                        if (p.isOp()) {
                            PluginData.save();
                        }
                    }
                }
            } else if (command.getName().equalsIgnoreCase("ally")) {
                AllyGroup group = PluginData.ALLY_DATA.getAllyGroup(p.getName());
                if (group == null) {
                    group = new AllyGroup(p.getName());
                }
                if (args.length == 1) {
                    if ("list".equalsIgnoreCase(args[0])) {
                        List<String> allies = group.getAllies();
                        if (allies.size() < 1) {
                            sender.sendMessage("No Allied Players...");
                            return true;
                        }
                        sender.sendMessage("Allied Players:");
                        for (String s : allies)
                            sender.sendMessage(ChatColor.YELLOW + s);
                        return true;
                    }
                } else if (args.length == 2) {
                    String ally = args[1];
                    if ("add".equalsIgnoreCase(args[0])) {
                        group.addAlliedPlayer(ally);
                        sender.sendMessage("Added " + ChatColor.YELLOW + ally + ChatColor.RESET + " to your allies.");
                    } else if ("remove".equalsIgnoreCase(args[0])) {
                        group.removeAlliedPlayer(ally);
                        sender.sendMessage("Removed " + ChatColor.YELLOW + ally + ChatColor.RESET + " from your allies.");
                    }
                    PluginData.ALLY_DATA.addAllyGroup(group);
                } else {
                    sender.sendMessage("Usage:");
                    sender.sendMessage("\"/ally add <PlayerName>\"");
                    sender.sendMessage("\"/ally remove <PlayerName>\"");
                }
            }
        }
        return true;
    }

}
