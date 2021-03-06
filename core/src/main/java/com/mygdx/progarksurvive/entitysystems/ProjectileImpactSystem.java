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
public class ProjectileImpactSystem extends IteratingSystem {

    private final World world;
    private final Engine engine;

    @Inject
    public ProjectileImpactSystem(Engine engine, World world) {
        super(Family.all(CollisionComponent.class, ProjectileComponent.class, PhysicsBodyComponent.class).get());
        this.world = world;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collisionComponent = entity.getComponent(CollisionComponent.class);
        ProjectileComponent projectileComponent = entity.getComponent(ProjectileComponent.class);

        if (collisionComponent.collisionEntity == null || collisionComponent.collisionEntity == projectileComponent.shooter)
            return;

        HealthComponent hitEntityHealth = collisionComponent.collisionEntity.getComponent(HealthComponent.class);
        if (hitEntityHealth != null) {
            hitEntityHealth.health -= projectileComponent.damage;
            projectileComponent.shooter.getComponent(ScoreComponent.class).score += 5;
        }

        PhysicsBodyComponent phc = entity.getComponent(PhysicsBodyComponent.class);
        engine.removeEntity(entity);
        world.destroyBody(phc.body);
    }
}
