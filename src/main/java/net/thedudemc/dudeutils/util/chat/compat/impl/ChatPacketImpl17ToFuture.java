package net.thedudemc.dudeutils.util.chat.compat.impl;


import net.thedudemc.dudeutils.util.chat.ReflectionHelper;
import net.thedudemc.dudeutils.util.chat.compat.ChatPacketCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public class ChatPacketImpl17ToFuture implements ChatPacketCompat {
    private final Constructor<?> chatPacketContructor;
    private static Object enumChatMessageTypeMessage;
    private static Object enumChatMessageTypeActionbar;

    public ChatPacketImpl17ToFuture() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> NMS_I_CHAT_BASE_COMPONENT = ReflectionHelper.getClass("net.minecraft.network.chat.IChatBaseComponent");
        Class<?> NMS_CHAT_MESSAGE_TYPE = ReflectionHelper.getClass("net.minecraft.network.chat.ChatMessageType");
        Class<?> NMS_PACKET_PLAY_OUT_CHAT = ReflectionHelper.getClass("net.minecraft.network.protocol.game.PacketPlayOutChat");

        chatPacketContructor = NMS_PACKET_PLAY_OUT_CHAT.getConstructor(
                NMS_I_CHAT_BASE_COMPONENT,
                NMS_CHAT_MESSAGE_TYPE,
                UUID.class
        );

        Method getChatMessageType = NMS_CHAT_MESSAGE_TYPE.getMethod("a", byte.class);

        enumChatMessageTypeMessage = getChatMessageType.invoke(null, (byte) 1);
        enumChatMessageTypeActionbar = getChatMessageType.invoke(null, (byte) 2);

    }

    private Object createPacket(Object chatComponent, Object type) {
        try {
            return chatPacketContructor.newInstance(
                    chatComponent,
                    type,
                    UUID.randomUUID()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object createActionbarPacket(Object chatComponent) {
        return createPacket(chatComponent, enumChatMessageTypeActionbar);
    }

    @Override
    public Object createTextPacket(Object chatComponent) {
        return createPacket(chatComponent, enumChatMessageTypeMessage);
    }
}
