package com.kasperhdl.planetjacker.entities;

import com.kasperhdl.planetjacker.utils.VectorUtil;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;

/**
 * Created by @Kasper on 30/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Planet extends Body {

    public Vector2f[] debugVectors;

    public float radius;

    Color[] colors;
    float bitstep;

    /**
     * Constructor for Planet
     * @param position position
     * @param radius radius
     * @param numPoints number of points to make polygon
     * @param randomness how much each point vary from radius
     */
    public Planet(Vector2f position,float radius,int numPoints,float randomness){
        this.position = position;
        this.radius = radius;

        Random rnd = new Random();

        colors = new Color[numPoints*2];

        shape = new Polygon();
        float step = (float)(Math.PI*2)/(numPoints+1);
        bitstep = step;
        int c = 0;
        for(float a = 0;a > -Math.PI*2;a -= step) {
                colors[c] = new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255));
                shape.addPoint((float) Math.cos(a) * radius + rnd.nextFloat() * randomness, (float) Math.sin(a) * radius + rnd.nextFloat() * randomness);
            c++;
        }
        System.out.println("done making planet");
        initShape();
    }

    @Override
    public void update(float delta){
        rotation += 0.05f;
    }

    @Override
    public void render(Graphics g){
        g.pushTransform();
        g.translate(position.x, position.y);
        g.rotate(0, 0, rotation);
        g.setColor(Color.white);
        g.draw(shape);

        g.popTransform();
    }
}
