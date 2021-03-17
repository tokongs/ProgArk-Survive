package com.mygdx.progarksurvive.networking.events;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class HostUpdateEvent {
    public Map<String, Vector2> playerPositions;
    public HostUpdateEvent(){
        playerPositions = new HashMap<>();
    }
}
