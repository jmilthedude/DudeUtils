package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.util.StringUtils;
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
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class PortalUtilityFeature extends Feature {

    private static BukkitTask task;

    private static final HashMap<UUID, Long> timers = new HashMap<>();
    private static final List<Cooldown> cooldowns = new ArrayList<>();
    private static final HashMap<UUID, Location> portals = new HashMap<>();

    @Override
    public String getName() {
        return "portal_utility";
    }

    @Override
    public void doEnable() {
        if (!isEnabled()) return;

        task = Bukkit.getScheduler().runTaskTimer(DudeUtils.INSTANCE, () -> {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            players.forEach(player -> {
                UUID id = player.getUniqueId();
                if (portals.containsKey(player.getUniqueId())) {

                    Location location = portals.get(id);
                    if (isChunkLoaded(location)) {
                        spawnParticles(id, location);
                    }

                    if (decrementTimer(id) <= 0) {
                        removeParticles(player);
                    }
                }
            });
        }, 60L, 20L);
    }

    @Override
    public void doDisable() {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
            task.cancel();
            task = null;
        }
    }

    private void activateParticles(Player player, Location oppositeLocation) {
        World world = oppositeLocation.getWorld();
        if (world == null) return;

        portals.put(player.getUniqueId(), oppositeLocation);
        timers.put(player.getUniqueId(), 300L);
        player.sendMessage(StringUtils.getDimensionName(oppositeLocation.getWorld().getEnvironment()) + " Portal Location: " + StringUtils.getCoordinateString(oppositeLocation));
    }

    private void removeParticles(Player player) {
        Location location = portals.remove(player.getUniqueId());
        timers.remove(player.getUniqueId());
        player.sendMessage("Particles Despawned at: " + StringUtils.getCoordinateString(location));
    }

    private void spawnParticles(UUID p, Location location) {
        World world = location.getWorld();
        if (world == null) return;
        double x = location.getBlockX() + 0.5;
        double z = location.getBlockZ() + 0.5;
        for (int i = 1; i < 128; i++) {
            world.spawnParticle(Particle.DRAGON_BREATH, x, i, z, 1, 0D, 0D, 0D, 0.005);
        }
    }

    private boolean isChunkLoaded(Location location) {
        World world = location.getWorld();
        if (world == null) return false;
        return world.getChunkAt(location).isLoaded();
    }

    private long decrementTimer(UUID p) {
        long timeRemaining = timers.get(p) - 1;
        timers.put(p, timeRemaining);
        return timeRemaining;
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

        if (portals.containsKey(player.getUniqueId())) {
            removeParticles(player);
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

        activateParticles(player, oppositeLocation);
    }

    @EventHandler
    public void onEnterPortal(EntityPortalEnterEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
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
        return cooldowns.stream().anyMatch(cooldown -> cooldown.playerId.equals(playerId));
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

    public static class Cooldown {
        private final UUID playerId;
        private int ticksRemaining;

        public Cooldown(UUID playerId, int cooldown) {
            this.playerId = playerId;
            this.ticksRemaining = cooldown;
        }

        public void update() {
            ticksRemaining--;
        }

        public boolean isComplete() {
            return ticksRemaining <= 0;
        }
    }


    private Location getAlternateDimensionLocation(Location current) {
        World world = current.getWorld();
        if (world == null) return null;
        if (world.getEnvironment() == World.Environment.NORMAL) {
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
