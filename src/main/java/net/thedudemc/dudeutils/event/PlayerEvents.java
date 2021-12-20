package net.thedudemc.dudeutils.event;

import net.thedudemc.dudeutils.command.PluginCommand;
import net.thedudemc.dudeutils.init.PluginCommands;
import net.thedudemc.dudeutils.init.PluginConfigs;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
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

            List<String> withId = new ArrayList<>();
            blocked.forEach(s -> withId.add("dudeutils:" + s));

            event.getCommands().removeAll(blocked);
            event.getCommands().removeAll(withId);
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

    private boolean isHostile(Entity entity) {
        return entity instanceof Monster ||
                entity instanceof Slime ||
                entity instanceof Phantom ||
                entity instanceof Hoglin ||
                entity instanceof Shulker;
    }

}
