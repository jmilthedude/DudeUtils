package net.thedudemc.dudeutils.features.villagerutility;

import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VillagerUtilityListener extends FeatureListener {
    public VillagerUtilityListener(Feature feature) {
        super(feature);
    }

    private static final List<Player> cancelInventoryEvent = new ArrayList<>();
    private static final HashMap<Player, Villager> selectedVillagers = new HashMap<>();


    @EventHandler
    public void getVillagerInfo(PlayerInteractAtEntityEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (!player.isSneaking() || !(entity instanceof Villager) || heldItem.getType() != Material.AIR)
            return;

        cancelInventoryEvent.add(player);

        // Player is sneaking and has interacted with a villager with an empty hand.

        Villager villager = (Villager) entity;
        Location jobLocation = villager.getMemory(MemoryKey.JOB_SITE);
        if (jobLocation == null) {
            player.sendMessage("The villager you clicked has no job.");
            return;
        }
        Block workstation = jobLocation.getBlock();
        Villager.Profession profession = villager.getProfession();

        sendChatMessage(player, "~~~Villager Info~~~",
                ChatColor.AQUA + "UUID" + ChatColor.RESET + ": " + ChatColor.YELLOW + villager.getUniqueId(),
                ChatColor.AQUA + "Profession" + ChatColor.RESET + ": " + ChatColor.GREEN + profession,
                ChatColor.AQUA + "Workstation" + ChatColor.RESET + ": " + ChatColor.DARK_PURPLE + workstation.getType() +
                        ChatColor.RESET + " | " +
                        ChatColor.GOLD + workstation.getX() + ChatColor.RESET + ", " +
                        ChatColor.GOLD + workstation.getY() + ChatColor.RESET + ", " +
                        ChatColor.GOLD + workstation.getZ() + ChatColor.RESET
        );
    }

    @EventHandler
    public void selectVillager(PlayerInteractAtEntityEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (!player.isSneaking() || !(entity instanceof Villager) || heldItem.getType() != Material.EMERALD)
            return;

        cancelInventoryEvent.add(player);

        Villager villager = (Villager) entity;
        selectedVillagers.put(player, villager);

        villager.setGlowing(true);
        player.sendMessage("You have selected a villager. Now you may choose their workstation assignment.");

    }

    @EventHandler
    public void assignWorkstation(PlayerInteractEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (block == null || !player.isSneaking() || heldItem.getType() != Material.EMERALD || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (VillagerUtilityFeature.possibleWorkstations.contains(block.getType())) {
            if (selectedVillagers.containsKey(player)) {
                Villager v = selectedVillagers.remove(player);
                v.setMemory(MemoryKey.JOB_SITE, block.getLocation());
                player.sendMessage("You have assigned the villager to the workstation: " + ChatColor.AQUA + block.getType());
                v.setGlowing(false);
            }
        }
    }

    @EventHandler
    public void removeWorkstation(PlayerInteractEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (!player.isSneaking() || heldItem.getType() != Material.EMERALD || event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        if (selectedVillagers.containsKey(player)) {
            Villager v = selectedVillagers.remove(player);
            v.setMemory(MemoryKey.JOB_SITE, null);
            player.sendMessage("You have unassigned the villager from their workstation.");
            v.setGlowing(false);
        }

    }

    @EventHandler
    public void cancelOpenInventory(InventoryOpenEvent event) {
        if (!feature.isEnabled()) return;

        if (event.getInventory().getType() != InventoryType.MERCHANT) return;
        Player p = (Player) event.getPlayer();
        if (cancelInventoryEvent.contains(p)) {
            event.setCancelled(true);
            cancelInventoryEvent.remove(p);
        }
    }

    private void sendChatMessage(Player p, String... message) {
        for (String s : message) {
            p.sendMessage(s);
        }
    }
}
