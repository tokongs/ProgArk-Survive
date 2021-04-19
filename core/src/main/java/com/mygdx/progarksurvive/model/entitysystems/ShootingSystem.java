package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import org.w3c.dom.Text;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShootingSystem extends EntitySystem {

    private final World world;
    private final Engine engine;
    private final Texture texture;
    private float deltaTime;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<PositionComponent> posm = ComponentMapper.getFor(PositionComponent.class);

    @Inject
    public ShootingSystem(Engine engine, World world, AssetManager assetManager){
        this.world = world;
        this.engine = engine;
        texture = assetManager.get("images/player.png", Texture.class);
    }
    
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime){
        if(this.deltaTime > 1.0f) {
            if(this.entities != null) {
                for (Entity entity : this.entities) {
                    PlayerComponent player = pm.get(entity);
                    PositionComponent position = posm.get(entity);
                    createProjectile(position.position, entity);
                }
            }
            this.deltaTime = 0.0f;
        }
        this.deltaTime += deltaTime;
    }

    private void createProjectile(Vector2 position, Entity shooter){
        Entity entity = new Entity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(1f / 2, 1f / 2, new Vector2(1f / 2, 1f / 2), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.setUserData(entity);
        body.createFixture(fixtureDef);
        body.setLinearVelocity(20, 0);
        shape.dispose();

        entity.add(new PositionComponent(position));
        entity.add(new CollisionComponent());
        entity.add(new PhysicsBodyComponent(body));
        entity.add(new ProjectileComponent(10, shooter));
        entity.add(new ImageComponent(texture, new Vector2(1, 1)));

        engine.addEntity(entity);
    }
    
}
