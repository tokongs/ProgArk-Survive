package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {


    public Vector2 position;
    public float rotation;

    public TransformComponent(Vector2 position, float rotation){
        this.position = position;
        this.rotation = rotation;
    }

    public Vector2 getPosition() {
        return position;
    }
}
