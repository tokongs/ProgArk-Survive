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
import com.mygdx.progarksurvive.Enemy;
import com.mygdx.progarksurvive.Player;
import com.mygdx.progarksurvive.Wall;
import com.mygdx.progarksurvive.model.entitycomponents.HealthComponent;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PositionComponent;
import com.mygdx.progarksurvive.model.entitycomponents.ScoreComponent;
import com.mygdx.progarksurvive.model.entitysystems.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public class GameModel {

    private final float worldHeight = 100.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public final Player player;
    private final World world;
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    @Inject
    public GameModel(Engine ashley, AssetManager assetManager, ProjectileImpactSystem projectileImpactSystem,
                     RenderSystem renderSystem,
                     HealthSystem healthSystem,
                     PositionSystem positionSystem,
                     PlayerTargetingSystem playerTargetingSystem,
                     EnemyTargetingSystem enemyTargetingSystem,
                     EnemyMovementSystem enemyMovementSystem,
                     ShootingSystem shootingSystem,
                     PlayerDamageSystem playerDamageSystem,
                     World world) {
        this.world = world;

        world.setContactListener(new CollisionListener());

        player = new Player(new Vector2(30, 30), new Vector2(5, 5), assetManager.get("images/player.png", Texture.class), world);

        int wallThickness = 1;
        Wall leftWall = new Wall(new Vector2(0, 0), new Vector2(wallThickness, worldHeight), Color.BLUE, world);
        Wall rightWall = new Wall(new Vector2(100 - wallThickness, 0), new Vector2(wallThickness, worldHeight), Color.BLUE, world);
        Wall topWall = new Wall(new Vector2(wallThickness, worldHeight - wallThickness), new Vector2(100- wallThickness, wallThickness), Color.BLUE, world);
        Wall bottomWall = new Wall(new Vector2(wallThickness, 0), new Vector2(100 - wallThickness, wallThickness), Color.BLUE, world);
        Wall columnTop = new Wall(new Vector2(50- wallThickness *4, 10+ wallThickness *4), new Vector2(wallThickness *8, wallThickness *8), Color.GREEN, world);
        Wall columnBot = new Wall(new Vector2(50- wallThickness *4, worldHeight-10-(wallThickness *4)), new Vector2(wallThickness *8, wallThickness *8), Color.RED, world);

        ashley.addEntity(leftWall.entity);
        ashley.addEntity(rightWall.entity);
        ashley.addEntity(topWall.entity);
        ashley.addEntity(bottomWall.entity);
        ashley.addEntity(columnTop.entity);
        ashley.addEntity(columnBot.entity);
        // Entities
        ashley.addEntity(player.entity);
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            Enemy enemy = new Enemy(new Vector2(i * 8,  40), new Vector2(2, 2), assetManager.get("images/player.png", Texture.class), world);
            ashley.addEntity(enemy.entity);
        }
        // Systems
        ashley.addSystem(renderSystem);
        ashley.addSystem(healthSystem);
        ashley.addSystem(positionSystem);
        ashley.addSystem(shootingSystem);
        ashley.addSystem(playerTargetingSystem);
        ashley.addSystem(enemyTargetingSystem);
        ashley.addSystem(enemyMovementSystem);
        ashley.addSystem(playerDamageSystem);
        ashley.addSystem(projectileImpactSystem);
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
    }

    public int getPlayerScore(){
        return player.entity.getComponent(ScoreComponent.class).score;
    }

    public int getPlayerHealth(){
        return player.entity.getComponent(HealthComponent.class).health;
    }

    public void debugRender(Matrix4 projectionMatrix) {
        debugRenderer.render(world, projectionMatrix);
    }
}
