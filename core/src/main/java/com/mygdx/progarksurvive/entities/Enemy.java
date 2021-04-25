package com.mygdx.progarksurvive.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.EntityType;
import com.mygdx.progarksurvive.entitycomponents.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Enemy {
    public Entity entity = new Entity();

    public Enemy(Vector2 position, Vector2 size, AnimationComponent animationComponent, World world) {
        entity.add(new EntityIdComponent());
        entity.add(new TypeComponent(EntityType.ENEMY));
        entity.add(new EnemyComponent());
        entity.add(new TransformComponent(position, 0));
        entity.add(new ImageComponent(animationComponent.textures.get(animationComponent.defaultTexture), size));
        entity.add(new HealthComponent(20));
        entity.add(new CollisionComponent());
        entity.add(new TargetingComponent());
        entity.add(new PhysicsBodyComponent(createBody(position, size, world)));
        entity.add(animationComponent);
    }

    public static AnimationComponent createAnimationComponent(AssetManager assetManager) {
        List<String> textureFilenames = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            textureFilenames.add("images/Zombie1Texture" + i + ".png");
        }
        List<Texture> textures = textureFilenames.stream().map(filename -> assetManager.get(filename, Texture.class)).collect(Collectors.toList());
        return new AnimationComponent(0.05f, textures, 4);
    }

    private Body createBody(Vector2 position, Vector2 size, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x / 2, size.y / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.9f;

        body.setUserData(entity);
        body.createFixture(fixtureDef);

        shape.dispose();

        return body;
    }
}
