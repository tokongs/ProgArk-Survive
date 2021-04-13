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
        // Entities
        ashley.addEntity(player);

        // Components
        player.add(new PositionComponent(0, 0));
        player.add(new VelocityComponent(5f, 5f));
        player.add(new ImageComponent("images/player.png"));

        // Systems
        ashley.addSystem(new MovementSystem());
        ashley.addSystem(new RenderSystem());
    }
}
