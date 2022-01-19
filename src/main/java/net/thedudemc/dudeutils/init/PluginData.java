package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.data.*;
import net.thedudemc.dudeutils.util.Log;

import java.util.HashMap;

public class PluginData {

    private static final HashMap<String, SaveData> registry = new HashMap<>();

    public static BlacklistSaveData BLACKLIST_DATA;
    public static AllySaveData ALLY_DATA;
    public static MagnetData MAGNET_DATA;
    public static DeathpointData DEATHPOINT;
    public static ChatNameColorData CHAT_NAME_COLOR_DATA;
    public static GhastLogData GHAST_LOG;

    public static void init() {
        Log.info("Initializing Data");
        BLACKLIST_DATA = (BlacklistSaveData) register(new BlacklistSaveData().readData());
        ALLY_DATA = (AllySaveData) register(new AllySaveData().readData());
        MAGNET_DATA = (MagnetData) register(new MagnetData().readData());
        DEATHPOINT = (DeathpointData) register(new DeathpointData().readData());
        CHAT_NAME_COLOR_DATA = (ChatNameColorData) register(new ChatNameColorData().readData());
        GHAST_LOG = (GhastLogData) register(new GhastLogData().readData());
    }

    private static SaveData register(SaveData data) {
        registry.put(data.getName(), data);
        return data;
    }

    public static SaveData get(String name) {
        return registry.get(name);
    }

    public static void save() {
        registry.values().forEach(SaveData::writeData);
    }

}
