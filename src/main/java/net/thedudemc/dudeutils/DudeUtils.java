package net.thedudemc.dudeutils;

import net.thedudemc.dudeutils.event.SleepEvent;
import net.thedudemc.dudeutils.features.magnet.MagnetHelper;
import net.thedudemc.dudeutils.features.portal.PortalParticles;
import net.thedudemc.dudeutils.init.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DudeUtils extends JavaPlugin implements Listener {

    public static DudeUtils INSTANCE;

    @Override
    public void onEnable() {

        INSTANCE = this;
        PluginConfigs.register();
        PluginData.register();
        PluginRecipes.register(this);
        PluginRecipes.removeDisabled(this);
        PluginEvents.register(this);
        PluginCommands.register();

        if (PluginConfigs.PORTAL_UTILITY.SPAWN_PARTICLES) PortalParticles.runSpawner();
        if (PluginConfigs.FEATURES.ENABLED.get("Magnet")) MagnetHelper.runMagnet();

        SleepEvent.run();
    }

    @Override
    public void onDisable() {
        PluginData.save();
    }

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        PluginData.save();
    }

    public static DudeUtils getInstance() {
        return INSTANCE;
    }

    public static void logInfo(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.AQUA);
        sb.append("[DudeUtils] ");
        sb.append(ChatColor.RESET);
        sb.append(msg);
        Bukkit.getLogger().info(sb.toString());
    }

    public static void logError(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.AQUA);
        sb.append("[DudeUtils] ");
        sb.append(ChatColor.RED);
        sb.append(msg);
        Bukkit.getLogger().info(sb.toString());
    }

}
