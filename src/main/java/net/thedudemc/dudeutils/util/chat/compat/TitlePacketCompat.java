package net.thedudemc.dudeutils.util.chat.compat;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public interface TitlePacketCompat {

    Object createTitleTextPacket(Object chatComponent);

    Object createTitleTimesPacket(int fadeIn, int stay, int fadeOut);

    Object createSubtitlePacket(Object chatComponent);
}
