package net.thedudemc.dudeutils;

import net.thedudemc.dudeutils.command.DudeCommands;
import net.thedudemc.dudeutils.event.SleepEvent;
import net.thedudemc.dudeutils.features.magnet.MagnetHelper;
import net.thedudemc.dudeutils.features.portal.PortalParticles;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import net.thedudemc.dudeutils.init.PluginEvents;
import net.thedudemc.dudeutils.init.PluginRecipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DudeUtils extends JavaPlugin implements Listener {

    public static DudeUtils INSTANCE;
    public DudeCommands commands = new DudeCommands();

    @Override
    public void onEnable() {

        INSTANCE = this;
        PluginConfigs.register();
        PluginData.register();
        PluginRecipes.register(this);
        PluginRecipes.removeDisabled(this);
        PluginEvents.register(this);


        commands.initCommands();

        if (PluginConfigs.PORTAL_UTILITY.SPAWN_PARTICLES) PortalParticles.runSpawner();
        if (PluginConfigs.MAGNET.ENABLE) MagnetHelper.runMagnet();

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
