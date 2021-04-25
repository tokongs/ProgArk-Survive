package com.mygdx.progarksurvive.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public Vector2 velocity;

    public VelocityComponent(float x, float y){
        this.velocity = new Vector2(x, y);
    }

    public VelocityComponent(Vector2 velocity){
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
