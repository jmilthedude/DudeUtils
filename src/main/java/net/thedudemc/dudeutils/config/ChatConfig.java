package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

public class ChatConfig extends Config {

    @Expose public boolean ENABLE_NAME_COLORS;

    @Override
    public String getName() {
        return "Chat";
    }

    @Override
    protected void reset() {
        ENABLE_NAME_COLORS = true;
    }
}
