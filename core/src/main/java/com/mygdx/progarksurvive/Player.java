package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;

public class Player {

    public Entity entity = new Entity();

    public Player(Vector2 position, Vector2 size, AnimationComponent animationComponent, World world){
        entity.add(new PlayerComponent());
        entity.add(new TransformComponent(position, 0));
        entity.add(new ImageComponent(animationComponent.textures.get(animationComponent.defaultTexture), size));
        entity.add(new HealthComponent(10000));
        entity.add(new CollisionComponent());
        entity.add(new TargetingComponent());
        entity.add(new ScoreComponent());
        entity.add(new PhysicsBodyComponent(createBody(position, size, world)));
        entity.add(animationComponent);
    }

    private Body createBody(Vector2 position, Vector2 size, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;

        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x / 2, size.y / 2);

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
