package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
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
            PhysicsBodyComponent physicsBodyComponent = model.player.entity.getComponent(PhysicsBodyComponent.class);
            physicsBodyComponent.body.setLinearVelocity(direction.scl(100));
            float newAngle = direction.angleDeg(new Vector2(1,0));
            physicsBodyComponent.body.setTransform(physicsBodyComponent.body.getPosition(), newAngle);
        } else {
            client.update(new ClientUpdateEvent(direction));
        }
    }

    public void update(float delta) {
        model.update(delta);
    }
}
