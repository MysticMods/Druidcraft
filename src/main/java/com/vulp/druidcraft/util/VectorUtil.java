package com.vulp.druidcraft.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;

public class VectorUtil {

    public static Direction getDirection(Vec3d original, Vec3d destination) {
        double a = Math.abs(original.x) - Math.abs(destination.x);
        double b = Math.abs(original.y) - Math.abs(destination.y);
        double c = Math.abs(original.z) - Math.abs(destination.z);
        if (Math.abs(a) < Math.abs(b)) {
            if (Math.abs(c) < Math.abs(b)) {
                if (original.y - destination.y < 0)
                    return Direction.UP;
                else return Direction.DOWN;
            }
            if (original.x - destination.x < 0)
                return Direction.EAST;
            else return Direction.WEST;
        }
        if (original.z - destination.z < 0)
            return Direction.SOUTH;
        else return Direction.NORTH;
    }
}
