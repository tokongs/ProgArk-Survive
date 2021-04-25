package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.progarksurvive.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayerDamageSystem extends IteratingSystem {

    @Inject
    public PlayerDamageSystem(){
        super(Family.all(PlayerComponent.class, HealthComponent.class, CollisionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity collisionEntity = entity.getComponent(CollisionComponent.class).collisionEntity;
        if(collisionEntity == null) return;

        EnemyComponent enemyComponent = collisionEntity.getComponent(EnemyComponent.class);
        if(enemyComponent == null) return;

        entity.getComponent(HealthComponent.class).health -= 100;
    }
}
