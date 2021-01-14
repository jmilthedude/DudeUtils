package net.thedudemc.dudeutils.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorHelper {

    public static Vector getDirectionNormalized(Vector destination, Vector origin) {
        return new Vector(destination.getX() - origin.getX(), destination.getY() - origin.getY(), destination.getZ() - origin.getZ()).normalize();
    }

    public static Vector getDirection(Vector destination, Vector origin) {
        return new Vector(destination.getX() - origin.getX(), destination.getY() - origin.getY(), destination.getZ() - origin.getZ());
    }

    public static Vector getVectorFromPos(Location pos) {
        return new Vector(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector add(Vector a, Vector b) {
        return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
    }

    public static Vector subtract(Vector a, Vector b) {
        return new Vector(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
    }

    public static Vector multiply(Vector velocity, float speed) {

        return new Vector(velocity.getX() * speed, velocity.getY() * speed, velocity.getZ() * speed);
    }

    public static Vector getMovementVelocity(Vector current, Vector target, float speed) {

        Vector velocity = VectorHelper.multiply(getDirectionNormalized(target, current), speed);

        return velocity;
    }

}