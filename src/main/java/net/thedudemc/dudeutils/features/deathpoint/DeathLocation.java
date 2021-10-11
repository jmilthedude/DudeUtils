package net.thedudemc.dudeutils.features.deathpoint;

import com.google.gson.annotations.Expose;
import org.bukkit.Location;

public class DeathLocation {
    @Expose
    private int x, y, z;
    @Expose
    private String dimension;

    public DeathLocation(int x, int y, int z, String dimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public static DeathLocation fromLocation(Location location) {
        if (location.getWorld() == null) return null;
        return new DeathLocation(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld()
                        .getEnvironment()
                        .toString()
                        .toLowerCase()
        );
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
