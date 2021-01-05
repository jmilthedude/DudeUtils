package net.thedudemc.dudeutils.config;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SpecialConfig extends Config {

    @Expose public List<String> IGNORED_BY_HOSTILES = new ArrayList<>();

    @Override
    public String getName() {
        return "Special";
    }

    @Override
    protected void reset() {
        IGNORED_BY_HOSTILES.add("Player1");
        IGNORED_BY_HOSTILES.add("Player2");
    }
}
