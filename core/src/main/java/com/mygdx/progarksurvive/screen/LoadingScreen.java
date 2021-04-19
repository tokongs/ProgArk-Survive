package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;

import javax.inject.Inject;

public class LoadingScreen implements Screen {


    private final Main game;
    private final AssetManager assetManager;

    @Inject
    public LoadingScreen(Main game, AssetManager assetManager){
        this.game = game;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(assetManager.update()){
            game.setState(GameState.MAIN_MENU);
        }
        System.out.printf("loading: %f%%%n", assetManager.getProgress() * 100);
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