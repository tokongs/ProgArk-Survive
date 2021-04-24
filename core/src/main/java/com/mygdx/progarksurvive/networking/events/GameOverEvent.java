package com.mygdx.progarksurvive.networking.events;

import java.util.HashMap;
import java.util.Map;

public class GameOverEvent extends HostNetworkEvent{
    public int round = 0;
    public Map<Integer, Integer> score = new HashMap<>();
    public Map<Integer, Integer> health = new HashMap<>();

    public GameOverEvent(){ }

    public GameOverEvent(int round, Map<Integer, Integer> score,  Map<Integer, Integer> health){
        this.round = round;
        this.health = health;
        this.score = score;
    }
}
