package net.thedudemc.dudeutils.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PetTransferEvent implements Listener {

    private static final HashMap<UUID, Tameable> selected = new HashMap<>();

    @EventHandler
    public void onRightClickPet(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem.getType() != Material.DIAMOND) return;

        Entity clicked = event.getRightClicked();
        if (!(clicked instanceof Tameable tamedEntity)) return;
        if (!tamedEntity.isTamed() || !Objects.requireNonNull(tamedEntity.getOwner()).getUniqueId().equals(player.getUniqueId())) return;

        selected.put(player.getUniqueId(), tamedEntity);
        player.sendMessage(ChatColor.GRAY + "You have selected the " + tamedEntity.getType().toString().toLowerCase() + ". Select the player you would like to transfer this pet to.");

    }

    @EventHandler
    public void onRightClickPlayer(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!selected.containsKey(player.getUniqueId())) return;


        Entity clicked = event.getRightClicked();
        if (!(clicked instanceof Player target)) return;

        Tameable tamedEntity = selected.remove(player.getUniqueId());
        tamedEntity.setOwner(target);

        player.sendMessage(ChatColor.GRAY + "You have transferred the " + tamedEntity.getType().toString().toLowerCase() + " to " + getPlayerNameWithColor(target) + ChatColor.GRAY + "!");
        target.sendMessage(getPlayerNameWithColor(player) + " has transferred the " + tamedEntity.getType().toString().toLowerCase() + " to you!");

    }

    private String getPlayerNameWithColor(Player player) {
        Team team = player.getScoreboard().getEntryTeam(player.getName());
        if (team != null) {
            return team.getColor() + player.getName();
        }
        return player.getName();
    }
}
