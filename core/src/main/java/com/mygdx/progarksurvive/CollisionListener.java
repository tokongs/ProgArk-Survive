package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.entitycomponents.CollisionComponent;


public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        System.out.printf("Collision detected: %s, %s \n", fa.getBody().getType(), fb.getBody().getType());

        if(!(fa.getBody().getUserData() instanceof Entity || fb.getBody().getUserData() instanceof Entity)) return;

        Entity e1 = (Entity) fa.getBody().getUserData();
        Entity e2 = (Entity) fa.getBody().getUserData();

        CollisionComponent c1 = e1.getComponent(CollisionComponent.class);
        CollisionComponent c2 = e2.getComponent(CollisionComponent.class);

        if(c1 == null || c2 == null) return;

        c1.collisionEntity = e2;
        c2.collisionEntity = e1;

    }

    @Override
    public void endContact(Contact contact) {
        System.out.printf("End: %s, %s \n", contact.getFixtureA().getBody().getType(), contact.getFixtureB().getBody().getType());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
