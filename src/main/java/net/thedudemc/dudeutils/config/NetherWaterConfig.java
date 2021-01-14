package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

public class NetherWaterConfig extends Config {

    @Expose
    public boolean ENABLED;

    @Override
    public String getName() {
        return "NetherWater";
    }

    @Override
    protected void reset() {
        ENABLED = false;
    }
}
