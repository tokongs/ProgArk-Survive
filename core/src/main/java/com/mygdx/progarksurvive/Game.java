package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.controller.MainMenuController;
import com.mygdx.progarksurvive.model.MainMenuModel;
import com.mygdx.progarksurvive.networking.*;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.screen.MainMenuScreen;
import com.sun.tools.javac.Main;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class Game extends com.badlogic.gdx.Game {
    //Networking
    private final NetworkedGameClient client;
    private final NetworkedGameHost host;

    //Game MVCs
    private final MainMenuScreen mainMenuScreen;

    @Inject
    public Game(NetworkedGameHost host, NetworkedGameClient client, MainMenuScreen mainMenuScreen){
        super();
        this.host = host;
        this.client = client;
        this.mainMenuScreen = mainMenuScreen;
    }

    @Override
    public void create() {
        setScreen(mainMenuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}
