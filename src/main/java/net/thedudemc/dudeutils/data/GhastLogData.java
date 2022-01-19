package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeutils.util.chat.JSONMessage;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class GhastLogData extends SaveData {

    @Expose private List<GhastSpawnLocation> locations = new ArrayList<>();

    @Override
    public String getName() {
        return "ghast_log";
    }

    @Override
    protected void reset() {

    }

    public void add(GhastSpawnLocation ghastSpawnLocation) {
        this.locations.add(ghastSpawnLocation);
        this.markDirty();
    }

    public List<GhastSpawnLocation> getLocations() {
        return new ArrayList<>(locations);
    }

    public static class GhastSpawnLocation {
        @Expose private int x, y, z;
        @Expose private String blockType;

        public GhastSpawnLocation(int x, int y, int z, String blockType) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockType = blockType;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public String getBlockType() {
            return blockType;
        }

        @Override
        public String toString() {
            return "GhastSpawnLocation{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", blockType='" + blockType + '\'' +
                    '}';
        }

        public JSONMessage getCommandString(String name) {
            return JSONMessage.create("Ghast Spawned on " + blockType + " at " + x + ", " + y + ", " + z).color(ChatColor.AQUA).runCommand("/execute in minecraft:the_nether run tp " + name + " " + x + " " + y + " " + z).tooltip("Click to be teleported!");
        }
    }
}
