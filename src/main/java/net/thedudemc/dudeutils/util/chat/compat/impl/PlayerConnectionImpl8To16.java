


package net.thedudemc.dudeutils.util.chat.compat.impl;


import net.thedudemc.dudeutils.util.Log;
import net.thedudemc.dudeutils.util.chat.ReflectionHelper;
import net.thedudemc.dudeutils.util.chat.compat.PlayerConnectionCompat;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Rayzr see https://github.com/rayzr522/JSONMessage
 *
 * All code in this class is written by them and I am copying it via MIT License.
 *
 */
public class PlayerConnectionImpl8To16 implements PlayerConnectionCompat {

    private final Field playerConnectionField;
    private final MethodHandle GET_HANDLE;
    private final MethodHandle SEND_PACKET;

    public PlayerConnectionImpl8To16() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Class<?> CRAFT_PLAYER = ReflectionHelper.getClass("{obc}.entity.CraftPlayer");
        Class<?> NMS_PACKET = ReflectionHelper.getClass("{nms}.Packet");
        Class<?> NMS_ENTITY_PLAYER = ReflectionHelper.getClass("{nms}.EntityPlayer");

        playerConnectionField = ReflectionHelper.getFieldByTypeName(NMS_ENTITY_PLAYER, "PlayerConnection");

        Method getHandle = CRAFT_PLAYER.getMethod("getHandle");
        Method sendPacket = playerConnectionField.getType().getMethod("sendPacket", NMS_PACKET);

        GET_HANDLE = MethodHandles.lookup().unreflect(getHandle);
        SEND_PACKET = MethodHandles.lookup().unreflect(sendPacket);
    }

    @Override
    public void sendPacket(Object packet, Player... players) {
        for (Player player : players) {
            try {
                SEND_PACKET.bindTo(playerConnectionField.get(GET_HANDLE.bindTo(player).invoke())).invoke(packet);
            } catch (Throwable e) {
                Log.info("Failed to send packet: " + e);
            }
        }
    }
}
