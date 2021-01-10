package net.thedudemc.dudeutils.event;

import org.bukkit.*;
import org.bukkit.block.Block;
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

    public static final HashSet<Material> ORES = new HashSet<Material>() {
        {
            add(Material.COAL_ORE);
            add(Material.DIAMOND_ORE);
            add(Material.EMERALD_ORE);
            add(Material.IRON_ORE);
            add(Material.GOLD_ORE);
            add(Material.LAPIS_ORE);
            add(Material.REDSTONE_ORE);
            add(Material.NETHER_GOLD_ORE);
            add(Material.NETHER_QUARTZ_ORE);
            add(Material.NETHERITE_SCRAP);
        }
    };

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().isSneaking() || event.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = block.getWorld();
        Location location = block.getLocation();

        if (!ORES.contains(block.getType())) {
            return;
        }

        if (mineBlocks(player, world, block.getType(), location, 50)) {
            event.setCancelled(true);
        }
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
        itemDrops.forEach(stack -> world.dropItemNaturally(player.getLocation(), stack));
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
