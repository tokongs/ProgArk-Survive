package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RenderSystem extends EntitySystem {
    private final SpriteBatch batch;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<ImageComponent> im = ComponentMapper.getFor(ImageComponent.class);

    @Inject
    public RenderSystem(SpriteBatch batch){
        this.batch = batch;
    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, ImageComponent.class).get());
    }

    public void update(float deltaTime){
        if(this.entities != null){
            for(Entity entity: this.entities){
                PositionComponent position = pm.get(entity);
                ImageComponent image = im.get(entity);
                batch.draw(image.texture, position.position.x, position.position.y, image.size.x, image.size.y);
            }
        }
    }
}
