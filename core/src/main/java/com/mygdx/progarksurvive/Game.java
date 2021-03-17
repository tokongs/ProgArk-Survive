package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.*;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class Game extends ApplicationAdapter {
    private final NetworkedGameClient client;
    private final NetworkedGameHost host;


    @Inject
    public Game(NetworkedGameHost host, NetworkedGameClient client){
        super();
        this.host = host;
        this.client = client;

        host.setEventHandler(event -> {
            System.out.println(event.playerPosition);
        });
    }

    @Override
    public void create() {
        try {
            host.startGameSession("My Game Session");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, InetAddress> hosts = client.findGameSessions();
        try {
            client.joinGameSession(hosts.get("My Game Session"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        client.update(new ClientUpdateEvent(new Vector2(1, 2)));
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void dispose() {

    }
}
