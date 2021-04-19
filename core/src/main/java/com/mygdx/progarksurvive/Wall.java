package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.model.entitycomponents.CollisionComponent;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;

public class Wall {
    public Entity entity;

    public Wall(Vector2 position, Vector2 size, Color color, World world){
        entity = new Entity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x / 2, size.y / 2, new Vector2(size.x / 2, size.y / 2), 0);
        body.setUserData(entity);
        body.createFixture(shape, 0.0f);
        shape.dispose();

        entity.add(new CollisionComponent());
        entity.add(new ImageComponent(color, size));
        entity.add(new PhysicsBodyComponent(body));
        entity.add(new PositionComponent(position));
    }
}
