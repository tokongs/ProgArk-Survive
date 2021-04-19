package com.mygdx.progarksurvive.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitycomponents.VelocityComponent;
import com.mygdx.progarksurvive.model.entitysystems.RenderSystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameController implements InputProcessor {

    private final GameModel model;
    private final Engine ashley;
    private float touchX, touchY;
    private boolean touchDown = false;
    World world = new World(new Vector2(0, 0), true);
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    @Inject
    public GameController(GameModel model, Engine ashley){
        this.model = model;
        this.ashley = ashley;
    }

    private void movePlayer(Camera camera){
        VelocityComponent velocityComponent = model.player.getComponent(VelocityComponent.class);
        PositionComponent positionComponent = model.player.getComponent(PositionComponent.class);
        Vector3 touch = camera.unproject(new Vector3(touchX, touchY, 0));
        if(touchDown ){
            velocityComponent.velocity.x = 2 * (touch.x - positionComponent.position.x);
            velocityComponent.velocity.y = 2 * (touch.y - positionComponent.position.y);
        } else {
            velocityComponent.velocity.set(0, 0);
        }

    }

    public void update(float delta, Camera camera){
        movePlayer(camera);
        ashley.update(delta);
        world.step(1/60f, 6, 2);
        debugRenderer.render(world, camera.combined);
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
