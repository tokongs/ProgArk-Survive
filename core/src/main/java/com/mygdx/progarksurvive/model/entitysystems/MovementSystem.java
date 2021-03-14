package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public MovementSystem(){}

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    public void update(float deltaTime){
        if(this.entities != null){
            for(Entity entity: this.entities){
                PositionComponent position = pm.get(entity);
                position.changePos(.1f,.1f);
            }
        }
    }
}
