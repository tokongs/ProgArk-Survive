package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {


    public Vector2 position;

    public PositionComponent(float x, float y){
        position = new Vector2(x, y);
    }

    public PositionComponent(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }
}
