package com.mygdx.progarksurvive.entitycomponents;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
/*
 *  Stores collision data such as entity that this entity has collided with
 */
public class CollisionComponent implements Component {
    public Entity collisionEntity;

}
