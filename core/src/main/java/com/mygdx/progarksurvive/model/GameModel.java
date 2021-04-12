package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameModel {

    Entity player = new Entity();

    @Inject
    public GameModel(Engine ashley){
        ashley.addEntity(player);
        player.add(new PositionComponent(0, 0));
    }
}
