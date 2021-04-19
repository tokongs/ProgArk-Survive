package com.mygdx.progarksurvive.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameController implements InputProcessor {

    private final GameModel model;
    private final Engine ashley;
    private float touchX, touchY;
    private boolean touchDown = false;

    @Inject
    public GameController(GameModel model, Engine ashley) {
        this.model = model;
        this.ashley = ashley;
    }

    private void movePlayer(Camera camera) {
        PhysicsBodyComponent physicsBodyComponent = model.player.entity.getComponent(PhysicsBodyComponent.class);
        Vector3 touch = camera.unproject(new Vector3(touchX, touchY, 0));

        if (touchDown) {
            physicsBodyComponent.body.setLinearVelocity(
                    new Vector2(2 * (touch.x - physicsBodyComponent.body.getPosition().x), 2 * (touch.y - physicsBodyComponent.body.getPosition().y))
            );
        } else {
            physicsBodyComponent.body.setLinearVelocity(new Vector2(0, 0));
        }
    }

    public void update(float delta, Camera camera) {
        movePlayer(camera);
        ashley.update(delta);
        model.update();
        model.debugRender(camera.combined);
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
