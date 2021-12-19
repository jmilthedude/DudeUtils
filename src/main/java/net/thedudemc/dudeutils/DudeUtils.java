package net.thedudemc.dudeutils;

import net.thedudemc.dudeutils.init.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DudeUtils extends JavaPlugin implements Listener {

    public static DudeUtils INSTANCE;

    public static DudeUtils getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {

        INSTANCE = this;
        PluginConfigs.register();
        PluginFeatures.register();
        PluginData.register();
        PluginRecipes.register(this);
        PluginRecipes.removeDisabled(this);
        PluginEvents.register(this);
        PluginCommands.register();
        PluginTasks.run();

    }

    @Override
    public void onDisable() {
        PluginConfigs.save();
        PluginData.save();
    }

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        PluginConfigs.save();
        PluginData.save();
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
