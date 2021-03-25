package com.mygdx.progarksurvive.networking.events;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class HostUpdateEvent {
    public Map<String, Vector2> playerPositions;
    public HostUpdateEvent(){
        playerPositions = new HashMap<>();
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof HostUpdateEvent)) return false;

        HostUpdateEvent e = (HostUpdateEvent) o;

        return playerPositions.equals(e.playerPositions);
    }
}
