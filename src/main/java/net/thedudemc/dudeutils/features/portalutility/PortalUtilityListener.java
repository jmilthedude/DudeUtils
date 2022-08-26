package net.thedudemc.dudeutils.features.portalutility;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.Feature;
import net.thedudemc.dudeutils.features.FeatureListener;
import net.thedudemc.dudeutils.util.Cooldown;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PortalUtilityListener extends FeatureListener {

    public static final List<Cooldown> cooldowns = new ArrayList<>();

    public PortalUtilityListener(Feature feature) {
        super(feature);
    }

    @EventHandler
    public void onPortalClicked(PlayerInteractEvent event) {
        if (event.getHand() == null || event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        if (!player.isSneaking()) return;

        ItemStack stack = event.getItem();
        if (stack == null || !stack.getType().equals(Material.FLINT_AND_STEEL)) return;

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.OBSIDIAN) return;

        event.setCancelled(true);

        if (PortalUtilityFeature.getPortals().containsKey(player.getUniqueId())) {
            PortalUtilityFeature.removeParticles(player);
            return;
        }

        Location current = block.getLocation();
        World world = current.getWorld();
        if (world == null) return;
        if (world.getEnvironment() == World.Environment.THE_END) return;

        Location oppositeLocation = getAlternateDimensionLocation(current);
        if (oppositeLocation == null) return;
        World oppositeWorld = oppositeLocation.getWorld();
        if (oppositeWorld == null) return;

        PortalUtilityFeature.activateParticles(player, oppositeLocation);
    }

    @EventHandler
    public void onEnterPortal(EntityPortalEnterEvent event) {
        if (event.getEntity() instanceof Player p) {
            if (!p.isSneaking()) return;
            if (playerInCooldown(p.getUniqueId())) return;
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

            GameMode current = p.getGameMode();
            updateGameMode(p, current);
            beginCooldown(p.getUniqueId());
        }
    }

    @EventHandler
    public void onBlockPlaceAttempt(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        GameMode gameMode = player.getGameMode();
        if (gameMode == GameMode.CREATIVE && playerInCooldown(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }


    private boolean playerInCooldown(UUID playerId) {
        return cooldowns.stream().anyMatch(cooldown -> cooldown.getPlayerId().equals(playerId));
    }

    private void beginCooldown(UUID playerId) {
        Cooldown cooldown = new Cooldown(playerId, 10 * 20);
        cooldowns.add(cooldown);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DudeUtils.getInstance(), () -> {
            cooldown.update();
            if (cooldown.isComplete()) {
                cooldowns.remove(cooldown);
            }
        }, 0, 1);
    }

    private void updateGameMode(Player player, GameMode current) {
        if (player == null) return;
        player.setGameMode(GameMode.CREATIVE);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setGameMode(current);
            }
        }.runTaskLater(DudeUtils.getInstance(), 3);
    }


    private Location getAlternateDimensionLocation(Location current) {
        World world = current.getWorld();
        if (world == null) return null;
        if (world.getEnvironment() == World.Environment.NORMAL) {
            double x = current.getBlockX() / 8D;
            double y = current.getBlockY();
            double z = current.getBlockZ() / 8D;
            return new Location(Bukkit.getWorlds().get(1), x, y, z);
        } else {
            double x = current.getBlockX() * 8D;
            double y = current.getBlockY();
            double z = current.getBlockZ() * 8D;
            return new Location(Bukkit.getWorlds().get(0), x, y, z);

        }
    }

}
