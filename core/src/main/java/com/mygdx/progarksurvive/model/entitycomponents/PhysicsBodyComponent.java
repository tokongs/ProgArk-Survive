package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsBodyComponent implements Component {
    public Body body;

    public PhysicsBodyComponent(Body body){
        this.body = body;
    }
}
