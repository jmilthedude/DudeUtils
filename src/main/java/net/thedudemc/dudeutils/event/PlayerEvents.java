package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.command.PluginCommand;
import net.thedudemc.dudeutils.features.deathpoint.DeathLocation;
import net.thedudemc.dudeutils.init.PluginCommands;
import net.thedudemc.dudeutils.init.PluginConfigs;
import net.thedudemc.dudeutils.init.PluginData;
import net.thedudemc.dudeutils.util.StringUtils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        if (!event.getPlayer().isOp()) {
            List<String> blocked = PluginCommands.REGISTRY.values()
                    .stream()
                    .filter(PluginCommand::isOpOnly)
                    .map(PluginCommand::getName)
                    .collect(Collectors.toList());

            System.out.println(blocked);

            event.getCommands().removeAll(blocked);
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            if (isHostile(event.getEntity())) {
                Player player = (Player) event.getTarget();
                if (PluginConfigs.SPECIAL.IGNORED_BY_HOSTILES.contains(player.getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        DeathLocation location = PluginData.DEATHPOINT.setDeathpoint(event.getEntity());
        event.getEntity().sendMessage("You died at: " + StringUtils.getCoordinateString(location));
    }

    private boolean isHostile(Entity entity) {
        return entity instanceof Monster ||
                entity instanceof Slime ||
                entity instanceof Phantom ||
                entity instanceof Hoglin ||
                entity instanceof Shulker;
    }

}
