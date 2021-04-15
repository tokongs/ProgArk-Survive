package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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

    public void update(float deltaTime){
        SpriteBatch batch = new SpriteBatch();
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
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
