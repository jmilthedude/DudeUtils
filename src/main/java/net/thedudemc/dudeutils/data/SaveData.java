package net.thedudemc.dudeutils.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thedudemc.dudeutils.DudeUtils;

import java.io.*;

public abstract class SaveData {

    private static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    protected File root = new File(DudeUtils.getInstance().getDataFolder(), "data/");
    protected String extension = ".json";

    public abstract String getName();

    protected abstract void reset();

    public void generateSaveData() {
        this.reset();
        this.writeData();
    }

    private File getDataFile() {
        return new File(this.root, this.getName() + this.extension);
    }

    public SaveData readData() {
        try {
            return GSON.fromJson(new FileReader(this.getDataFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generateSaveData();
        }
        return this;
    }

    public void writeData() {
        try {
            if (!root.exists() && !root.mkdirs()) return;
            if (!this.getDataFile().exists() && !this.getDataFile().createNewFile()) return;
            FileWriter writer = new FileWriter(this.getDataFile());
            GSON.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


}
