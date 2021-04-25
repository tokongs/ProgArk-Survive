package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.progarksurvive.entitycomponents.AnimationComponent;
import com.mygdx.progarksurvive.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;

import javax.inject.Inject;

public class AnimationSystem extends IteratingSystem {


    @Inject
    public AnimationSystem(){
        super(Family.all(ImageComponent.class, AnimationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsBodyComponent physicsBodyComponent = entity.getComponent(PhysicsBodyComponent.class);
        ImageComponent imageComponent = entity.getComponent(ImageComponent.class);
        AnimationComponent animationcomponent = entity.getComponent(AnimationComponent.class);
        if(physicsBodyComponent == null || !physicsBodyComponent.body.getLinearVelocity().isZero(10)){
            animationcomponent.timePast += deltaTime;
            if(animationcomponent.timePast < animationcomponent.timePerTexture){
                return;
            }

            animationcomponent.timePast = 0;
            animationcomponent.textureIndex = (animationcomponent.textureIndex + 1)%animationcomponent.textures.size();
            imageComponent.setTexture(animationcomponent.textures.get(animationcomponent.textureIndex));
            return;
        }
        imageComponent.setTexture(animationcomponent.textures.get(animationcomponent.defaultTexture));
    }
}
