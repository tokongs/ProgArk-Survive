package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;

public class NetworkIdComponent implements Component {
    private static long nextId;

    private final long id;

    public NetworkIdComponent(){
        id = nextId;
        nextId++;
    }

    public long getId(){
        return id;
    }
}
