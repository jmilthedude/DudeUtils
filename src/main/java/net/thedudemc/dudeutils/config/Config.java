package net.thedudemc.dudeutils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thedudemc.dudeutils.DudeUtils;

import java.io.*;

public abstract class Config {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected File root = new File(DudeUtils.getInstance().getDataFolder(), "config/");
    protected String extension = ".json";

    private boolean isDirty = true;

    public void generateConfig() {
        this.reset();

        this.writeConfig();
    }

    private File getConfigFile() {
        return new File(this.root, this.getName() + this.extension);
    }

    public abstract String getName();

    public Config readConfig() {
        try {
            return GSON.fromJson(new FileReader(this.getConfigFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generateConfig();
        }

        return this;
    }

    protected abstract void reset();

    public void writeConfig() {
        if (!this.isDirty) return;
        try {
            if (!root.exists() && !root.mkdirs()) return;
            if (!this.getConfigFile().exists() && !this.getConfigFile().createNewFile()) return;
            FileWriter writer = new FileWriter(this.getConfigFile());
            GSON.toJson(this, writer);
            writer.flush();
            writer.close();
            DudeUtils.logInfo("Saved " + this.getName());
            this.isDirty = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void markDirty() {
        this.isDirty = true;
    }

}