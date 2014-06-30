package com.ebol4.libgdxgame;

public class Bullet {

    public static class Type {
        public static final int NORMAL = 0;
        public static final int SPECTRAL = 1;
        public static final int SPLIT = 2;
        public static final int EXPLOSIVE = 3;
    }


    float r = 0;
    float g = 0.7f;
    float b = 1;
    float a = 1;
    float xVel = 0;
    float yVel = 0;
    float x;
    float y;
    float damage;
    int radius;
    int type;

    public Bullet(int type, float x, float y, float damage, float xVel, float yVel) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.xVel = xVel;
        this.yVel = yVel;

        if(damage < 30) {
            this.radius = 10;
        }
        else {
            this.r = 0.7f;
            this.g = 0.2f;
            this.b = 0.2f;
            this.radius = 20;
        }
    }

}
