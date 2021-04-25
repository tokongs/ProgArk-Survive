package com.mygdx.progarksurvive;

import com.mygdx.progarksurvive.screen.*;

import dagger.Lazy;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Main extends com.badlogic.gdx.Game {
    private final Prefs prefs;
    private boolean gameRunning = false;
    public PlayServices playServices;
    private boolean isGameHost = true;

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
    Lazy<GameOverScreen> gameOverScreen;

    @Inject
    public Main() {
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

    public void setState(GameState state) {
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
            case GAME_OVER:
                setScreen(gameOverScreen.get());
                break;
        }
    }

    public Prefs getPrefs() {
        return prefs;
    }



    public boolean isGameRunning() {
        return gameRunning;
    }

    public boolean getIsGameHost() {
        return isGameHost;
    }

    public void setIsGameHost(boolean isGameHost) {
        this.isGameHost = isGameHost;
    }


    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
}
