package com.kasperhdl.planetjacker.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 30/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Player extends Body {

    public Vector2f[] debugVectors;

    public Polygon shape;
    public Vector2f center;

    //physical props
    private float force;
    private float rotationForce;

    private float inertia;
    private float friction;
    private float angularInertia;
    private float angularFriction;

    private float maxAngularVelocity;
    private float maxVelocity;

    public float rotation;
    public float rotationAcceleration;
    public float rotationVelocity;



    private int upKey,downKey,leftKey,rightKey;
    private boolean upKeyDown,downKeyDown,leftKeyDown,rightKeyDown;

    public Player(){
        size = new Vector2f(20f,20f);

        force = .2f;
        rotationForce = 0.05f;
        mass = 100f;

        rotationAcceleration = 0;
        rotationVelocity = 0;

        inertia = .60f;
        friction = .99f;

        angularInertia = 0.7f;
        angularFriction = 0.7f;

        maxAngularVelocity = 1.2f;
        maxVelocity = 2f;


        rotation = 0;
        position = new Vector2f(200,200);
        velocity = new Vector2f(0,0);
        acceleration = new Vector2f(0,0);


        //key
        upKey = Input.KEY_UP;
        downKey = Input.KEY_DOWN;
        leftKey = Input.KEY_LEFT;
        rightKey = Input.KEY_RIGHT;
        //keyDown
        upKeyDown = false;
        downKeyDown = false;
        leftKeyDown = false;
        rightKeyDown = false;


        shape = new Polygon();
        shape.addPoint( size.x/2,0);
        shape.addPoint(-size.x/2,-size.y/2);
        shape.addPoint(-size.x/2, size.y/2);

        center = new Vector2f(shape.getCenterX(),shape.getCenterY());
        System.out.println(center);
    }

    @Override
    public void update(float delta){
        move(delta);
    }

    @Override
    public void render(Graphics g){


        g.pushTransform();
        g.translate(position.x, position.y);
        g.rotate(0, 0, rotation);

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


    private void move(float delta){
        float inputX = 0;
        float inputY = 0;

        //rotation

        rotationAcceleration *= angularInertia;
        rotationVelocity *= angularFriction;

        if(leftKeyDown)
            inputX = -1;
        else if(rightKeyDown)
            inputX = 1;
        else
            inputX = 0;

        rotationAcceleration += inputX * rotationForce;

        if(Math.abs(rotationVelocity) > maxAngularVelocity)
            rotationVelocity = Math.signum(rotationVelocity) * maxAngularVelocity;
        else
            rotationVelocity += rotationAcceleration;

        rotation += rotationVelocity;

        //position


        acceleration.scale(inertia);
        velocity.scale(friction);

        if(upKeyDown)
            inputY = 1;
        else if(downKeyDown)
            inputY = -1;
        else
            inputY = 0;

        Vector2f input = new Vector2f(inputY,0);

        double cos = Math.cos(rotation/180*Math.PI);
        double sin = Math.sin(rotation/180*Math.PI);

        double nx = input.x * cos - input.y * sin;
        double ny = input.x * sin + input.y * cos;

        input.x = (float) nx;
        input.y = (float) ny;

        input.scale(force / mass);

        acceleration.add(input);


        if(velocity.length() > maxVelocity)
            velocity = velocity.getNormal().scale(maxVelocity);
        else
            velocity.add(acceleration);

        position.add(velocity);

    }

    public void keyPressed(int key) {

        if(upKey == key)
            upKeyDown = true;

        if(downKey == key)
            downKeyDown = true;

        if(leftKey == key)
            leftKeyDown = true;

        if(rightKey == key)
            rightKeyDown = true;

    }

    public void keyReleased(int key) {
        if(upKey == key)
            upKeyDown = false;

        if(downKey == key)
            downKeyDown = false;

        if(leftKey == key)
            leftKeyDown = false;

        if(rightKey == key)
            rightKeyDown = false;

    }

}
