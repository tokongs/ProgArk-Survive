package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import org.w3c.dom.Text;

public class Player {

    public Entity entity = new Entity();

    public Player(Vector2 position, Vector2 size, Texture texture, World world){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(position);

        Body body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(size.x);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        circle.dispose();
        entity.add(new PlayerComponent());
        entity.add(new PositionComponent(position));
        entity.add(new ImageComponent(texture, size));
        entity.add(new HealthComponent(100));
        entity.add(new CollisionComponent());
        entity.add(new PhysicsBodyComponent(body));
    }
}
