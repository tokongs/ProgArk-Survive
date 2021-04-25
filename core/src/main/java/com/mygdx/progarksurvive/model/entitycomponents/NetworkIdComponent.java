package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;

public class NetworkIdComponent implements Component {

    private int id;

    public NetworkIdComponent(){

    }

    public NetworkIdComponent(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
