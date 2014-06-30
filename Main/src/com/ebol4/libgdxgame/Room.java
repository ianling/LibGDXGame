package com.ebol4.libgdxgame;

import com.badlogic.gdx.math.Rectangle;

public class Room {
    public static class Type {
        public static final int NORMAL = 0;
        public static final int SHOP = 1;
        public static final int ITEM = 2;
        public static final int BOSS = 3;
    }
    float x;
    float y;
    float width;
    float height;
    int type;
    float[] vertices;
    boolean visited = false;

    public Room(int type, float x, float y, float width, float height){
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vertices = new float[]{x,y, x+width,y, x+width,y+height, x,y+height};
    }

    public float getCenterX() {
        return x + (width/2);
    }
    public float getCenterY() {
        return y + (height/2);
    }

    public float getLeftX(){
        return x;
    }
    public float getRightX(){
        return x + width;
    }

    public float getBottomY(){
        return y;
    }
    public float getTopY(){
        return y + height;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x,y,width,height);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void updateVertices() {
        vertices = new float[]{x,y, x+width,y, x+width,y+height, x,y+height};
    }
}