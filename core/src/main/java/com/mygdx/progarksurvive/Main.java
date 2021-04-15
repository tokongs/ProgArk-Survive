package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.networking.*;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.screen.GameScreen;
import com.mygdx.progarksurvive.screen.LoadingScreen;
import dagger.Lazy;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

@Singleton
public class Main extends com.badlogic.gdx.Game {

    private final AssetManager assetManager;

    @Inject
    Lazy<LoadingScreen> loadingScreen;

    @Inject
    Lazy<GameScreen> gameScreen;

    @Inject
    Lazy<GameScreen> mainMenuScreen;

    @Inject
    public Main(AssetManager assetManager){
        super();
        this.assetManager = assetManager;
    }

    @Override
    public void create() {
        setState(GameState.LOADING);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }

    public void setState(GameState state){
        switch (state) {
            case LOADING:
                setScreen(loadingScreen.get());
                break;
            case MAIN_MENU:
                setScreen(mainMenuScreen.get());
                break;
            case GAME:
                setScreen(gameScreen.get());
                break;
        }
    }
}