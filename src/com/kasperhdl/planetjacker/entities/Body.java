package com.kasperhdl.planetjacker.entities;

import com.kasperhdl.planetjacker.utils.CollisionUtil;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 30/03/2015
 * <p/>
 * Description:
 * a body is an entity affected by gravity
 * <p/>
 * Usage:
 * ---
 */

public class Body extends Entity{

    protected float mass;
    public float rotation;

    public Vector2f[] points;
    public Vector2f[] middlePoints;
    public Polygon shape;

    public Vector2f acceleration;
    public Vector2f velocity;

    protected void initShape(){

        points = CollisionUtil.floatsToVector2(shape.getPoints());
        middlePoints = new Vector2f[points.length];

        //take points and find middle of line
        double lastT = 0;
        for (int i = 0; i < middlePoints.length; i++) {
            double t = points[i].getTheta() - lastT;
            Vector2f vector = new Vector2f(10,0);
            vector.setTheta(t/2);
            middlePoints[i] = vector;
        }

    }
}
