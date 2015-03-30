package com.kasperhdl.planetjacker.entities;

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
    public Polygon shape;

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

        shape = new Polygon();
        float step = (float)(Math.PI*2)/numPoints;
        for(float a = 0;a > -Math.PI*2+step;a -= step){
            shape.addPoint((float)Math.cos(a)*radius + rnd.nextFloat()*randomness,(float)Math.sin(a)*radius + rnd.nextFloat()*randomness);
        }

    }

    @Override
    public void update(float delta){

    }

    @Override
    public void render(Graphics g){
        g.pushTransform();
        g.translate(position.x, position.y);



        for(int i = 0;i<debugVectors.length;i++){
            Vector2f point = new Vector2f(shape.getPoint(i)[0],shape.getPoint(i)[1]);
            Vector2f perp = debugVectors[i].getPerpendicular();
            g.setColor(Color.red);
            g.drawLine(point.x, point.y, point.x + perp.x, point.y + perp.y);
            g.setColor(Color.green);

            Vector2f[] projPoints = new Vector2f[debugVectors.length-1];

            int c = 0;

            for(int j = 0;j<debugVectors.length;j++) {
                if (i == j) continue;
                Vector2f proj = new Vector2f(0,0);
                debugVectors[j].projectOntoUnit(perp.getNormal(), proj);

                projPoints[c] = proj;

                c++;
            }

            float extDist = 0;
            int maxInd = 0;

            for(int p = 1;p<projPoints.length;p++){
                float dist = projPoints[p].distance(projPoints[0]);
                if(dist > extDist){
                    extDist = dist;
                    maxInd = p;
                }
            }

            int minInd = 0;
            if(maxInd == minInd)minInd = 1;

            for(int p = 0;p<projPoints.length;p++){
                if(p == maxInd)continue;

                float dist = projPoints[p].distance(projPoints[maxInd]);
                if(dist > extDist){
                    extDist = dist;
                    minInd = p;
                }
            }
            g.setColor(Color.green);
            g.drawLine(projPoints[maxInd].x,projPoints[maxInd].y,projPoints[minInd].x,projPoints[minInd].y);
        }

        g.setColor(Color.white);

        g.draw(shape);

        g.popTransform();
    }
}
