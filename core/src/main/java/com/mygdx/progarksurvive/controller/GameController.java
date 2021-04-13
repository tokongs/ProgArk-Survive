package com.mygdx.progarksurvive.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitysystems.RenderSystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameController implements InputProcessor {

    private final GameModel model;
    private final Engine ashley;

    @Inject
    public GameController(GameModel model, Engine ashley){
        this.model = model;
        this.ashley = ashley;
    }

    public void update(float delta){
        ashley.update(delta);
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
        System.out.println("test");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

    public void load_textures() {
        ashley.getSystem(RenderSystem.class).addAllTextures();
    }
}
