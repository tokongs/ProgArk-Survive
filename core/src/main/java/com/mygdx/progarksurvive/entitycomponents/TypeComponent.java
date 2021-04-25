package com.mygdx.progarksurvive.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.progarksurvive.EntityType;

public class TypeComponent implements Component {
    public EntityType type;

    public TypeComponent() {

    }

    public TypeComponent(EntityType type){
        this.type = type;
    }
}
