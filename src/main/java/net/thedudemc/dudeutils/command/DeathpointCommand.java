package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.command.exception.InvalidUsageCommandException;
import net.thedudemc.dudeutils.data.DeathpointData;
import net.thedudemc.dudeutils.features.deathpoint.DeathHistory;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class DeathpointCommand extends PluginCommand {
    @Override
    public String getName() {
        return "deathpoint";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        Player player = (Player) sender;
        DeathpointData data = this.getFeature().getSaveData();

        if (args.length == 0) {
            DeathLocation location = data.getLatestDeathLocation(player);
            if (location == null) {
                sender.sendMessage("There is no recent deathpoint saved.");
                return;
            }
            sender.sendMessage("You last died at: " + StringUtils.getCoordinateString(location));
            return;
        } else if (args.length == 1) {
            if ("list".equalsIgnoreCase(args[0])) {
                DeathHistory history = data.getDeathHistory(player);
                if (history.getDeathpoints().isEmpty()) sender.sendMessage("There is no recent deathpoint saved.");
                sender.sendMessage("Recent Deathpoints:");
                history.getDeathpoints().forEach(location -> sender.sendMessage(ChatColor.GRAY + "Index " + ChatColor.YELLOW + history.getDeathpoints().indexOf(location) + ChatColor.WHITE + ": " + location.toString()));
                return;
            }

            //deathpoint restore <player> <index>
        } else if (args.length == 3) {
            if ("restore".equalsIgnoreCase(args[0])) {

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    throw new CommandException("No player found by that name.");
                }
                DeathHistory history = data.getDeathHistory(target);

                int index = Integer.parseInt(args[2]) - 1;
                if (index + 1 > history.getDeathpoints().size()) {
                    throw new CommandException("There are only " + history.getDeathpoints().size() + " deathpoints.");
                }

                DeathLocation deathData = history.getDeathpoints().get(index);
                dropExistingItems(target);
                target.getInventory().setContents(deathData.getInventory());
                return;
            }
        }
        throw new InvalidUsageCommandException(this.getName());
    }

    private void dropExistingItems(Player target) {
        ItemStack[] contents = target.getInventory().getContents();
        Arrays.stream(contents).filter(Objects::nonNull).forEach(itemStack -> target.getWorld().dropItem(target.getLocation(), itemStack));
    }
}
