package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

@Singleton
public class NetworkingScreen implements Screen {

    private final NetworkedGameClient client;
    private final NetworkedGameHost host;
    private String gameSessionName = "";
    private final Main game;
    private final Stage stage = new Stage(new StretchViewport(800.0f, 800.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth()));


    private boolean isHost = false;

    private final Label statusLabel;
    private final TextField gameSessionNameField;
    private final TextButton startGameSessionButton;
    private final TextButton joinGameSessionButton;

    @Inject
    public NetworkingScreen(Main game, NetworkedGameClient client, NetworkedGameHost host, AssetManager assetManager) {
        this.client = client;
        this.host = host;
        this.game = game;
        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        statusLabel = new Label("Not connected", skin);
        Label gameSessionNameLabel = new Label("Session name: ", skin);
        gameSessionNameField = new TextField("", skin);
        startGameSessionButton = new TextButton("Start game session", skin);
        joinGameSessionButton = new TextButton("Join game session", skin);
        TextButton startGameButton = new TextButton("Start game", skin);

        table.add(gameSessionNameLabel).size(100, 80);
        table.add(gameSessionNameField).size(300, 80);
        table.row();
        table.add(startGameSessionButton).size(200, 100).center();
        table.add(joinGameSessionButton).size(200, 100).center();
        table.row();
        table.add(statusLabel).size(200, 80).colspan(2);
        table.row();
        table.add(startGameButton).size(200, 100).colspan(2);

        startGameSessionButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onStartGameSessionClick();
                return false;
            }
        });

        joinGameSessionButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onJoinGameSessionClick();
                return false;
            }
        });

        startGameButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setState(GameState.GAME);
                return false;
            }
        });

    }

    private void onStartGameSessionClick(){
        startGameSessionButton.setDisabled(true);
        joinGameSessionButton.setDisabled(true);
        try {
            host.startGameSession(gameSessionNameField.getText());
            statusLabel.setText("Waiting for peers...");
            isHost = true;
            game.setIsGameHost(true);
        } catch (IOException e) {
            startGameSessionButton.setDisabled(false);
            joinGameSessionButton.setDisabled(false);
            e.printStackTrace();
        }
    }

    private void onJoinGameSessionClick(){
        startGameSessionButton.setDisabled(true);
        joinGameSessionButton.setDisabled(true);
        Map<String, InetAddress> discoveredGameSession = client.findGameSessions();
        try {
            client.joinGameSession(discoveredGameSession.get(gameSessionNameField.getText()).getHostAddress());
            statusLabel.setText("Waiting for host to start the game...");
        } catch (Exception e) {
            startGameSessionButton.setDisabled(false);
            joinGameSessionButton.setDisabled(false);
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();

        if(isHost && host.numberOfConnections() > 0){
            statusLabel.setText("Number of connected peers: " + host.numberOfConnections());
        }
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
