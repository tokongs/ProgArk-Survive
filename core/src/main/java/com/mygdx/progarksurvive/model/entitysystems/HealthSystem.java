package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.progarksurvive.model.entitycomponents.DamageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.HealthComponent;

public final class HealthSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<DamageComponent> dm = ComponentMapper.getFor(DamageComponent.class);

    public HealthSystem(){}

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
    }

    public void update(float deltaTime){
        if(entities != null){
            for(Entity entity: entities){
                HealthComponent health = hm.get(entity);
                DamageComponent damage = dm.get(entity);

                health.damage(damage.getDamage());

                if(health.getHealth() < 0f){
                    // Entity Dead, should be removed
                    getEngine().removeEntity(entity);
                }
            }
        }
    }
}
