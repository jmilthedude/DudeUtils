package net.thedudemc.dudeutils.util.chat.compat.impl;


import com.google.gson.JsonObject;
import net.thedudemc.dudeutils.util.chat.ReflectionHelper;
import net.thedudemc.dudeutils.util.chat.compat.ChatComponentCompat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

public class ChatComponentImpl8To16 implements ChatComponentCompat {
    private final Constructor<?> chatComponentText;
    private final MethodHandle STRING_TO_CHAT;

    public ChatComponentImpl8To16() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Class<?> NMS_CHAT_COMPONENT_TEXT = ReflectionHelper.getClass("{nms}.ChatComponentText");
        Class<?> NMS_CHAT_SERIALIZER = ReflectionHelper.getClass("{nms}.IChatBaseComponent$ChatSerializer");

        chatComponentText = NMS_CHAT_COMPONENT_TEXT.getConstructor(String.class);
        STRING_TO_CHAT = MethodHandles.lookup().unreflect(
                NMS_CHAT_SERIALIZER.getMethod("a", String.class)
        );
    }

    @Override
    public Object createComponent(String message) {
        try {
            return chatComponentText.newInstance(message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object fromJson(JsonObject json) {
        try {
            return STRING_TO_CHAT.invoke(json.toString());
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
