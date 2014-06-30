package com.ebol4.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraHandler {
    static OrthographicCamera camera;
    static float defaultZoom = 1.5f;
    static double zoomVel = 0;
    static double zoomAccel = 0.006;
    static double zoomMaxVel = 0.06;
    static boolean accelerating = false;
    static float zoomInMax = 0.27f;
    static float zoomOutMax = 9;
    static double resetZoomVel = 0;
    static double resetZoomAccel = 0.008;
    static double resetZoomMaxVel = 0.072;
    static float resetZoomHalfwayPoint;
    static boolean resettingZoom = false;

    public static void initialize() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        Gdx.gl.glClearColor(0,0,0,1);
        camera.zoom = defaultZoom;
    }

    public static void update() {
        // ***  BEGIN ZOOM HANDLING  *** //
        if(accelerating || (zoomVel != 0 && !resettingZoom)){

            //Fix weird double/float inaccuracies. zoomVel would get stuck at 0.0059999999...
            if((zoomVel > 0 && zoomVel < 0.006) || (zoomVel < 0 && zoomVel > -0.006))
                stopZoom();

            //Slow down if we're not currently zooming, but there is zoom motion occurring
            if(!accelerating && zoomVel > 0)
                zoomVel -= zoomAccel;
            else if(!accelerating && zoomVel < 0)
                zoomVel += zoomAccel;

            //Speed up if we ARE zooming
            else if(accelerating && zoomVel > 0 && zoomVel < zoomMaxVel) //zooming IN
                zoomVel += zoomAccel;
            else if(accelerating && zoomVel < 0 && zoomVel > -zoomMaxVel) //zooming OUT
                zoomVel -= zoomAccel;
            //Final catch-all. Stop accelerating if something weird has happened, or zoomVel==0
            else accelerating = false;

        }

        //Resetting zoom...
        if(resettingZoom) {
            //TODO
        }

        //Make sure they don't zoom too far in/out.
        if(camera.zoom >= zoomInMax && camera.zoom <= zoomOutMax) //All is well, zoom normally.
            camera.zoom += zoomVel;
        else if(camera.zoom < zoomInMax) { //They've zoomed in too far!
            stopZoom();
            camera.zoom = zoomInMax;
        }
        else if(camera.zoom > zoomOutMax) { //They've zoomed out too far!
            stopZoom();
            camera.zoom = zoomOutMax;
        }
        // ***  END ZOOM HANDLING  *** //

        camera.position.x = Player.x;
        camera.position.y = Player.y;
        System.out.println("zoom: "+zoomVel);
        camera.update();
    }

    public static void zoomIn() {
        accelerating = true;
        zoomVel += zoomAccel;
        cancelResetZoom();
    }
    public static void zoomOut() {
        accelerating = true;
        zoomVel -= zoomAccel;
        cancelResetZoom();
    }

    public static void resetZoom() {
        if(camera.zoom != 1) {
            resettingZoom = true;
            resetZoomHalfwayPoint = (camera.zoom-defaultZoom)/2+defaultZoom;
        }

    }
    static void cancelResetZoom() {
        resettingZoom = false;
        resetZoomVel = 0;
    }

    public static void stopZoom() {
        accelerating = false;
        zoomVel = 0;
    }
}
