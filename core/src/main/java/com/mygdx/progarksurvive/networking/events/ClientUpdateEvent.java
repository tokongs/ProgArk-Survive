package com.mygdx.progarksurvive.networking.events;


import com.badlogic.gdx.math.Vector2;

public class ClientUpdateEvent {
    public Vector2 direction;

    public ClientUpdateEvent() {

    }

    public ClientUpdateEvent(Vector2 direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClientUpdateEvent)) return false;
        return direction == ((ClientUpdateEvent) o).direction;
    }
}
