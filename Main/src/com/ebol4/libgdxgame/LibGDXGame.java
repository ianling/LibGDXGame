/*
LIBGDX GAME (UNTITLED) -- Ian Ling (ebol4)

TODO: resetCamera() in CameraHandler.java
 */
package com.ebol4.libgdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LibGDXGame extends Game {
    ShapeRenderer shapeRenderer;
    Player player;
    List<Bullet> bullets;
    float timeSinceLastBullet;
    float timeSinceLastFloorGen;
    GameInputProcessor inputProcessor;
    @Override
    public void create() {
        CameraHandler.initialize();
        shapeRenderer = new ShapeRenderer();
        inputProcessor = new GameInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
        Floor.generateFloor();

        //default player is spawned at the center of the first room, and is a 30x30 square
        Player.initialize();
        bullets = new ArrayList<Bullet>();
    }

    @Override
    public void render() {
        //If directions are pressed, increase or decrease velocity in that direction
        if(Gdx.input.isKeyPressed(Input.Keys.A)) Player.moveLeft();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) Player.moveRight();
        if(Gdx.input.isKeyPressed(Input.Keys.W)) Player.moveUp();
        if(Gdx.input.isKeyPressed(Input.Keys.S)) Player.moveDown();
        if(Gdx.input.isKeyPressed(Input.Keys.TAB)) CameraHandler.resetZoom();

        timeSinceLastFloorGen += Gdx.graphics.getDeltaTime();
        if(timeSinceLastFloorGen > 0.5f)
            if(Gdx.input.isKeyPressed(Input.Keys.C)) {
                timeSinceLastFloorGen = 0;
                Floor.generateFloor();
            }


        //Actually change the player's coordinates now
        Player.movePlayer();

        //Decelerate the player if they aren't moving along that axis, or if they are trying to move in the other direction
        if((!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) ||
           (!Gdx.input.isKeyPressed(Input.Keys.W) && Player.yVel < 0) ||
           (!Gdx.input.isKeyPressed(Input.Keys.S) && Player.yVel > 0))
                Player.decelerateY();
        if((!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) ||
           (!Gdx.input.isKeyPressed(Input.Keys.D) && Player.xVel < 0) ||
           (!Gdx.input.isKeyPressed(Input.Keys.A) && Player.xVel > 0))
                Player.decelerateX();


        timeSinceLastBullet += Gdx.graphics.getDeltaTime();
        if(timeSinceLastBullet > Player.bulletSpawnRate) {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) spawnBullet(Player.bulletType, Player.x, Player.y+(Player.height/2),Player.damage,-Player.bulletSpeed,Player.yVel/4);
            else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spawnBullet(Player.bulletType, Player.x+Player.width, Player.y+(Player.height/2),Player.damage,Player.bulletSpeed,Player.yVel/4);
            else if(Gdx.input.isKeyPressed(Input.Keys.UP)) spawnBullet(Player.bulletType, Player.x+(Player.width/2), Player.y+Player.height,Player.damage,Player.xVel/4,Player.bulletSpeed);
            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) spawnBullet(Player.bulletType, Player.x+(Player.width/2), Player.y,Player.damage,Player.xVel/4,-Player.bulletSpeed);
        }

        //Clear the previous frame
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        CameraHandler.update();

        shapeRenderer.setProjectionMatrix(CameraHandler.camera.combined);

        //**FILLED SHAPES**//
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Player.r,Player.g,Player.b,Player.a); //red
            shapeRenderer.rect(Player.x,Player.y,Player.width,Player.height); //player

            for(Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
                Bullet b = iterator.next();
                b.x += b.xVel * Gdx.graphics.getDeltaTime();
                b.y += b.yVel * Gdx.graphics.getDeltaTime();
                if(b.x > 725-b.radius || b.x < 75 || b.y > 525-b.radius || b.y < 75)
                    iterator.remove();
                else {
                    shapeRenderer.setColor(b.r, b.g, b.b, b.a);
                    shapeRenderer.circle(b.x, b.y, b.radius);
                }
            }
        shapeRenderer.end();

        //**LINE SHAPES**//
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);

        for(Room r: Floor.rooms) {
            shapeRenderer.polyline(r.vertices);
        }

        shapeRenderer.end();
    }

    public void spawnBullet(int type, float x, float y, float damage, float xVel, float yVel) {
        timeSinceLastBullet = 0;
        bullets.add(new Bullet(type, x, y, damage, xVel, yVel));
    }
}
