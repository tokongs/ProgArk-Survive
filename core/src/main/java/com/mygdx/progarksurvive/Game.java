package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.networking.*;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.screen.GameScreen;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class Game extends com.badlogic.gdx.Game {
    private final NetworkedGameClient client;
    private final NetworkedGameHost host;

    private final GameScreen gameScreen;

    @Inject
    public Game(NetworkedGameHost host, NetworkedGameClient client, GameScreen gameScreen){
        super();
        this.host = host;
        this.client = client;
        this.gameScreen = gameScreen;
    }

    @Override
    public void create() {
        this.setScreen(gameScreen);
        try {
            host.startGameSession("My Game Session");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, InetAddress> hosts = client.findGameSessions();
        try {
            client.joinGameSession(hosts.get("My Game Session").getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        super.render();
        //client.update(new ClientUpdateEvent(new Vector2(1, 2)));
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {

    }
}
