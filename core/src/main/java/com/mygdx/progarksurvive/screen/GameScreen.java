package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.progarksurvive.controller.GameController;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameScreen implements Screen {

    GameController controller;

    @Inject
    public GameScreen(GameController controller){
        this.controller = controller;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);

        // Loading textures after Gdx has been built
        controller.load_textures();
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}