package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PlayerComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InputSystem extends IteratingSystem {

    ComponentMapper<PhysicsBodyComponent> pbm;
    ComponentMapper<PlayerComponent> pm;

    @Inject
    public InputSystem(){
        super(Family.all(PlayerComponent.class).get());
        pbm = ComponentMapper.getFor(PhysicsBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
