package com.mygdx.progarksurvive.networking.events;

import java.util.HashMap;
import java.util.Map;

public class GameOverEvent extends HostNetworkEvent{
    public int round = 0;

    public GameOverEvent(){ }

    public GameOverEvent(int round){
        this.round = round;
    }
}
