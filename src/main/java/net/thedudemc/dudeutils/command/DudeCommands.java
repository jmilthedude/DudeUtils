package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.config.VeinMinerConfig;
import net.thedudemc.dudeutils.features.allies.AllyGroup;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DudeCommands implements CommandExecutor, TabCompleter {

    private static List<String> colors = new ArrayList<>();

    public void initCommands() {
        DudeUtils.INSTANCE.getCommand("dude").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("slime").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("ally").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("color").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("veinminer").setExecutor(this);
        DudeUtils.INSTANCE.getCommand("color").setTabCompleter(this);

        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(t -> {
            colors.add(t.getName());
        });
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
            } else if (command.getName().equalsIgnoreCase("color")) {
                if (args.length == 1) {
                    String teamColor = args[0];
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                    Team team = scoreboard.getTeam(teamColor);
                    if (team == null) {
                        sender.sendMessage("Invalid Color Name...");
                        return true;
                    }
                    if (scoreboard.getEntryTeam(p.getName()) != null) {
                        scoreboard.getEntryTeam(p.getName()).removeEntry(p.getName());
                    }
                    team.addEntry(p.getName());
                }
            } else if (command.getName().equalsIgnoreCase("veinminer")) {
                if (p.isOp()) {
                    ItemStack heldItem = p.getInventory().getItemInMainHand();
                    VeinMinerConfig.applyOrRemoveVeinMinerUpgrade(heldItem);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("color")) {
            return StringUtil.copyPartialMatches(args[0], colors, new ArrayList<>(colors.size()));
        }
        return null;
    }
}
