package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PortalEvents implements Listener {

    public static HashMap<Player, Long> timers = new HashMap<>();
    private static final List<Cooldown> cooldowns = new ArrayList<>();
    public static HashMap<Player, Location> portals = new HashMap<>();

    @EventHandler
    public void onPortalClicked(PlayerInteractEvent event) {
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
            if (portals.containsKey(player)) {
                Location location = portals.remove(player);
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

        portals.put(player, alternate);
        timers.put(player, 300L);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUseOldPortal(PlayerPortalEvent event) {
        System.out.println("oldPortalEvent");
        World world = event.getFrom().getWorld();
        if (world != null && world.getName().contains("world_old")) {
            System.out.println("in old world");
            World spawnWorld = Bukkit.getWorld("world");
            if (spawnWorld != null) {
                System.out.println("found spawn world");
                event.setCancelled(true);
                event.getPlayer().teleport(spawnWorld.getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void onMove(EntityPortalEnterEvent event) {

        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (!p.isSneaking()) return;
            if (playerInCooldown(p)) return;
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

            GameMode current = p.getGameMode();
            updateGameMode(p, current);
            beginCooldown(p);
        }
    }

    @EventHandler
    public void onBlockPlaceAttempt(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && playerInCooldown(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    private boolean playerInCooldown(Player p) {
        for (Cooldown c : cooldowns) {
            if (c.player.equals(p)) return true;
        }
        return false;
    }

    private void beginCooldown(Player p) {
        Cooldown cooldown = new Cooldown(p, 10 * 20);
        cooldowns.add(cooldown);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DudeUtils.getInstance(), () -> {
            cooldown.update();
            if (cooldown.isComplete()) {
                cooldowns.remove(cooldown);
            }
        }, 0, 1);
    }

    private void updateGameMode(Player p, GameMode current) {
        p.setGameMode(GameMode.CREATIVE);
        new BukkitRunnable() {

            @Override
            public void run() {
                p.setGameMode(current);
            }
        }.runTaskLater(DudeUtils.getInstance(), 3);
    }

    public static class Cooldown {
        private Player player;
        private int ticksRemaining;

        public Cooldown(Player p, int cooldown) {
            this.player = p;
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
