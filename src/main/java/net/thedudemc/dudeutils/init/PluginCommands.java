package net.thedudemc.dudeutils.init;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.command.*;
import net.thedudemc.dudeutils.util.Log;

import java.util.HashMap;

public class PluginCommands {

    public static HashMap<String, PluginCommand> REGISTRY = new HashMap<>();

    public static DudeCommand DUDE;
    public static SlimeCommand SLIME;
    public static AllyCommand ALLY;
    public static ColorCommand COLOR;
    public static VeinMinerCommand VEIN_MINER;
    public static MagnetCommand MAGNET;
    public static SaveCommand SAVE;
    public static CoordsCommand COORDS;
    public static DeathpointCommand DEATHPOINT;
    public static FeatureCommand FEATURE;

    public static void init() {
        Log.info("Initializing Commands");

        DUDE = (DudeCommand) registerCommand(new DudeCommand()).opOnly();
        SLIME = (SlimeCommand) registerCommand(new SlimeCommand().playerOnly());
        ALLY = (AllyCommand) registerCommand(new AllyCommand().playerOnly());
        COLOR = (ColorCommand) registerCommand(new ColorCommand().playerOnly());
        VEIN_MINER = (VeinMinerCommand) registerCommand(new VeinMinerCommand().playerOnly().opOnly());
        MAGNET = (MagnetCommand) registerCommand(new MagnetCommand().playerOnly().opOnly());
        SAVE = (SaveCommand) registerCommand(new SaveCommand().opOnly());
        COORDS = (CoordsCommand) registerCommand(new CoordsCommand().playerOnly());
        DEATHPOINT = (DeathpointCommand) registerCommand(new DeathpointCommand().playerOnly());
        FEATURE = (FeatureCommand) registerCommand(new FeatureCommand().opOnly());


    }

    private static PluginCommand registerCommand(PluginCommand command) {
        DudeUtils.getInstance().getCommand(command.getName()).setExecutor(command);
        DudeUtils.getInstance().getCommand(command.getName()).setTabCompleter(command);
        REGISTRY.put(command.getName(), command);
        return command;
    }

}
