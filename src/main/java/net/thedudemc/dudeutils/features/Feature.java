package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.SaveData;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.event.Listener;

public abstract class Feature implements Listener {

    public abstract String getName();

    public boolean isEnabled() {
        return PluginConfigs.FEATURES.ENABLED.get(this.getName());
    }

    public abstract void doEnable();

    public abstract void doDisable();

    public void enable() {
        PluginConfigs.FEATURES.setEnabled(this.getName(), true);
        this.doEnable();
    }

    public void disable() {
        PluginConfigs.FEATURES.setEnabled(this.getName(), false);
        this.doDisable();
    }

    public <T extends SaveData> T getSaveData() {
        T data = (T) PluginData.get(this.getName());
        if (data == null) throw new RuntimeException("No data found with that name: " + this.getName());
        return data;
    }


}
