package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.mygdx.progarksurvive.model.EntityType;

public class TypeComponent implements Component {
    public EntityType type;

    public TypeComponent() {

    }

    public TypeComponent(EntityType type){
        this.type = type;
    }
}
