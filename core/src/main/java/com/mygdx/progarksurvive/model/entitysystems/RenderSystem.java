package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitycomponents.VelocityComponent;

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<ImageComponent> im = ComponentMapper.getFor(ImageComponent.class);

    public RenderSystem(){}

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, ImageComponent.class).get());
    }

    public void addAllTextures(){
        for(Entity entity: this.entities){
            ImageComponent image = im.get(entity);
            image.texture = new Texture(Gdx.files.internal(image.path));
        }
    }

    public void update(float deltaTime){
        SpriteBatch batch = new SpriteBatch();
        batch.begin();
        if(this.entities != null){
            for(Entity entity: this.entities){
                PositionComponent position = pm.get(entity);
                ImageComponent image = im.get(entity);
                batch.draw(image.texture, position.position.x, position.position.y);
            }
        }
        batch.end();
        batch.dispose();
    }
}
