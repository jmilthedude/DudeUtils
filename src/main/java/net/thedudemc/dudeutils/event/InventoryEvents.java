package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.BlacklistFeature;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryEvents implements Listener {

    @EventHandler
    public void onPlayerInventorySort(InventoryClickEvent event) {
        if (BlacklistFeature.hasBlacklistOpen((Player) event.getWhoClicked())) {
            return;
        }
        if (event.getInventory().getType() != InventoryType.CRAFTING) {
            return;
        }
        if (event.getSlotType() != SlotType.OUTSIDE) {
            return;
        }
        if (event.getCursor().getType() != Material.AIR) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getView().getBottomInventory();

        sortInventoryAsync(player, inv, 9, 36);

    }

    @EventHandler
    public void onChestSort(InventoryClickEvent event) {
        if (BlacklistFeature.hasBlacklistOpen((Player) event.getWhoClicked())) {
            return;
        }
        InventoryType type = event.getInventory().getType();
        if (type != InventoryType.CHEST && type != InventoryType.BARREL && type != InventoryType.SHULKER_BOX && type != InventoryType.ENDER_CHEST) {
            return;
        }
        if (event.getSlotType() != SlotType.OUTSIDE) {
            return;
        }
        if (event.getCursor().getType() != Material.AIR) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getView().getTopInventory();

        sortInventoryAsync(player, inv, 0, inv.getSize());

    }

    private void sortInventoryAsync(Player player, Inventory inv, int minSlot, int maxSlot) {
        Bukkit.getScheduler().runTaskAsynchronously(DudeUtils.INSTANCE, () -> {

            List<ItemStack> items = new ArrayList<>();
            for (int i = minSlot; i < maxSlot; i++) {
                ItemStack item = inv.getItem(i);
                if (item != null)
                    items.add(item);
            }

            items.sort(Comparator.comparing(ItemStack::getType));

            List<ItemStack> combined = combineStacks(items);

            ItemStack[] array = new ItemStack[maxSlot - minSlot];

            int i = 0;
            for (ItemStack item : combined) {
                array[i++] = item;
            }

            for (int j = minSlot; j < maxSlot; j++) {
                inv.setItem(j, array[j - minSlot]);
            }

            for (HumanEntity e : inv.getViewers()) {
                if (e instanceof Player) {
                    ((Player) e).updateInventory();
                }
            }
        });
    }

    private List<ItemStack> combineStacks(List<ItemStack> items) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack item : items) {
            int index = getFirstSimilar(result, item);
            if (index > -1) {
                ItemStack similar = result.get(index);
                int similarAmount = similar.getAmount();
                if (similarAmount + item.getAmount() > item.getMaxStackSize()) {
                    similar.setAmount(item.getMaxStackSize());
                    item.setAmount(item.getAmount() - item.getMaxStackSize() + similarAmount);
                    result.add(item);
                } else {
                    similar.setAmount(similarAmount + item.getAmount());
                }
                result.set(index, similar);
            } else {
                result.add(item);
            }
        }
        return result;
    }

    private int getFirstSimilar(List<ItemStack> items, ItemStack input) {
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (item.isSimilar(input)) {
                if (item.getAmount() < item.getMaxStackSize())
                    return i;
            }
        }
        return -1;
    }

}
