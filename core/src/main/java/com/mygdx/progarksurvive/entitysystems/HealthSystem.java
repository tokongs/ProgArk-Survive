package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HealthSystem extends IteratingSystem {

    private final World world;
    private final Engine engine;

    @Inject
    public HealthSystem(Engine engine, World world) {
        super(Family.all(PhysicsBodyComponent.class, HealthComponent.class).get());
        this.world = world;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent hm = entity.getComponent(HealthComponent.class);
        if(hm.health <= 0){
            PhysicsBodyComponent phc = entity.getComponent(PhysicsBodyComponent.class);
            EnemyComponent enemyComponent = entity.getComponent(EnemyComponent.class);
            if(enemyComponent != null) {
                engine.removeEntity(entity);
                world.destroyBody(phc.body);
            }
        }
    }
}
