package com.kasperhdl.planetjacker;

import com.kasperhdl.planetjacker.entities.Planet;
import com.kasperhdl.planetjacker.entities.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
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

public class World {

    //public ArrayList<Player> players;
    public Player player;
    public ArrayList<Planet> planets;


    public World(){
        //players = new ArrayList<Player>();
        planets = new ArrayList<Planet>();

        player = new Player();

        Random rnd = new Random();
        float rad = 50;
        int points = 7;
        float randomness = 15;

        int numPlanets = 10;

//        float step = (float)(Math.PI*2)/numPlanets;
  //      for(float a = 0;a < Math.PI*2;a += step){
    float a = 0;
            planets.add(new Planet(new Vector2f((float)Math.cos(a) * 250 + Game.SCREEN_WIDTH/2,(float)Math.sin(a) * 200 + Game.SCREEN_HEIGHT/2),rad,points,randomness));
    //    }


        player.debugVectors = floatsToVector2(player.shape.getPoints());
        planets.get(0).debugVectors = floatsToVector2(planets.get(0).shape.getPoints());
    }

    public void update(float delta){
        //for(Player player : players)
            player.update(delta);



        for(Planet planet : planets){
            planet.update(delta);
        }


    }

    public void render(Graphics g){
        for(Planet planet : planets){
            planet.render(g);
        }

       // for(Player player : players)
            player.render(g);

    }

    public boolean checkCollision(Polygon p1, Polygon p2){







        return false;
    }

    public Vector2f[] floatsToVector2(float[] floats){
        Vector2f[] vectors = new Vector2f[floats.length/2];

        for(int i = 0;i<vectors.length;i++){
            vectors[i] = new Vector2f(floats[i*2],floats[i*2+1]);
        }

        return vectors;
    }
}
