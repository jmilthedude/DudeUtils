package net.thedudemc.dudeutils.features.magnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import net.thedudemc.dudeutils.DudeUtils;

public class MagnetHelper {

	public static List<UUID> magnetizedPlayers = new ArrayList<UUID>();
	private static double speed = DudeUtils.INSTANCE.getConfig().getDouble("magnet.speed");
	private static int range = DudeUtils.INSTANCE.getConfig().getInt("magnet.range");

	private static BukkitTask task;

	public static void addPlayer(UUID uuid) {
		if (!magnetizedPlayers.contains(uuid)) {
			magnetizedPlayers.add(uuid);
		}
	}

	public static void removePlayer(UUID uuid) {
		if (magnetizedPlayers.contains(uuid)) {
			magnetizedPlayers.remove(uuid);
		}
	}

	public static boolean isWearingItem(Player player) {
		ItemStack item = player.getInventory().getHelmet();

		if (item != null) {
			Material mat = item.getType();
			if (mat == Material.DRAGON_HEAD) {
				return true;
			}
		}
		return false;
	}

	static Predicate<Entity> filter = new Predicate<Entity>() {
		@Override
		public boolean test(Entity e) {
			if (e.getType() == EntityType.DROPPED_ITEM)
				return true;
			return false;
		}
	};

	public static void runMagnet() {
		task = Bukkit.getScheduler().runTaskTimer(DudeUtils.INSTANCE, new Runnable() {
			@Override
			public void run() {
				Collection<? extends Player> players = Bukkit.getOnlinePlayers();
				for (Player p : players) {
					if (!MagnetHelper.isWearingItem(p))
						continue;

					World world = p.getWorld();
					Location location = p.getLocation();
					double x1 = location.getX();
					double y1 = location.getY();
					double z1 = location.getZ();

					Collection<Entity> entities = world.getNearbyEntities(p.getLocation(), range, range, range, filter);
					for (Entity e : entities) {
						Location eLoc = e.getLocation();
						double x2 = eLoc.getX();
						double y2 = eLoc.getY();
						double z2 = eLoc.getZ();
						Vector toPlayer = new Vector((x1 - x2) * speed, (y1 - y2) * speed, (z1 - z2) * speed);
						e.setVelocity(toPlayer);
					}
				}
			}
		}, 60L, 1L);

	}

	public static void cancelMagnet() {
		if (task != null)
			Bukkit.getScheduler().cancelTask(task.getTaskId());

	}

}
