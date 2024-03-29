package net.thedudemc.dudeutils.util.chat.compat.impl;

import net.thedudemc.dudeutils.util.chat.ReflectionHelper;
import net.thedudemc.dudeutils.util.chat.compat.ChatPacketCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public class ChatPacketImpl8To11 implements ChatPacketCompat {
    private final Constructor<?> chatPacketContructor;
    private final Field packetPlayOutChatComponent;
    private final Field packetPlayOutChatMessageType;

    public ChatPacketImpl8To11() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> NMS_PACKET_PLAY_OUT_CHAT = ReflectionHelper.getClass("{nms}.PacketPlayOutChat");

        chatPacketContructor = NMS_PACKET_PLAY_OUT_CHAT.getConstructor();
        packetPlayOutChatComponent = ReflectionHelper.getField(NMS_PACKET_PLAY_OUT_CHAT, "a");
        packetPlayOutChatMessageType = ReflectionHelper.getField(NMS_PACKET_PLAY_OUT_CHAT, "b");
    }

    private Object createPacket(Object chatComponent, byte type) {
        try {
            Object packet = chatPacketContructor.newInstance();
            ReflectionHelper.setFieldValue(packetPlayOutChatComponent, packet, chatComponent);
            ReflectionHelper.setFieldValue(packetPlayOutChatMessageType, packet, type);
            return packet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createActionbarPacket(Object chatComponent) {
        return createPacket(chatComponent, (byte) 2);
    }

    @Override
    public Object createTextPacket(Object chatComponent) {
        return createPacket(chatComponent, (byte) 1);
    }
}
