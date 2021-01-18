package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.config.*;

public class PluginConfigs {

    public static FeaturesConfig FEATURES;
    public static MagnetConfig MAGNET;
    public static PortalConfig PORTAL_UTILITY;
    public static RecipeConfig RECIPES;
    public static SpecialConfig SPECIAL;
    public static VeinMinerConfig VEINMINER;

    public static void register() {

        FEATURES = (FeaturesConfig) new FeaturesConfig().readConfig();

        MAGNET = (MagnetConfig) new MagnetConfig().readConfig();
        PORTAL_UTILITY = (PortalConfig) new PortalConfig().readConfig();
        RECIPES = (RecipeConfig) new RecipeConfig().readConfig();
        SPECIAL = (SpecialConfig) new SpecialConfig().readConfig();
        VEINMINER = (VeinMinerConfig) new VeinMinerConfig().readConfig();

    }
}
