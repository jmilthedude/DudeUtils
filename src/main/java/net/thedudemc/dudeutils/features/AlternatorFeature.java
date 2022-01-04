package net.thedudemc.dudeutils.features;

import net.thedudemc.dudeutils.DudeUtils;
import net.thedudemc.dudeutils.features.alternator.AlternatorHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class AlternatorFeature extends Feature{
    @Override
    public String getName() {
        return "alternator";
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    private static final HashSet<String> cooldowns = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUse(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        ItemStack heldItem = event.getItem();
        if (heldItem == null || !AlternatorHelper.isAlternatorItem(heldItem)) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        if (cooldowns.contains(player.getName())) return; //prevents spamming
        if (player.isSneaking() && event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack newAlternator = AlternatorHelper.changeAlternatorMode(heldItem);
            player.getInventory().setItemInMainHand(newAlternator);
            cooldowns.add(player.getName());
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            useAlternator(event.getClickedBlock(), event.getBlockFace(), player, heldItem);
            cooldowns.add(player.getName());
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(DudeUtils.getInstance(), () -> cooldowns.remove(player.getName()), 3L);
    }

    private void useAlternator(Block block, BlockFace face, Player player, ItemStack alternator) {
        if (block.getType() == Material.BEDROCK || block.getType() == Material.OBSIDIAN) return;
        AlternatorHelper.Mode mode = AlternatorHelper.getCurrentMode(alternator);

        ItemStack[] hotbar = getHotbar(player);
        List<ItemStack> possibleBlocks = getPossibleBlocks(hotbar);

        if (possibleBlocks.isEmpty()) return;

        Collections.shuffle(possibleBlocks);
        ItemStack randomBlock = possibleBlocks.get(0);

        if (mode == AlternatorHelper.Mode.CHANGE) {
            player.getInventory().addItem(new ItemStack(block.getType()));
            block.setType(randomBlock.getType());
        } else {
            Block newBlock = block.getRelative(face);
            newBlock.setType(randomBlock.getType());
        }
        player.playSound(block.getLocation(), Sound.BLOCK_STONE_PLACE, .7f, 1);
        removeUsedBlock(player, randomBlock.getType());
    }

    private void removeUsedBlock(Player player, Material used) {
        int slot = player.getInventory().first(used);
        ItemStack stack = player.getInventory().getItem(slot);
        stack.setAmount(stack.getAmount() - 1);
        player.getInventory().setItem(slot, stack);
    }

    private List<ItemStack> getPossibleBlocks(ItemStack[] hotbar) {
        List<ItemStack> blocks = new ArrayList<>();
        for (ItemStack stack : hotbar) {
            if (!stack.getType().isAir() && stack.getType().isBlock()) blocks.add(stack);
        }
        return blocks;
    }

    private ItemStack[] getHotbar(Player player) {
        ItemStack[] hotbar = getEmptyArray(9);
        for (int i = 0; i < hotbar.length; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack == null) continue;
            ItemStack toAdd = stack.clone();
            toAdd.setAmount(1);
            hotbar[i] = toAdd;
        }
        return hotbar;
    }

    private ItemStack[] getEmptyArray(int size) {
        ItemStack[] array = new ItemStack[size];
        for (int i = 0; i < size; i++) array[i] = new ItemStack(Material.AIR);
        return array;
    }
}
