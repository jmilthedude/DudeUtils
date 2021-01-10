package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class VeinMinerEvent implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!PluginConfigs.VEINMINER.isEnabled()) return;
        Player player = event.getPlayer();

        if (!player.isSneaking() || player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (!canVeinMine(heldItem)) return;

        Block block = event.getBlock();
        World world = block.getWorld();
        Location location = block.getLocation();

        if (!PluginConfigs.VEINMINER.getMaterials().contains(block.getType())) {
            return;
        }

        if (mineBlocks(player, world, block.getType(), location, PluginConfigs.VEINMINER.getBlockLimit())) {
            event.setCancelled(true);
        }
    }

    private boolean canVeinMine(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                return lore.contains("VeinMiner!");
            }
        }
        return false;
    }

    private boolean mineBlocks(Player player, World world, Material material, Location location, int limit) {
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta meta = heldItem.getItemMeta();
        Damageable item = (Damageable) meta;

        if (item != null)
            if (item.getDamage() >= heldItem.getType().getMaxDurability()) return false;

        int traversedBlocks = 0;
        List<ItemStack> itemDrops = new LinkedList<>();
        Queue<Location> locationQueue = new LinkedList<>();

        itemDrops.addAll(destroyBlockAs(world, player, location));
        if (itemDrops.isEmpty()) return false;

        locationQueue.add(location);
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

                        if (world.getBlockAt(currentLocation).getType() == material) {
                            itemDrops.addAll(destroyBlockAs(world, player, currentLocation));
                            locationQueue.add(currentLocation);
                            traversedBlocks++;
                        }
                    }
                }
            }
        }
        itemDrops.forEach(stack -> {
            Item droppedItem = world.dropItemNaturally(player.getLocation(), stack);
            droppedItem.setPickupDelay(0);
        });
        return true;

    }

    private Collection<? extends ItemStack> destroyBlockAs(World world, Player player, Location location) {
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta meta = heldItem.getItemMeta();
        Damageable item = (Damageable) meta;

        if (item != null) {
            if (item.getDamage() >= heldItem.getType().getMaxDurability()) {
                heldItem.setAmount(0);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                return Collections.emptyList();
            }

            item.setDamage(item.getDamage() + 1);
            heldItem.setItemMeta(meta);
        }

        Collection<ItemStack> drops = world.getBlockAt(location).getDrops(heldItem);
        world.getBlockAt(location).setType(Material.AIR);
        return drops;
    }
}
