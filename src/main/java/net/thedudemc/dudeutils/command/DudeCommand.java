package net.thedudemc.dudeutils.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DudeCommand extends PluginCommand {

    @Override
    public String getName() {
        return "dude";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "Dude!");
    }


}
