package com.kasperhdl.planetjacker.entities;

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

    protected Vector2f acceleration;
    protected Vector2f velocity;

    public Body(){

    }
}
