package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.EntityType;
import com.mygdx.progarksurvive.model.entitycomponents.*;

public class Projectile {
    public Entity entity = new Entity();
    private final float bulletVelocity = 1000f;

    public Projectile(Vector2 position, Vector2 direction, World world, Entity shooter){
        Entity entity = new Entity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;

        bodyDef.position.set(position.mulAdd(direction, 5));


        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(5f / 2, 5f / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;

        body.setUserData(entity);
        body.createFixture(fixtureDef);
        body.setLinearVelocity(direction.scl(bulletVelocity));
        body.setTransform(body.getPosition(), direction.angleDeg(new Vector2(1, 0)));
        shape.dispose();

        entity.add(new NetworkIdComponent());
        entity.add(new TypeComponent(EntityType.PROJECTILE));
        entity.add(new TransformComponent(position,0));
        entity.add(new CollisionComponent());
        entity.add(new PhysicsBodyComponent(body));
        entity.add(new ProjectileComponent(10, shooter));
    }

    public static ImageComponent createImageComponent(AssetManager assetManager){
        Texture texture = assetManager.get("images/BulletTexture.png");
        return new ImageComponent(texture, new Vector2(5, 3));
    }
}
