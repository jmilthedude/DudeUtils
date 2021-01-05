package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

public class MagnetConfig extends Config {

    @Expose public boolean ENABLE;
    @Expose public double SPEED = 0.25d;
    @Expose public int RANGE;

    @Override
    public String getName() {
        return "Magnet";
    }

    @Override
    protected void reset() {
        ENABLE = false;
        SPEED = 0.25D;
        RANGE = 8;
    }
}
