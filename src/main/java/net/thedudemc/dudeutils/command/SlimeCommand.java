package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlimeCommand extends PluginCommand {

    @Override
    public String getName() {
        return "slime";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!PluginConfigs.FEATURES.ENABLED.get("SlimeChunkCheck")) {
            sender.sendMessage("That feature is disabled in this server.");
            return;
        }
        if (args.length == 0) {
            Player p = (Player) sender;
            Chunk chunk = p.getWorld().getChunkAt(p.getLocation());
            if (chunk.isSlimeChunk()) {
                p.sendMessage("You " + ChatColor.GREEN + "ARE " + ChatColor.RESET + "standing in a " + ChatColor.GREEN + "SLIME CHUNK!" + ChatColor.RESET);
            } else {
                p.sendMessage("You " + ChatColor.RED + "ARE NOT " + ChatColor.RESET + "standing in a " + ChatColor.GREEN + "SLIME CHUNK!" + ChatColor.RESET);
            }
        }
    }
}
