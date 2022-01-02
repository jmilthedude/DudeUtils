package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.command.exception.InvalidUsageCommandException;
import net.thedudemc.dudeutils.data.DeathpointData;
import net.thedudemc.dudeutils.features.deathpoint.DeathHistory;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
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
        } else if (args.length >= 3) {
            if ("restore".equalsIgnoreCase(args[0])) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    throw new CommandException("No player found by that name.");
                }
                DeathHistory history = data.getDeathHistory(target);

                int index = Integer.parseInt(args[2]);
                if (index >= history.getDeathpoints().size()) {
                    throw new CommandException("There are only " + history.getDeathpoints().size() + " deathpoint(s).");
                }

                DeathLocation deathData = history.getDeathpoints().get(index);

                if (args.length == 3) {
                    dropExistingItems(target);
                    target.getInventory().setContents(deathData.getInventory());
                    sender.sendMessage("Death contents placed into the player's inventory.");
                } else if (args.length == 4) {
                    if ("dump".equalsIgnoreCase(args[3])) {
                        Block block = player.getTargetBlockExact(8);
                        if (block == null) {
                            throw new CommandException("No block found in view. Maybe get closer.");
                        }
                        if (!(block.getState() instanceof Chest)) {
                            throw new CommandException("You are not targeting a chest.");
                        }
                        Chest chest = (Chest) block.getState();
                        if (!(chest.getInventory() instanceof DoubleChestInventory)) {
                            throw new CommandException("You must be looking at a double chest.");
                        }
                        if (!chest.getInventory().isEmpty()) {
                            throw new CommandException("The chest must be empty.");
                        }
                        chest.getInventory().setContents(deathData.getInventory());
                        sender.sendMessage("Death contents placed into the chest.");
                        return;
                    }

                }
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
