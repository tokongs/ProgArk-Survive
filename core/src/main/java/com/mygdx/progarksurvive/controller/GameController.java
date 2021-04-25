package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameController{

    private final GameModel model;
    private final Main game;
    private final NetworkedGameClient client;

    @Inject
    public GameController(GameModel model, Main game, NetworkedGameClient client) {
        this.game = game;
        this.model = model;
        this.client = client;
    }

    public void movePlayer(Vector2 direction) {

        if (game.getIsGameHost()) {
            model.player.setVelocity(direction);
        } else {
            client.update(new ClientUpdateEvent(direction));
        }
    }

    public void update(float delta) {
        model.update(delta);
    }
}
