package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.networking.*;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.screen.*;
import com.mygdx.progarksurvive.Prefs;
import dagger.Lazy;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

@Singleton
public class Main extends com.badlogic.gdx.Game {
    private final Prefs prefs;
    private boolean gameRunning = false;
    public PlayServices playServices;



    @Inject
    Lazy<LoadingScreen> loadingScreen;

    @Inject
    Lazy<GameScreen> gameScreen;

    @Inject
    Lazy<MainMenuScreen> mainMenuScreen;

    @Inject
    Lazy<SettingsScreen> settingsScreen;

    @Inject
    Lazy<NetworkingScreen> networkingScreen;

    @Inject
    public Main(){
        super();
        this.prefs = new Prefs();
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
            case SETTINGS:
                setScreen(settingsScreen.get());
                break;
            case LOADING:
                setScreen(loadingScreen.get());
                break;
            case MAIN_MENU:
                setScreen(mainMenuScreen.get());
                break;
            case NETWORKING:
                setScreen(networkingScreen.get());
                break;
            case GAME:
                setScreen(gameScreen.get());
                break;
        }
    }

    public Prefs getPrefs(){return prefs;}

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
}
