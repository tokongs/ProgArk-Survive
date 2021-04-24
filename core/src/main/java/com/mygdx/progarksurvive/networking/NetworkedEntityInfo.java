package com.mygdx.progarksurvive.networking;

import com.badlogic.ashley.core.Component;

import java.util.List;
import java.util.Map;

public class NetworkedEntityInfo {
    public List<Component> components;

    public NetworkedEntityInfo(){
    }

    public NetworkedEntityInfo(List<Component> components){
        this.components = components;
    }
}
