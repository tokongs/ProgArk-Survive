package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.model.entitycomponents.HealthComponent;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitycomponents.VelocityComponent;
import com.mygdx.progarksurvive.model.entitysystems.MovementSystem;
import com.mygdx.progarksurvive.model.entitysystems.RenderSystem;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public class GameModel {

    public Entity player = new Entity();
    public List<Entity> enemies = new ArrayList<Entity>();

    @Inject
    public GameModel(Engine ashley, AssetManager assetManager, RenderSystem renderSystem){
        // Entities
        ashley.addEntity(player);

        // Components
        player.add(new PositionComponent(0, 0));
        player.add(new VelocityComponent(5f, 5f));
        player.add(new ImageComponent(assetManager.get("images/player.png", Texture.class), new Vector2(10, 10)));
        player.add(new HealthComponent(100));

        for(int i = 0; i  < 10; i++){
            enemies.add(new Entity());
        }

        Random rand = new Random();

        enemies.forEach(entity -> entity.add(new PositionComponent(rand.nextFloat() * 100, rand.nextFloat() * 100)));
        enemies.forEach(entity -> entity.add(new VelocityComponent((rand.nextFloat() * 10) - 5, (rand.nextFloat() * 10) - 5)));
        enemies.forEach(entity -> entity.add(new ImageComponent(assetManager.get("images/player.png", Texture.class), new Vector2(5, 5))));
        enemies.forEach(entity -> entity.add(new HealthComponent(20)));
        enemies.forEach(ashley::addEntity);
        // Systems
        ashley.addSystem(new MovementSystem());
        ashley.addSystem(renderSystem);
    }
}
