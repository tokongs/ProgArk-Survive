package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.progarksurvive.model.EntityModel;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public MovementSystem(){}

    public void addedToEngine(EntityModel entityModel){
        entities = entityModel.engine.getEntitiesFor(Family.one(PositionComponent.class).get());
    }

    public void update(){
        for(Entity entity: entities){
            PositionComponent position = pm.get(entity);
            position.changePos(.1f,.1f);
        }
    }
}
