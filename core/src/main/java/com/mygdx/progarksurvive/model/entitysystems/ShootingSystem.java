package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.Player;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import org.w3c.dom.Text;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShootingSystem extends IntervalIteratingSystem {

    private final World world;
    private final Engine engine;
    private final Texture texture;

    @Inject
    public ShootingSystem(Engine engine, World world, AssetManager assetManager){
        super(Family.all(PlayerComponent.class, TargetingComponent.class, PhysicsBodyComponent.class).get(), 1f);
        this.world = world;
        this.engine = engine;
        texture = assetManager.get("images/player.png", Texture.class);
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

        createProjectile(position, direction, entity);

    }

    private void createProjectile(Vector2 position, Vector2 direction, Entity shooter){
        Entity entity = new Entity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(position.mulAdd(direction, 3));

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(1f / 2, 1f / 2, new Vector2(1f / 2, 1f / 2), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        body.setUserData(entity);
        body.createFixture(fixtureDef);
        body.setLinearVelocity(direction.scl(70));
        shape.dispose();

        entity.add(new PositionComponent(position));
        entity.add(new CollisionComponent());
        entity.add(new PhysicsBodyComponent(body));
        entity.add(new ProjectileComponent(10, shooter));
        entity.add(new ImageComponent(texture, new Vector2(1, 1)));

        engine.addEntity(entity);
    }
    
}
