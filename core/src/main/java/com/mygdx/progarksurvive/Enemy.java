package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;

public class Enemy {
    public Entity entity = new Entity();

    public Enemy(Vector2 position, Vector2 size, Texture texture, World world){
        entity.add(new EnemyComponent());
        entity.add(new PositionComponent(position));
        entity.add(new ImageComponent(texture, size));
        entity.add(new HealthComponent(20));
        entity.add(new CollisionComponent());
        entity.add(new PhysicsBodyComponent(createBody(position, size, world)));
    }

    private Body createBody(Vector2 position, Vector2 size, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x / 2, size.y / 2, new Vector2(size.x / 2, size.y / 2), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        body.setUserData(entity);
        body.createFixture(fixtureDef);

        shape.dispose();

        return body;
    }
}
