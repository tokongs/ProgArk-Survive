package com.mygdx.progarksurvive.controller;

import com.badlogic.ashley.core.Entity;
import com.mygdx.progarksurvive.model.EntityModel;
import com.mygdx.progarksurvive.model.entitycomponents.DamageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.HealthComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitysystems.HealthSystem;
import com.mygdx.progarksurvive.model.entitysystems.MovementSystem;

public class GameController {
    public GameController(EntityModel entityModel) {
        Entity entity = new Entity();
        entity.add(new PositionComponent());
        entity.add(new HealthComponent());
        entity.add(new DamageComponent());

        entityModel.addEntity(entity);

        entityModel.addSystem(new HealthSystem());
        entityModel.addSystem(new MovementSystem());
    }

    public void render(EntityModel entityModel, Float deltaTime){
        entityModel.engine.update(deltaTime);
    }
}
