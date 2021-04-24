package com.mygdx.progarksurvive.networking.events;


public class ClientUpdateEvent {
    public float touchX, touchY;
    public boolean touchDown = false;

    public ClientUpdateEvent() {

    }

    public ClientUpdateEvent(float touchX, float touchY, boolean touchDown) {
        this.touchX = touchX;
        this.touchY = touchY;
        this.touchDown = touchDown;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClientUpdateEvent)) return false;
        return touchY == ((ClientUpdateEvent) o).touchY && touchX == ((ClientUpdateEvent) o).touchX;
    }
}
