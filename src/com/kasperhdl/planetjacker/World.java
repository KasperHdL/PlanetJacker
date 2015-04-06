package com.kasperhdl.planetjacker;

import com.kasperhdl.planetjacker.entities.Planet;
import com.kasperhdl.planetjacker.entities.Player;
import com.kasperhdl.planetjacker.utils.CollisionUtil;
import org.newdawn.slick.Color;
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

    boolean debug = false;

    public World(){
        //players = new ArrayList<Player>();
        planets = new ArrayList<Planet>();

        player = new Player();

        float rad = 50;
        int points = 10;
        float randomness = 0;

        int numPlanets = 10;

        float step = (float)(Math.PI*2)/numPlanets;
        for(float a = 0;a < Math.PI*2;a += step){
            planets.add(new Planet(new Vector2f((float)Math.cos(a) * 250 + Game.SCREEN_WIDTH/2,(float)Math.sin(a) * 200 + Game.SCREEN_HEIGHT/2),rad,points,randomness));
        }

    }

    public void update(float delta){
        //for(Player player : players)
            player.update(delta);



        for(Planet planet : planets){
            planet.update(delta);
            Vector2f diff = CollisionUtil.checkCollision(planet,player);
            if(diff != null) {
               player.position.add(diff.scale(player.velocity.length()));
            }
        }




    }

    public void render(Graphics g){
        // for(Player player : players)
        player.render(g);

        for(Planet planet : planets){
            planet.render(g);

            Vector2f diff = CollisionUtil.checkCollision(player,planet);
            if(diff != null) {
                g.setColor(Color.red);
                diff.scale(player.velocity.length());
                g.drawLine(player.position.x, player.position.y, player.position.x - diff.x, player.position.y - diff.y);
            }
        }

        if(debug)
            renderdebug(g);

    }

    private void renderdebug(Graphics g){

        //debug rendering
        g.pushTransform();
        g.translate(player.position.x, player.position.y);
        g.rotate(0,0, player.rotation);
        g.setColor(Color.green);
        for(int i = 0;i<CollisionUtil.drawLines.size()/2;i++){
            if(i == 0)g.setColor(Color.green);
            if(i == 3)g.setColor(Color.cyan);
            if(i == 6)g.setColor(Color.pink);
            g.pushTransform();
            g.translate(i%3 * 10, 0);
            g.drawLine(CollisionUtil.drawLines.get(i * 2).x, CollisionUtil.drawLines.get(i * 2).y, CollisionUtil.drawLines.get(i * 2 +1 ).x, CollisionUtil.drawLines.get(i * 2 + 1).y);

            g.popTransform();
        }
        g.popTransform();

        g.setColor(Color.white);

        g.pushTransform();
        g.translate(50,50);
        Polygon shape = new Polygon(CollisionUtil.vector2ToFloats(player.points));

        g.draw(shape);

        shape = new Polygon(CollisionUtil.vector2ToFloats(CollisionUtil.otherPoints));
        g.setColor(Color.cyan);
        g.draw(shape);
        g.popTransform();

    }
}
