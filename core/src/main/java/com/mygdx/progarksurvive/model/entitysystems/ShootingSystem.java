package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.Projectile;
import com.mygdx.progarksurvive.model.EntityType;
import com.mygdx.progarksurvive.model.entitycomponents.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShootingSystem extends IntervalIteratingSystem {

    private final World world;
    private final Engine engine;

    private final float bulletVelocity = 1000f;

    @Inject
    public ShootingSystem(Engine engine, World world, AssetManager assetManager){
        super(Family.all(PlayerComponent.class, TargetingComponent.class, PhysicsBodyComponent.class).get(), 0.2f);
        this.world = world;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity) {
        TargetingComponent targeting = entity.getComponent(TargetingComponent.class);
        PhysicsBodyComponent body = entity.getComponent(PhysicsBodyComponent.class);

        // If player is moving, it should not be shooting
        if(body.body.getLinearVelocity().x != 0 | body.body.getLinearVelocity().y != 0){targeting.target = null;}

        if(targeting.target == null) return;

        Vector2 position = new Vector2(entity.getComponent(PhysicsBodyComponent.class).body.getPosition());
        Vector2 direction = new Vector2(targeting.target.getComponent(PhysicsBodyComponent.class).body.getPosition()).sub(position).limit(1);

        // If target is dead, remove target pointer
        if(targeting.target.getComponent(HealthComponent.class).health < 0){targeting.target = null; return;}

        Projectile projectile = new Projectile(position, direction, world, entity);
        engine.addEntity(projectile.entity);

    }
}
