package com.ebol4.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;

public class Player {
    static float r = 1;
    static float g = 0;
    static float b = 0;
    static float a = 1;
    static float xVel = 0;
    static float yVel = 0;
    static float acceleration = 20;
    static float maxSpeed = 200;
    static int damage = 25;
    static float bulletSpeed = 250;
    static float bulletSpawnRate = 0.4f;
    static int bulletType = Bullet.Type.NORMAL;
    static float x;
    static float y;
    static float width;
    static float height;
    static int[] items;


    public static void initialize() {
        x = Floor.rooms.get(0).getCenterX();
        y = Floor.rooms.get(0).getCenterY();
        width = 30;
        height = 30;
    }

    //Returns a Rectangle surrounding the player
    public static Rectangle getRectangle() {
        return new Rectangle(x,y,width,height);
    }

    //Decrease x-velocity
    public static void moveLeft() {
        if(xVel > -maxSpeed)
            xVel -= 2*acceleration;
    }
    //Increase x-velocity
    public static void moveRight() {
        if(xVel < maxSpeed)
            xVel += 2*acceleration;
    }

    //Decrease y-velocity
    public static void moveDown() {
        if(yVel > -maxSpeed)
            yVel -= 2*acceleration;
    }
    //Increase y-velocity
    public static void moveUp() {
        if(yVel < maxSpeed)
            yVel += 2*acceleration;
    }
    public static void movePlayer() {
        x += xVel * Gdx.graphics.getDeltaTime();
        y += yVel * Gdx.graphics.getDeltaTime();
    }

    public static void decelerateY() {
        if(yVel > 0)
            yVel -= acceleration;
        else if(yVel < 0)
            yVel += acceleration;
    }
    public static void decelerateX() {
        if(xVel > 0)
            xVel -= acceleration;
        else if (xVel < 0)
            xVel += acceleration;
    }
}
