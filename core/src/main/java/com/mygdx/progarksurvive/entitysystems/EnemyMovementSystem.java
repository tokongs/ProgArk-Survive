package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.entitycomponents.EnemyComponent;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.entitycomponents.TargetingComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EnemyMovementSystem extends IteratingSystem {

    @Inject
    public EnemyMovementSystem(){
        super(Family.all(EnemyComponent.class, PhysicsBodyComponent.class, TargetingComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TargetingComponent targeting = entity.getComponent(TargetingComponent.class);
        PhysicsBodyComponent body = entity.getComponent(PhysicsBodyComponent.class);
        if(targeting.target == null) {
            body.body.setLinearVelocity(0, 0);
        } else {
            Vector2 position = new Vector2(entity.getComponent(PhysicsBodyComponent.class).body.getPosition());
            Vector2 direction = new Vector2(targeting.target.getComponent(PhysicsBodyComponent.class).body.getPosition()).sub(position).limit(1);
            body.body.setLinearVelocity(direction.scl(50));
        }
    }
}
