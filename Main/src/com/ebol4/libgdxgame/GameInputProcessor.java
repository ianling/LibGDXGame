package com.ebol4.libgdxgame;

import com.badlogic.gdx.InputProcessor;

class GameInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        if(amount == 1)
            CameraHandler.zoomIn();
        else if(amount == -1)
            CameraHandler.zoomOut();
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}