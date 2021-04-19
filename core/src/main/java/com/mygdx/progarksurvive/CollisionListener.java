package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.model.entitycomponents.CollisionComponent;


public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
       // System.out.printf("Collision detected: %s, %s \n", fixtureA.getBody().getType(), fixtureB.getBody().getType());

        if(!(fixtureA.getBody().getUserData() instanceof Entity || fixtureB.getBody().getUserData() instanceof Entity)) return;

        Entity entityA = (Entity) fixtureA.getBody().getUserData();
        Entity entityB = (Entity) fixtureB.getBody().getUserData();

        CollisionComponent collisionA = entityA.getComponent(CollisionComponent.class);
        CollisionComponent collisionB = entityB.getComponent(CollisionComponent.class);

        if(collisionA == null || collisionB == null) return;

        collisionA.collisionEntity = entityB;
        collisionB.collisionEntity = entityA;
    }

    @Override
    public void endContact(Contact contact) {
        //System.out.printf("End: %s, %s \n", contact.getFixtureA().getBody().getType(), contact.getFixtureB().getBody().getType());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
