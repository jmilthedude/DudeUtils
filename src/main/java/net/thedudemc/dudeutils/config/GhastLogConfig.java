package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

public class GhastLogConfig extends Config {

    @Expose private boolean serverWide;
    @Expose private int minX;
    @Expose private int minY;
    @Expose private int minZ;
    @Expose private int maxX;
    @Expose private int maxY;
    @Expose private int maxZ;

    @Override
    public String getName() {
        return "ghast_log";
    }

    @Override
    protected void reset() {
        this.serverWide = false;

        this.minX = -377;
        this.minY = 0;
        this.minZ = -373;

        this.maxX = -149;
        this.maxY = 128;
        this.maxZ = -117;
    }

    public boolean isServerWide() {
        return serverWide;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }
}
