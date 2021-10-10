package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ColorCommand extends PluginCommand {

    @Override
    public String getName() {
        return "color";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        if(!PluginConfigs.FEATURES.ENABLED.get("ChatNameColor")) {
            sender.sendMessage("That feature is disabled in this server.");
            return;
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            String teamColor = args[0];
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Team team = scoreboard.getTeam(teamColor);

            if (team == null) throw new CommandException("No color found for: " + teamColor);

            if (scoreboard.getEntryTeam(p.getName()) != null) {
                scoreboard.getEntryTeam(p.getName()).removeEntry(p.getName());
            }

            team.addEntry(p.getName());
            return;
        }
        throw new CommandException("Invalid Usage. See \"/color help\" for how to use this command.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return StringUtil.copyPartialMatches(args[0], new ArrayList<String>() {
            {
                Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(t -> {
                    add(t.getName());
                });
            }
        }, new ArrayList<>(Bukkit.getScoreboardManager().getMainScoreboard().getTeams().size()));
    }
}
