package com.mygdx.progarksurvive.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.EntityType;
import com.mygdx.progarksurvive.entitycomponents.*;

public class Wall {
    public Entity entity;

    public Wall(Vector2 position, Vector2 size, Color color, World world){
        entity = new Entity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x / 2, size.y / 2);
        body.setUserData(entity);
        body.createFixture(shape, 0.0f);
        shape.dispose();

        entity.add(new CollisionComponent());
        entity.add(new TypeComponent(EntityType.WALL));
        entity.add(new ImageComponent(color, size));
        entity.add(new PhysicsBodyComponent(body));
        entity.add(new TransformComponent(position, 0));
    }
}
