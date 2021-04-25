package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.events.GameStartEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class NetworkingScreen implements Screen {

    private final NetworkedGameClient client;
    private final NetworkedGameHost host;
    private String gameSessionName = "";
    private final Main game;
    private final Stage stage = new Stage(new StretchViewport(800.0f, 800.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth()));

    private boolean isHost = true;

    private final Label statusLabel;
    private final TextField gameSessionNameField;
    private final TextButton startGameSessionButton;
    private final TextButton joinGameSessionButton;
    private final TextButton startGameButton;

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
        startGameButton = new TextButton("Start game", skin);

        table.add(gameSessionNameLabel).size(200, 80);
        table.add(gameSessionNameField).size(200, 80);
        table.row().padTop(20);
        table.add(startGameSessionButton).size(190, 100).padRight(20);
        table.add(joinGameSessionButton).size(190, 100);
        table.row().padTop(20);
        table.add(startGameButton).size(200, 100).colspan(2);
        table.row();
        table.add(statusLabel).size(200, 80).colspan(2).align(Align.center);


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
                onStartGameClick();
                return false;
            }
        });
    }

    private void onStartGameClick(){
        game.setState(GameState.GAME);

        if(host.isActive()){
            host.update(new GameStartEvent());
        }
    }

    private void onStartGameSessionClick(){
        if(host.isActive() || client.isActive()){
            return;
        }
        try {
            host.startGameSession(gameSessionNameField.getText());
            statusLabel.setText("Waiting for peers...");
            isHost = true;
            game.setIsGameHost(true);
            gameSessionNameField.setDisabled(true);
        } catch (IOException e) {
            statusLabel.setText("Unable to start game session!");
            e.printStackTrace();
        }
    }

    private void onJoinGameSessionClick(){
        if(host.isActive() || client.isActive()){
            return;
        }

        statusLabel.setText("Searching for host...");
        new Thread(() -> {
            client.leaveGameSession();
            startGameButton.setVisible(false);

            try {
                Map<String, InetAddress> discoveredGameSession = client.findGameSessions();

                client.joinGameSession(discoveredGameSession.get(gameSessionNameField.getText()).getHostAddress());
                isHost = false;
                game.setIsGameHost(false);
                statusLabel.setText("Waiting for host to start the game...");
                client.setEventHandler((Id, event) -> {
                    if(event instanceof GameStartEvent){
                        Gdx.app.postRunnable(() -> game.setState(GameState.GAME));
                    }
                });
                gameSessionNameField.setDisabled(true);
            } catch (Exception e) {
                statusLabel.setText("Unable to join game session!");
                isHost = true;
                game.setIsGameHost(true);
                startGameButton.setVisible(true);
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        statusLabel.setText("");
        startGameButton.setVisible(true);
        gameSessionNameField.setDisabled(false);
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
