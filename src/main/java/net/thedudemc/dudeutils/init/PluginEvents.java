package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.*;
import net.thedudemc.dudeutils.event.PortalEvents;

public class PluginEvents {

    public static void register(DudeUtils plugin) {
        if (PluginConfigs.FEATURES.ENABLED.get("ChatNameColor")) plugin.getServer().getPluginManager().registerEvents(new ChatEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("PortalUtility")) plugin.getServer().getPluginManager().registerEvents(new PortalEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlacklistEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AllyEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("SinglePlayerSleep")) plugin.getServer().getPluginManager().registerEvents(new SleepEvent(), plugin);
        if(PluginConfigs.FEATURES.ENABLED.get("VeinMiner")) plugin.getServer().getPluginManager().registerEvents(new VeinMinerEvent(), plugin);
        if(PluginConfigs.FEATURES.ENABLED.get("NetherWater")) plugin.getServer().getPluginManager().registerEvents(new NetherWaterEvent(), plugin);
        if(PluginConfigs.FEATURES.ENABLED.get("VillagerUtility")) plugin.getServer().getPluginManager().registerEvents(new VillagerEvents(), plugin);
    }
}
