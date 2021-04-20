package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.model.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PlayerTargetingSystem extends IteratingSystem {

    private final World world;
    private final Engine ashley;

    @Inject
    public PlayerTargetingSystem(World world, Engine ashley) {
        super(Family.all(PlayerComponent.class, PositionComponent.class, TargetingComponent.class).get());
        this.world = world;
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
    }
}