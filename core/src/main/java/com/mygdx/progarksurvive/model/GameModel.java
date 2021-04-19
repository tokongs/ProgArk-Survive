package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.progarksurvive.CollisionListener;
import com.mygdx.progarksurvive.Player;
import com.mygdx.progarksurvive.Wall;
import com.mygdx.progarksurvive.model.entitycomponents.HealthComponent;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitysystems.InputSystem;
import com.mygdx.progarksurvive.model.entitysystems.PositionSystem;
import com.mygdx.progarksurvive.model.entitysystems.RenderSystem;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public class GameModel {

    private final float worldHeight = 100.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public final Player player;
    public List<Entity> enemies = new ArrayList<Entity>();
    private final World world = new World(new Vector2(0, 0), true);
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    @Inject
    public GameModel(Engine ashley, AssetManager assetManager, RenderSystem renderSystem, PositionSystem positionSystem) {
        world.setContactListener(new CollisionListener());

        player = new Player(new Vector2(30, 30), new Vector2(15, 15), assetManager.get("images/player.png", Texture.class), world);

        Wall leftWall = new Wall(new Vector2(0, 0), new Vector2(5, worldHeight), Color.BLUE, world);
        Wall rightWall = new Wall(new Vector2(100 - 5, 0), new Vector2(5, worldHeight), Color.BLUE, world);
        Wall topWall = new Wall(new Vector2(5, worldHeight - 5), new Vector2(90, 5), Color.BLUE, world);
        Wall bottomWall = new Wall(new Vector2(5, 0), new Vector2(90, 5), Color.BLUE, world);

        ashley.addEntity(leftWall.entity);
        ashley.addEntity(rightWall.entity);
        ashley.addEntity(topWall.entity);
        ashley.addEntity(bottomWall.entity);
        // Entities
        ashley.addEntity(player.entity);

        for (int i = 0; i < 10; i++) {
            enemies.add(new Entity());
        }

        Random rand = new Random();

        enemies.forEach(entity -> entity.add(new PositionComponent(rand.nextFloat() * 100, rand.nextFloat() * 100)));
        enemies.forEach(entity -> entity.add(new ImageComponent(assetManager.get("images/player.png", Texture.class), new Vector2(5, 5))));
        enemies.forEach(entity -> entity.add(new HealthComponent(20)));
        enemies.forEach(ashley::addEntity);
        // Systems
        ashley.addSystem(renderSystem);
        ashley.addSystem(positionSystem);
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
    }

    public void debugRender(Matrix4 projectionMatrix) {
        debugRenderer.render(world, projectionMatrix);
    }
}
