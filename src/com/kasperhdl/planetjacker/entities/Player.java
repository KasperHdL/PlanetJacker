package com.kasperhdl.planetjacker.entities;

import com.kasperhdl.planetjacker.utils.VectorUtil;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
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

public class Player extends Body {

    public Vector2f[] debugVectors;

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

    public float rotationAcceleration;
    public float rotationVelocity;



    private int upKey,downKey,leftKey,rightKey;
    private boolean upKeyDown,downKeyDown,leftKeyDown,rightKeyDown;


    Color[] colors;
    Random rnd;

    public Player(){
        rnd = new Random();
        colors = new Color[10];
        for (int i = 0;i<colors.length;i++)
            colors[i] = new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255));


        size = new Vector2f(20f,20f);

        force = .02f;
        rotationForce = 500f;
        mass = 1f;

        rotationAcceleration = 0;
        rotationVelocity = 0;

        inertia = .95f;
        friction = .99f;

        angularInertia = .9f;
        angularFriction = .9f;

        maxAngularVelocity = 1.2f;
        maxVelocity = 1f;


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

        System.out.println("done making a player");
        initShape();
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

        g.setColor(Color.white);

        g.draw(shape);
        g.popTransform();
    }


    private void move(float delta){
        float inputX;
        float inputY;

        //rotation
        rotation += rotationVelocity * delta;

        if(Math.abs(rotationVelocity) > maxAngularVelocity)
            rotationVelocity = Math.signum(rotationVelocity) * maxAngularVelocity * delta;
        else
            rotationVelocity += rotationAcceleration * delta;

        if(leftKeyDown)
            inputX = -1;
        else if(rightKeyDown)
            inputX = 1;
        else
            inputX = 0;

        rotationAcceleration += inputX * rotationForce;

        rotationAcceleration *= angularInertia;
        rotationVelocity *= angularFriction;

        //position

        Vector2f tmpVel = new Vector2f(velocity);
        tmpVel.scale(delta);

        position.add(velocity);

        Vector2f tmpAcc = new Vector2f(acceleration);
        tmpAcc.scale(delta);

        if(velocity.length() > maxVelocity)
            velocity = velocity.getNormal().scale(maxVelocity);
        else
            velocity.add(tmpAcc);


        if(upKeyDown)
            inputY = 1;
        else if(downKeyDown)
            inputY = -1;
        else
            inputY = 0;

        Vector2f input = new Vector2f(inputY,0);
        VectorUtil.rotateVector(input, rotation);

        input.scale(force / mass);

        acceleration.add(input);

        acceleration.scale(inertia);
        velocity.scale(friction);


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
