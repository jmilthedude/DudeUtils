package net.thedudemc.dudeutils.util.chat.compat;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public interface ChatPacketCompat {
    Object createActionbarPacket(Object chatComponent);
    Object createTextPacket(Object chatComponent);
}
