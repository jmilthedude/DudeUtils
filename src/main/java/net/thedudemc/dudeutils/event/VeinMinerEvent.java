package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.config.VeinMinerConfig;
import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class VeinMinerEvent implements Listener {

    private static final Set<UUID> mining = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (mining.contains(player.getUniqueId())) return;

        if (!player.isSneaking() || player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (!canVeinMine(heldItem)) return;

        Block block = event.getBlock();
        if (!isVeinMineable(block)) return;

        if (mine(block, player)) {
            event.setCancelled(true);
        }
    }

    private boolean isVeinMineable(Block block) {
        return PluginConfigs.VEINMINER.getMaterials().contains(block.getType());
    }

    private boolean mine(Block block, Player player) {
        mining.add(player.getUniqueId());
        World world = block.getWorld();
        Material material = block.getType();
        int limit = PluginConfigs.VEINMINER.getBlockLimit();

        player.breakBlock(block);
        int traversedBlocks = 0;
        Queue<Location> locationQueue = new LinkedList<>();

        locationQueue.add(block.getLocation());
        traversedBlocks++;

        floodLoop:
        while (!locationQueue.isEmpty()) {
            Location headLocation = locationQueue.poll();

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) continue;
                        if (traversedBlocks >= limit) break floodLoop;
                        Location currentLocation = new Location(world,
                                headLocation.getBlockX() + x,
                                headLocation.getBlockY() + y,
                                headLocation.getBlockZ() + z
                        );

                        Block toBreak = world.getBlockAt(currentLocation);

                        if (toBreak.getType() == material) {
                            locationQueue.add(currentLocation);
                            if (!player.breakBlock(toBreak)) {
                                break floodLoop;
                            }
                            traversedBlocks++;
                            player.setExhaustion(player.getExhaustion() + PluginConfigs.VEINMINER.getExhaustion());
                        }
                    }
                }
            }
        }
        mining.remove(player.getUniqueId());
        return true;

    }

    private boolean canVeinMine(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            PersistentDataContainer data = meta.getPersistentDataContainer();
            Byte value = data.get(VeinMinerConfig.KEY, PersistentDataType.BYTE);
            if (value != null) {
                return value != (byte) 0;
            }
        }
        return false;
    }

}
