package com.mygdx.progarksurvive.networking.events;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostUpdateEvent {
    public List<Vector2> playerPositions = new ArrayList<>();
    public List<Vector2> enemyPositions = new ArrayList<>();
    public List<Vector2> projectilePositions = new ArrayList<>();

    public HostUpdateEvent(){
    }

    public HostUpdateEvent(List<Vector2> playerPositions, List<Vector2> enemyPositions, List<Vector2> projectilePositions){
        this.playerPositions = playerPositions;
        this.enemyPositions = enemyPositions;
        this.projectilePositions = projectilePositions;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof HostUpdateEvent)) return false;

        HostUpdateEvent e = (HostUpdateEvent) o;

        return playerPositions.equals(e.playerPositions);
    }
}
