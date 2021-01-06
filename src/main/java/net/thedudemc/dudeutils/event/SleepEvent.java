package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.DudeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class SleepEvent implements Listener {

    private static long night = 12542;
    private static long day = 23460;

    private static List<String> sleepingPlayers = new ArrayList<>();


    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world.getEnvironment() != World.Environment.NORMAL) return;
        PlayerBedEnterEvent.BedEnterResult result = event.getBedEnterResult();
        if (isNight(world) && result == PlayerBedEnterEvent.BedEnterResult.OK) {
            DudeUtils.getInstance().getServer().broadcastMessage(getSleepMessage(player.getName()));
            sleepingPlayers.add(player.getName());
        } else if (world.isThundering()) {
            DudeUtils.getInstance().getServer().broadcastMessage(getRainMessage(player.getName()));
            Bukkit.getScheduler().scheduleSyncDelayedTask(DudeUtils.getInstance(), () -> {
                world.setWeatherDuration(0);
                world.setThunderDuration(0);
                world.setThundering(false);
                world.setStorm(false);
            });
        }
    }

    private boolean isNight(World world) {
        long time = world.getTime();
        return time >= night && time < day;
    }

    private String getSleepMessage(String name) {
        Team team = DudeUtils.getInstance().getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(name);
        if (team == null) return name + " is sleeping!";
        Math.random();
        return team.getColor() + name + ChatColor.RESET + " is sleeping!";
    }

    private String getRainMessage(String name) {
        Team team = DudeUtils.getInstance().getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(name);
        if (team == null) return name + " is making the rain go away!";
        return team.getColor() + name + ChatColor.RESET + " is making the rain go away!";
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent event) {
        sleepingPlayers.remove(event.getPlayer().getName());
    }

    public static void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DudeUtils.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (sleepingPlayers.isEmpty()) return;
                World world = DudeUtils.getInstance().getServer().getWorlds().get(0);
                setTime(world, getNextTime(world));
            }

            private long getNextTime(World world) {
                return world.getFullTime() + 100L;
            }

            private void setTime(World world, long time) {
                world.setFullTime(time);
            }
        }, 0L, 1L);
    }
}
