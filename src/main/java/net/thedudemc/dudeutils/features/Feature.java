package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.SaveData;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.event.Listener;

public abstract class Feature implements Listener {

    public abstract String getName();

    public Feature() {
        if (this.isEnabled()) this.enable();
        else this.disable();
    }

    public boolean isEnabled() {
        return PluginConfigs.FEATURES.ENABLED.get(this.getName());
    }

    public abstract void onEnabled();

    public abstract void onDisabled();

    public void enable() {
        PluginConfigs.FEATURES.setEnabled(this.getName(), true);
        this.onEnabled();
    }

    public void disable() {
        PluginConfigs.FEATURES.setEnabled(this.getName(), false);
        this.onDisabled();
    }

    public SaveData getSaveData() {
        SaveData data = PluginData.get(this.getName());
        if (data == null) throw new RuntimeException("No data found with that name: " + this.getName());
        return data;
    }


}
