package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.*;
import net.thedudemc.dudeutils.event.PortalEvents;

public class PluginEvents {

    public static void register(DudeUtils plugin) {
        if (PluginConfigs.CHAT.ENABLE_NAME_COLORS) plugin.getServer().getPluginManager().registerEvents(new ChatEvents(), plugin);
        if (PluginConfigs.PORTAL_UTILITY.ENABLE) plugin.getServer().getPluginManager().registerEvents(new PortalEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlacklistEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AllyEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SleepEvent(), plugin);
        if(PluginConfigs.VEINMINER.ENABLED) plugin.getServer().getPluginManager().registerEvents(new VeinMinerEvent(), plugin);
        if(PluginConfigs.NETHER_WATER.ENABLED) plugin.getServer().getPluginManager().registerEvents(new NetherWaterEvent(), plugin);
    }
}
