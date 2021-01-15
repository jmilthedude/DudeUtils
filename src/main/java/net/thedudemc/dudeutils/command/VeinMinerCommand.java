package net.thedudemc.dudeutils.command;

import net.thedudemc.dudeutils.command.exception.CommandException;
import net.thedudemc.dudeutils.config.VeinMinerConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VeinMinerCommand extends PluginCommand {
    @Override
    public String getName() {
        return "veinminer";
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {

        Player p = (Player) sender;
        ItemStack heldItem = p.getInventory().getItemInMainHand();
        VeinMinerConfig.applyOrRemoveVeinMinerUpgrade(heldItem);
    }
}
