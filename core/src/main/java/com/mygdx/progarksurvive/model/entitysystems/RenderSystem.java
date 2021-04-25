package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.TransformComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RenderSystem extends IteratingSystem {
    private final SpriteBatch batch;

    @Inject
    public RenderSystem(SpriteBatch batch){
        super(Family.all(TransformComponent.class, ImageComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = entity.getComponent(TransformComponent.class);
        ImageComponent image = entity.getComponent(ImageComponent.class);
        image.sprite.setPosition(transform.position.x, transform.position.y);
        image.sprite.setRotation(transform.rotation);
        image.sprite.draw(batch);
    }
}
