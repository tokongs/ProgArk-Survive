package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.entitycomponents.TransformComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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
