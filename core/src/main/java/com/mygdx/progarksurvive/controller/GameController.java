package com.mygdx.progarksurvive.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameController implements InputProcessor {

    private final GameModel model;
    private float touchX, touchY;
    private boolean touchDown = false;
    private final Main game;
    @Inject
    public GameController(GameModel model, Main game) {
        this.game = game;
        this.model = model;
    }

    private void movePlayer(Camera camera) {
        PhysicsBodyComponent physicsBodyComponent = model.player.entity.getComponent(PhysicsBodyComponent.class);
        Vector3 touch = camera.unproject(new Vector3(touchX, touchY, 0));

        if (touchDown) {
            Vector2 direction = new Vector2(touch.x - physicsBodyComponent.body.getPosition().x, touch.y - physicsBodyComponent.body.getPosition().y).limit(1);
            physicsBodyComponent.body.setLinearVelocity(direction.scl(100));
        } else {
            physicsBodyComponent.body.setLinearVelocity(new Vector2(0, 0));
        }
    }

    public void update(float delta, Camera camera) {
        if(game.getIsGameHost()){
            movePlayer(camera);
        }
        model.debugRender(camera.combined);
        model.update(delta);
    }

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDown = true;
        touchX = screenX;
        touchY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchDown = false;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchX = screenX;
        touchY = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
