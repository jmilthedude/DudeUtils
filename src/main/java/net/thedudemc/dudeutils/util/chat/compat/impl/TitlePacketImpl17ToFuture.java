package net.thedudemc.dudeutils.util.chat.compat.impl;


import net.thedudemc.dudeutils.util.chat.ReflectionHelper;
import net.thedudemc.dudeutils.util.chat.compat.TitlePacketCompat;

import java.lang.reflect.Constructor;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public class TitlePacketImpl17ToFuture implements TitlePacketCompat {

    private final Constructor<?> titleTextPacketConstructor;
    private final Constructor<?> subtitleTextPacketConstructor;
    private final Constructor<?> titlesAnimationPacketConstructor;

    public TitlePacketImpl17ToFuture() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> NMS_I_CHAT_BASE_COMPONENT = ReflectionHelper.getClass("net.minecraft.network.chat.IChatBaseComponent");

        titleTextPacketConstructor = ReflectionHelper.getClass("net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket")
                .getConstructor(NMS_I_CHAT_BASE_COMPONENT);
        subtitleTextPacketConstructor = ReflectionHelper.getClass("net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket")
                .getConstructor(NMS_I_CHAT_BASE_COMPONENT);
        titlesAnimationPacketConstructor = ReflectionHelper.getClass("net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket")
                .getConstructor(int.class, int.class, int.class);
    }

    @Override
    public Object createTitleTextPacket(Object chatComponent) {
        try {
            return titleTextPacketConstructor.newInstance(chatComponent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createTitleTimesPacket(int fadeIn, int stay, int fadeOut) {
        try {
            return titlesAnimationPacketConstructor.newInstance(fadeIn, stay, fadeOut);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createSubtitlePacket(Object chatComponent) {
        try {
            return subtitleTextPacketConstructor.newInstance(chatComponent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
