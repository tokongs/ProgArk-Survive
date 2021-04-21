package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PlayerComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;

import javax.inject.Inject;

public class PositionSystem extends IteratingSystem {


    ComponentMapper<PhysicsBodyComponent> pbm;
    ComponentMapper<PositionComponent> pm;

    @Inject
    public PositionSystem(){
        super(Family.all(PhysicsBodyComponent.class, PositionComponent.class, ImageComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsBodyComponent pbc = entity.getComponent(PhysicsBodyComponent.class);
        PositionComponent pc = entity.getComponent(PositionComponent.class);
        ImageComponent ic  = entity.getComponent(ImageComponent.class);
        pc.position = new Vector2(pbc.body.getPosition()).mulAdd(ic.size, -0.5f);
    }
}
