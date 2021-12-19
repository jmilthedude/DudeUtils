package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.init.PluginConfigs;

public abstract class Feature {

    protected boolean isEnabled;

    public abstract String getName();

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public abstract void doEnable();

    public abstract void doDisable();

    public void enable() {
        this.isEnabled = true;
        PluginConfigs.FEATURES.setEnabled(this.getName(), true);
        this.doEnable();
    }

    public void disable() {
        this.isEnabled = false;
        PluginConfigs.FEATURES.setEnabled(this.getName(), false);
        this.doDisable();
    }


}
