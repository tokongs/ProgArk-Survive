package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;


public class Game extends ApplicationAdapter {

    public final int TCP_PORT = 54555;
    public final int UDP_PORT = 54777;

    Server server = new Server();
    Client client = new Client();

    AssetManager manager = new AssetManager();

    private Stage stage;
    private Table table;

    TextButton connectButton;
    @Override
    public void create() {
        Kryo kryo = server.getKryo();
        kryo.register(String.class);
        kryo = client.getKryo();
        kryo.register(String.class);
        server.start();

        client.start();

        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof String) {
                    System.out.println(object);
                    connection.sendTCP("Thanks");
                }
            }
        });

        try {
            server.bind(TCP_PORT, UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileHandle f = Gdx.files.internal("uiskin.json");
        Skin skin = new Skin(f);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        connectButton = new TextButton("Connect to local server", skin);
        connectButton.setTransform(true);
        connectButton.setScale(4f);
        connectButton.setOriginY(0);
        connectButton.setOriginY(100);

        connectButton.addListener(event -> {
            if(client.isConnected()){
                return false;
            }
            InetAddress addr = client.discoverHost(UDP_PORT, 5000);
            System.out.println(addr);
            try {
                client.connect(10000, addr, TCP_PORT, UDP_PORT);
                client.sendTCP("Hei");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        });

        table.add(connectButton);
        stage.addActor(table);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        manager.dispose();
        if (server != null) {
            try {
                server.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
