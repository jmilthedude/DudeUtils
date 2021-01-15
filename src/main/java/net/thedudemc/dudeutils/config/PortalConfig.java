package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

public class PortalConfig extends Config {


    @Expose public boolean SPAWN_PARTICLES;

    @Override
    public String getName() {
        return "PortalUtility";
    }

    @Override
    protected void reset() {
        SPAWN_PARTICLES = true;
    }
}
