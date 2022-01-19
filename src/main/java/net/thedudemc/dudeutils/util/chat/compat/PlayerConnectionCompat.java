package net.thedudemc.dudeutils.util.chat.compat;

import org.bukkit.entity.Player;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public interface PlayerConnectionCompat {
    void sendPacket(Object packet, Player... players);
}
