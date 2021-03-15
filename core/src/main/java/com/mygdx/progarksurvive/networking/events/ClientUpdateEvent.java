package com.mygdx.progarksurvive.networking.events;


import com.badlogic.gdx.math.Vector2;

public class ClientUpdateEvent {
    public Vector2 playerPosition;

    public ClientUpdateEvent(Vector2 playerPosition) {
        this.playerPosition = playerPosition;
    }
}
