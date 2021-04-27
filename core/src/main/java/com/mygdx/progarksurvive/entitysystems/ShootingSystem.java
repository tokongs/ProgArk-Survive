package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.entities.Projectile;
import com.mygdx.progarksurvive.entitycomponents.DamageComponent;
import com.mygdx.progarksurvive.entitycomponents.HealthComponent;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.entitycomponents.PlayerComponent;
import com.mygdx.progarksurvive.entitycomponents.TargetingComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShootingSystem extends IntervalIteratingSystem {

    private final World world;
    private final Engine engine;
    private  final AssetManager assetManager;

    @Inject
    public ShootingSystem(Engine engine, World world, AssetManager assetManager) {
        super(Family.all(PlayerComponent.class, TargetingComponent.class, PhysicsBodyComponent.class, DamageComponent.class).get(), 0.2f);
        this.world = world;
        this.engine = engine;
        this.assetManager = assetManager;
    }

    @Override
    protected void processEntity(Entity entity) {
        TargetingComponent targeting = entity.getComponent(TargetingComponent.class);
        PhysicsBodyComponent body = entity.getComponent(PhysicsBodyComponent.class);
        DamageComponent dc = entity.getComponent(DamageComponent.class);
        // If player is moving, it should not be shooting
        if (!body.body.getLinearVelocity().isZero(10)) targeting.target = null;


        if (targeting.target == null) return;

        Vector2 position = new Vector2(entity.getComponent(PhysicsBodyComponent.class).body.getPosition());
        Vector2 direction = new Vector2(targeting.target.getComponent(PhysicsBodyComponent.class).body.getPosition()).sub(position).limit(1);

        // If target is dead, remove target pointer
        if (targeting.target.getComponent(HealthComponent.class).health < 0) {
            targeting.target = null;
            return;
        }

        Projectile projectile = new Projectile(position, direction, world, entity, assetManager, dc.damage, dc.speed);
        engine.addEntity(projectile.entity);

    }
}
