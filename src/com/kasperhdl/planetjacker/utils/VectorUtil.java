package com.kasperhdl.planetjacker.utils;

import org.newdawn.slick.geom.Vector2f;

import java.util.Vector;

/**
 * Created by @Kasper on 05/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class VectorUtil {

    public static void rotateVector(Vector2f vector, float angle){
        double cos = Math.cos(angle/180*Math.PI);
        double sin = Math.sin(angle/180*Math.PI);

        double nx = vector.x * cos - vector.y * sin;
        double ny = vector.x * sin + vector.y * cos;

        vector.x = (float) nx;
        vector.y = (float) ny;

    }
}
