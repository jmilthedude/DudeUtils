package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.SaveData;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.event.Listener;

public abstract class Feature implements Listener {

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

    public <T extends SaveData> T getSaveData() {
        T data = (T) PluginData.get(this.getName());
        if (data == null) throw new RuntimeException("No data found with that name: " + this.getName());
        return data;
    }


}
