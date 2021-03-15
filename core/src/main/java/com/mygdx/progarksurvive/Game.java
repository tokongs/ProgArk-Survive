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
    AssetManager manager = new AssetManager();

    private Stage stage;
    private Table table;

    TextButton connectButton;
    @Override
    public void create() {
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
    }
}
