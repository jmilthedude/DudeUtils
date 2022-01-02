package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.data.SaveData;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public abstract class Feature implements Listener {

    protected static BukkitTask task;

    public abstract String getName();

    public Feature() {
        if (this.isEnabled()) this.enable();
        else this.disable();
    }

    public boolean isEnabled() {
        return PluginConfigs.FEATURES.ENABLED.get(this.getName());
    }

    public abstract void doEnable();

    public abstract void doDisable();

    public void execute() {
    }

    protected void createTask() {
    }

    protected void cancelTask() {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task.getTaskId());
            task.cancel();
            task = null;
        }
    }

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
