package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

public class EntityModel {
    public Engine engine;

    public EntityModel(){
        this.engine = new Engine();
    }

    public void addSystem(EntitySystem system){
        this.engine.addSystem(system);
    }

    public void removeSystem(EntitySystem system){
        this.engine.removeSystem(system);
    }

    public void addEntity(Entity entity){
        this.engine.addEntity(entity);
    }

    public void removeEntity(Entity entity){
        this.engine.removeEntity(entity);
    }

    public void removeAllEntities(){
        this.engine.removeAllEntities();
    }

}
