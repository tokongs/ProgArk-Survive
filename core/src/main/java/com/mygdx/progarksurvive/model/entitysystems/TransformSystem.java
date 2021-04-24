package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.model.entitycomponents.TransformComponent;

import javax.inject.Inject;

public class TransformSystem extends IteratingSystem {
    @Inject
    public TransformSystem(){
        super(Family.all(PhysicsBodyComponent.class, TransformComponent.class, ImageComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsBodyComponent pbc = entity.getComponent(PhysicsBodyComponent.class);
        TransformComponent pc = entity.getComponent(TransformComponent.class);
        ImageComponent ic  = entity.getComponent(ImageComponent.class);
        pc.position = new Vector2(pbc.body.getPosition()).mulAdd(ic.size, -0.5f);
        pc.rotation = pbc.body.getAngle();
    }
}
