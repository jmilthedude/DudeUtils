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
    public static XPStorageData XP_STORAGE_DATA;

    public static void init() {
        Log.info("Initializing Data");
        BLACKLIST_DATA = register(new BlacklistSaveData().readData());
        ALLY_DATA = register(new AllySaveData().readData());
        MAGNET_DATA = register(new MagnetData().readData());
        DEATHPOINT = register(new DeathpointData().readData());
        CHAT_NAME_COLOR_DATA = register(new ChatNameColorData().readData());
        XP_STORAGE_DATA = register(new XPStorageData().readData());
    }

    private static <T extends SaveData> T register(SaveData data) {
        registry.put(data.getName(), data);
        return (T) data;
    }

    public static SaveData get(String name) {
        return registry.get(name);
    }

    public static void save() {
        registry.values().forEach(SaveData::writeData);
    }

}
