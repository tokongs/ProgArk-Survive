package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.progarksurvive.entitycomponents.CollisionComponent;


public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        updateContact(contact, true);
    }

    @Override
    public void endContact(Contact contact) {
        updateContact(contact, false);
    }

    /**
     * Updates the collisionComponent of the two fixtures in the contact.
     * @param contact A contact object describing the contact
     * @param begin Whether this is the beginning of a contact,
     */
    private void updateContact(Contact contact, boolean begin){
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(!(fixtureA.getBody().getUserData() instanceof Entity || fixtureB.getBody().getUserData() instanceof Entity)) return;

        Entity entityA = (Entity) fixtureA.getBody().getUserData();
        Entity entityB = (Entity) fixtureB.getBody().getUserData();

        CollisionComponent collisionA = entityA.getComponent(CollisionComponent.class);
        CollisionComponent collisionB = entityB.getComponent(CollisionComponent.class);

        if(begin) {
            if(collisionA == null || collisionB == null) return;
            collisionA.collisionEntity = entityB;
            collisionB.collisionEntity = entityA;
        } else {
            collisionA.collisionEntity = null;
            collisionB.collisionEntity = null;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
