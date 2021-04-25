package com.mygdx.progarksurvive.entitycomponents;

import com.badlogic.ashley.core.Component;

public class EntityIdComponent implements Component {
    private static long nextId;

    private final long id;

    public EntityIdComponent(){
        id = nextId;
        nextId++;
    }

    public long getId(){
        return id;
    }
}
