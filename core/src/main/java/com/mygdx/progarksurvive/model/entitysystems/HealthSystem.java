package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.model.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HealthSystem extends IteratingSystem {

    private final World world;
    private final Engine engine;

    ComponentMapper<PhysicsBodyComponent> pbm;
    ComponentMapper<HealthComponent> hm;

    @Inject
    public HealthSystem(Engine engine, World world) {
        super(Family.all(PhysicsBodyComponent.class, HealthComponent.class).get());
        this.world = world;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HealthComponent hm = entity.getComponent(HealthComponent.class);

        // If dead, remove entity from the world and ashley engine
        // Should probably be enemy specific here, then handle players another way
        if(hm.health <= 0){
            PhysicsBodyComponent phc = entity.getComponent(PhysicsBodyComponent.class);
            EnemyComponent enemyComponent = entity.getComponent(EnemyComponent.class);
            PlayerComponent playerComponent = entity.getComponent(PlayerComponent.class);
            if(enemyComponent != null) {
                engine.removeEntity(entity);
                world.destroyBody(phc.body);

            } else if(playerComponent != null) {
                // Game over
            }

        }
    }
}
