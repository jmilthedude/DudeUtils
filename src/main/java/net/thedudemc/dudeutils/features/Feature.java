package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.SaveData;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;

public abstract class Feature {

    public abstract String getName();

    public Feature() {
        if (this.isEnabled()) this.enable();
        else this.disable();
    }

    public abstract FeatureListener getListener();

    public boolean isEnabled() {
        return PluginConfigs.FEATURES.ENABLED.get(this.getName());
    }

    protected void onEnabled() {

    }

    protected void onDisabled() {
    }

    public void enable() {
        PluginConfigs.FEATURES.setEnabled(this.getName(), true);
        this.onEnabled();
    }

    public void disable() {
        PluginConfigs.FEATURES.setEnabled(this.getName(), false);
        this.onDisabled();
    }

    public <T extends SaveData> T getSaveData() {
        T data = (T) PluginData.get(this.getName());
        if (data == null) throw new RuntimeException("No data found with that name: " + this.getName());
        return data;
    }


}
