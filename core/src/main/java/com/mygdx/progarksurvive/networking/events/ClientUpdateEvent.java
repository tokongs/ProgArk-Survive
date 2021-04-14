package com.mygdx.progarksurvive.networking.events;


import com.badlogic.gdx.math.Vector2;

public class ClientUpdateEvent {
    public Vector2 playerPosition;
    public ClientUpdateEvent(){
        playerPosition = new Vector2(0, 0);
    }
    public ClientUpdateEvent(Vector2 playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof ClientUpdateEvent)) return false;
        return playerPosition == ((ClientUpdateEvent) o).playerPosition;
    }
}