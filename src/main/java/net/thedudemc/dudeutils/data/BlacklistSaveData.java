package net.thedudemc.dudeutils.data;

import com.google.gson.annotations.Expose;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class BlacklistSaveData extends SaveData {

    @Expose
    public HashMap<UUID, List<String>> BLACKLIST = new HashMap<>();

    @Override
    public String getName() {
        return "blacklist";
    }

    @Override
    protected void reset() {
        BLACKLIST.put(UUID.randomUUID(), Arrays.asList(
                Material.STICK.toString(),
                Material.ROTTEN_FLESH.toString(),
                Material.DEAD_BUSH.toString())
        );
        BLACKLIST.put(UUID.randomUUID(), Arrays.asList(
                Material.STICK.toString(),
                Material.ROTTEN_FLESH.toString(),
                Material.DEAD_BUSH.toString())
        );
        this.markDirty();
    }

    @Nonnull
    public List<Material> getItems(UUID uuid) {
        if (BLACKLIST.containsKey(uuid)) {
            return BLACKLIST.get(uuid).stream().map(Material::getMaterial).collect(Collectors.toList());
        }
        List<Material> materials = new ArrayList<>();
        BLACKLIST.put(uuid, new ArrayList<>());
        this.markDirty();

        return materials;
    }

    public void updateItems(UUID uuid, List<Material> materials) {
        BLACKLIST.put(uuid, materials.stream().map(Material::toString).collect(Collectors.toList()));
        this.markDirty();
    }
}
