package net.thedudemc.dudeutils;

import net.thedudemc.dudeutils.init.*;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DudeUtils extends JavaPlugin implements Listener {

    private static DudeUtils INSTANCE;

    public static DudeUtils getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        if (INSTANCE == null) INSTANCE = this;

        PluginConfigs.init();
        PluginFeatures.init();
        PluginData.init();
        PluginRecipes.init();
        PluginEvents.init();
        PluginCommands.init();
        PluginTasks.init();

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


    public static NamespacedKey getKey(String name) {
        return new NamespacedKey(getInstance(), name);
    }


}
