package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.config.VeinMinerConfig;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.util.MathUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class VeinMinerEvent implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
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
            PersistentDataContainer data = meta.getPersistentDataContainer();
            Byte value = data.get(VeinMinerConfig.KEY, PersistentDataType.BYTE);
            if (value != null) {
                return value != (byte) 0;
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
        Queue<Location> locationQueue = new LinkedList<>();
        boolean dropAtPlayer = PluginConfigs.VEINMINER.shouldDropAtPlayer();

        List<ItemStack> itemDrops = new LinkedList<>(destroyBlockAs(world, player, location));
        if (itemDrops.isEmpty()) return false;
        if (!dropAtPlayer) itemDrops.forEach(itemStack -> world.dropItemNaturally(location, itemStack));

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
                            Collection<? extends ItemStack> drops = destroyBlockAs(world, player, currentLocation);
                            if (drops.isEmpty()) break floodLoop;

                            int xp = getExperience(material);
                            if (!heldItem.getEnchantments().containsKey(Enchantment.SILK_TOUCH) && xp != 0) {
                                world.spawn(location, ExperienceOrb.class, experienceOrb -> experienceOrb.setExperience(xp));
                            }

                            if (dropAtPlayer) itemDrops.addAll(drops);
                            else drops.forEach(itemStack -> world.dropItemNaturally(currentLocation, itemStack));
                            locationQueue.add(currentLocation);
                            traversedBlocks++;
                            player.setExhaustion(player.getExhaustion() + PluginConfigs.VEINMINER.getExhaustion());
                        }
                    }
                }
            }
        }
        if (dropAtPlayer) {
            itemDrops.forEach(stack -> {
                Item droppedItem = world.dropItemNaturally(player.getLocation(), stack);
                droppedItem.setPickupDelay(0);
            });
        }
        return true;

    }

    private Collection<? extends ItemStack> destroyBlockAs(World world, Player player, Location location) {
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta meta = heldItem.getItemMeta();
        Damageable item = (Damageable) meta;

        if (item != null) {
            int damage = PluginConfigs.VEINMINER.getDamagePerBlock();

            float damageReductionChance = 1.0f;
            int unbLvl = heldItem.getEnchantmentLevel(Enchantment.DURABILITY);
            if (unbLvl > 0) {
                damageReductionChance = (100f / (float) (unbLvl + 1)) / 100f;
            }
            if (Math.random() < damageReductionChance) {
                if ((item.getDamage() + damage) >= heldItem.getType().getMaxDurability()) {
                    heldItem.setAmount(0);
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    return Collections.emptyList();
                } else {
                    item.setDamage(item.getDamage() + damage);
                    heldItem.setItemMeta(meta);
                }
            }
        }
        Collection<ItemStack> drops = world.getBlockAt(location).getDrops(heldItem);
        world.getBlockAt(location).setType(Material.AIR);
        return drops;
    }

    private int getExperience(Material material) {
        switch (material) {
            case COAL_ORE:
                return MathUtils.getRandomInt(0, 3);
            case NETHER_GOLD_ORE:
                return MathUtils.getRandomInt(0, 2);
            case DIAMOND_ORE:
            case EMERALD_ORE:
                return MathUtils.getRandomInt(3, 8);
            case LAPIS_ORE:
            case NETHER_QUARTZ_ORE:
                return MathUtils.getRandomInt(2, 6);
            case REDSTONE_ORE:
                return MathUtils.getRandomInt(1, 6);
            default:
                return 0;
        }
    }

}
