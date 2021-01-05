package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.features.portal.PortalHelper;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PortalEvents implements Listener {

    public static HashMap<Player, Long> timers = new HashMap<>();


    @EventHandler
    public void onPortalClicked(PlayerInteractEvent event) {
        if (!PluginConfigs.PORTAL_UTILITY.ENABLE) {
            return;
        }
        if (event.getHand() == null || event.getHand() != EquipmentSlot.HAND)
            return;

        Player player = event.getPlayer();

        if (!player.isSneaking())
            return;

        ItemStack stack = event.getItem();

        if (stack == null || !stack.getType().equals(Material.FLINT_AND_STEEL))
            return;

        Block block = event.getClickedBlock();

        if (block == null || block.getType() != Material.OBSIDIAN)
            return;
        else {
            if (PortalHelper.portals.containsKey(player)) {
                Location location = PortalHelper.portals.remove(player);
                event.setCancelled(true);
                player.sendMessage("Removed particles at: " + StringUtils.getCoordinateString(location));
                return;
            }
        }

        event.setCancelled(true);

        Location current = block.getLocation();

        if (current.getWorld().getEnvironment() == Environment.THE_END)
            return;

        Location alternate = getAlternateDimensionLocation(current);

        player.sendMessage(StringUtils.getDimensionName(alternate.getWorld().getEnvironment()) + " Portal Location: " + StringUtils.getCoordinateString(alternate));

        PortalHelper.portals.put(player, alternate);
        timers.put(player, 300L);

    }


    private Location getAlternateDimensionLocation(Location current) {
        if (current.getWorld().getEnvironment() == Environment.NORMAL) {
            int x = current.getBlockX() / 8;
            int y = current.getBlockY();
            int z = current.getBlockZ() / 8;
            return new Location(Bukkit.getWorlds().get(1), x, y, z);
        } else {
            int x = current.getBlockX() * 8;
            int y = current.getBlockY();
            int z = current.getBlockZ() * 8;
            return new Location(Bukkit.getWorlds().get(0), x, y, z);

        }
    }

}
