package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.event.*;
import net.thedudemc.dudeutils.event.PortalEvents;

public class PluginEvents {

    public static void register(DudeUtils plugin) {
        if (PluginConfigs.FEATURES.ENABLED.get("ChatNameColor")) plugin.getServer().getPluginManager().registerEvents(new ChatEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("PortalUtility")) plugin.getServer().getPluginManager().registerEvents(new PortalEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("Blacklist")) plugin.getServer().getPluginManager().registerEvents(new BlacklistEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("Allies")) plugin.getServer().getPluginManager().registerEvents(new AllyEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("InventorySorter")) plugin.getServer().getPluginManager().registerEvents(new InventoryEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("SinglePlayerSleep")) plugin.getServer().getPluginManager().registerEvents(new SleepEvent(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("VeinMiner")) plugin.getServer().getPluginManager().registerEvents(new VeinMinerEvent(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("NetherWater")) plugin.getServer().getPluginManager().registerEvents(new NetherWaterEvent(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("VillagerUtility")) plugin.getServer().getPluginManager().registerEvents(new VillagerEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("Alternator")) plugin.getServer().getPluginManager().registerEvents(new AlternatorEvents(), plugin);
        if (PluginConfigs.FEATURES.ENABLED.get("ShulkerDropsTwo")) plugin.getServer().getPluginManager().registerEvents(new ShulkerEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerEvents(), plugin);
    }
}
