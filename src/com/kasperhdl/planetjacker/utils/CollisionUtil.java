package com.kasperhdl.planetjacker.utils;

import com.kasperhdl.planetjacker.entities.Body;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by @Kasper on 05/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class CollisionUtil {

    public static Vector2f[] otherPoints;

    public static ArrayList<Vector2f> drawLines = new ArrayList<Vector2f>();

    public static Vector2f checkCollision(Body b1, Body b2){

        //TODO Currently moveing colliding object by pushing DIRECTLY away from other body (position - position)

        drawLines.clear();

        Vector2f dPos = new Vector2f(b2.position.x - b1.position.x,b2.position.y - b1.position.y);
        float dAng = b2.rotation - b1.rotation;

        otherPoints = new Vector2f[b2.points.length];

        //rotate then translate
        for(int i = 0;i< otherPoints.length;i++){
            otherPoints[i] = new Vector2f(b2.points[i]);
            VectorUtil.rotateVector(otherPoints[i], b2.rotation);
            otherPoints[i].add(dPos);
            VectorUtil.rotateVector(otherPoints[i], -b1.rotation);
        }

        // Vector2f minNormal = new Vector2f(0,0); //TODO should be used
        float minDist = Float.MAX_VALUE;

        for(int i = 0;i<b1.points.length;i++){
            Vector2f perp = b1.middlePoints[i];

            //projects points to perpendicular and find the extremes
            Vector2f[] projectedPoints = projectPointsOnPerpendicular(b1.points,perp);
            Vector2f[] b1Extremes = getExtremePoints(projectedPoints);
            float b1Dist = b1Extremes[0].distance(b1Extremes[1]);

            projectedPoints = projectPointsOnPerpendicular(otherPoints,perp);
            Vector2f[] b2Extremes = getExtremePoints(projectedPoints);
            float b2Dist = b2Extremes[0].distance(b2Extremes[1]);

            Vector2f[] allExtremes = {b1Extremes[0],b1Extremes[1],b2Extremes[0],b2Extremes[1]};
            Vector2f[] extremeOfExtremes = getExtremePoints(allExtremes);
            float dist = extremeOfExtremes[0].distance(extremeOfExtremes[1]);

            drawLines.add(allExtremes[0]);
            drawLines.add(allExtremes[1]);
            drawLines.add(allExtremes[2]);
            drawLines.add(allExtremes[3]);
            drawLines.add(extremeOfExtremes[0]);
            drawLines.add(extremeOfExtremes[1]);

            if(b1Dist + b2Dist < dist) {
                return null;
            }else if((b1Dist + b2Dist) - dist < minDist){
                minDist = (b1Dist + b2Dist) - dist;
                //minNormal = perp.getNormal(); //TODO should be used
            }
        }
        dPos.scale(minDist);
        return dPos;
    }

    private static Vector2f[] projectPointsOnPerpendicular(Vector2f[] points, Vector2f perp){
        Vector2f[] projectedPoints = new Vector2f[points.length];
        Vector2f proj;
        int c = 0;

        for (Vector2f point : points) {
            proj = new Vector2f(0, 0);
            point.projectOntoUnit(perp.getNormal(), proj);
            projectedPoints[c] = proj;
            c++;
        }

        return projectedPoints;
    }

    private static Vector2f[] getExtremePoints(Vector2f[] points){
        float extDist = 0;
        int maxInd = 0;

        for(int p = 1;p<points.length;p++){
            float dist = points[p].distance(points[0]);
            if(dist > extDist){
                extDist = dist;
                maxInd = p;
            }
        }

        int minInd = 0;
        extDist = 0;

        for(int p = 0;p<points.length;p++){
            if(p == maxInd)continue;

            float dist = points[p].distance(points[maxInd]);
            if(dist > extDist){
                extDist = dist;
                minInd = p;
            }
        }

        return new Vector2f[]{points[minInd],points[maxInd]};
    }

    public static Vector2f[] floatsToVector2(float[] floats){
        Vector2f[] vectors = new Vector2f[floats.length/2];

        for(int i = 0;i<vectors.length;i++){
            vectors[i] = new Vector2f(floats[i*2],floats[i*2+1]);
        }

        return vectors;
    }

    public static float[] vector2ToFloats(Vector2f[] vectors){
        float[] floats = new float[vectors.length*2];

        for(int i = 0;i<vectors.length;i++){
            floats[i*2]     = vectors[i].x;
            floats[i*2+1]   = vectors[i].y;
        }

        return floats;
    }
}
