package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.features.chatnamecolor.ChatNameColorFeature;
import net.thedudemc.dudeutils.init.PluginFeatures;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        if (!PluginFeatures.CHAT_NAME_COLOR.isEnabled()) {
            throw new CommandException("That feature is not enabled on this server.");
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            String nameColor = args[0];
            ChatColor color = ChatNameColorFeature.getColor(nameColor);

            if (color == ChatColor.RESET) throw new CommandException("No color found for: " + nameColor);

            PluginFeatures.CHAT_NAME_COLOR.setNameColor(p, nameColor);
            p.setPlayerListName(color + p.getName());
            return;
        }
        throw new CommandException("Invalid Usage. See \"/color help\" for how to use this command.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> names = ChatNameColorFeature.getColorNames();

        return StringUtil.copyPartialMatches(args[0], names, new ArrayList<>(names.size()));
    }
}
