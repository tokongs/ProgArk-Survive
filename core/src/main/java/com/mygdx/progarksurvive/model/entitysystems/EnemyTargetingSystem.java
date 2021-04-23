package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.model.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EnemyTargetingSystem extends IteratingSystem {
    private final Engine ashley;

    @Inject
    public EnemyTargetingSystem(Engine ashley) {
        super(Family.all(EnemyComponent.class, PositionComponent.class, TargetingComponent.class).get());
        this.ashley = ashley;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsBodyComponent body = entity.getComponent(PhysicsBodyComponent.class);
        Vector2 position = new Vector2(body.body.getPosition());
        ImmutableArray<Entity> players = ashley.getEntitiesFor(Family.all(PlayerComponent.class, PhysicsBodyComponent.class).get());
        float minDistance = Float.MAX_VALUE;
        for (Entity player: players) {
            Vector2 enemyPosition = new Vector2(player.getComponent(PhysicsBodyComponent.class).body.getPosition());
            float distance = position.dst(enemyPosition);
            if(distance < minDistance){
                entity.getComponent(TargetingComponent.class).target = player;
                minDistance = distance;
            }
        }
    }
}