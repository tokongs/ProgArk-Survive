package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayerTargetingSystem extends IteratingSystem {

    private final Engine ashley;

    @Inject
    public PlayerTargetingSystem(Engine ashley) {
        super(Family.all(PlayerComponent.class, TransformComponent.class, TargetingComponent.class).get());
        this.ashley = ashley;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsBodyComponent body = entity.getComponent(PhysicsBodyComponent.class);
        Vector2 position = new Vector2(body.body.getPosition());
        ImmutableArray<Entity> enemies = ashley.getEntitiesFor(Family.all(EnemyComponent.class, PhysicsBodyComponent.class).get());
        float minDistance = Float.MAX_VALUE;
        for (Entity enemy: enemies) {
            Vector2 enemyPosition = new Vector2(enemy.getComponent(PhysicsBodyComponent.class).body.getPosition());
            float distance = position.dst(enemyPosition);
            if(distance < minDistance){
                entity.getComponent(TargetingComponent.class).target = enemy;
                minDistance = distance;
            }
        }
        Entity nearestEnemy = entity.getComponent(TargetingComponent.class).target;
        if(!body.body.getLinearVelocity().isZero() || nearestEnemy == null){
            return;
        }
        Vector2 direction = nearestEnemy.getComponent(PhysicsBodyComponent.class).body.getPosition().sub(body.body.getPosition());
        body.body.setTransform(body.body.getPosition(), direction.angleDeg(new Vector2(1,0)));
    }
}