package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitycomponents.VelocityComponent;
import com.mygdx.progarksurvive.model.entitysystems.MovementSystem;
import com.mygdx.progarksurvive.model.entitysystems.RenderSystem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameModel {

    public Entity player = new Entity();

    @Inject
    public GameModel(Engine ashley){
        ashley.addEntity(player);
        player.add(new PositionComponent(0, 0));
        player.add(new VelocityComponent(.5f, .5f));

        // Nullpointer reference to the filehandle.
        // Tried moving the texture creation to the image component, but Gdx is not instantiated here either.
        //player.add(new ImageComponent(new Texture(Gdx.files.internal("images/player.png"))));

        ashley.addSystem(new MovementSystem());

        // awaiting image component texture fix
        //ashley.addSystem(new RenderSystem());
    }
}
