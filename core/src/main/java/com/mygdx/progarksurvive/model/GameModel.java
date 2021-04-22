package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.progarksurvive.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import com.mygdx.progarksurvive.model.entitysystems.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public class GameModel {

    private final float worldHeight = 1000.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    private final float worldWidth = 1000.0f;
    public Player player;
    private final World world;
    private final Engine ashley;
    private final AssetManager assetManager;
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final Main game;
    private int round = 1;
    private boolean initialized = false;
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
                     World world,
                     Main game) {
        this.world = world;
        this.ashley = ashley;
        this.assetManager = assetManager;
        this.game = game;

        initialize();

        world.setContactListener(new CollisionListener());

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

    public void setupMap(){
        int wallThickness = 10;
        Wall leftWall = new Wall(new Vector2(wallThickness / 2f, worldHeight / 2f), new Vector2(wallThickness, worldHeight), Color.BLUE, world);
        Wall rightWall = new Wall(new Vector2(worldWidth - wallThickness / 2f, worldHeight / 2f), new Vector2(wallThickness, worldHeight), Color.BLUE, world);
        Wall topWall = new Wall(new Vector2(worldWidth / 2f + wallThickness / 2f, worldHeight - wallThickness / 2f), new Vector2(worldWidth - wallThickness, wallThickness), Color.BLUE, world);
        Wall bottomWall = new Wall(new Vector2(worldWidth / 2f + wallThickness / 2f, wallThickness / 2f), new Vector2(worldWidth - wallThickness, wallThickness), Color.BLUE, world);

        Wall columnTop = new Wall(new Vector2(500- wallThickness *4, 100+ wallThickness *4), new Vector2(wallThickness *8, wallThickness *8), Color.GREEN, world);
        Wall columnBot = new Wall(new Vector2(500- wallThickness *4, worldHeight-100-(wallThickness *4)), new Vector2(wallThickness *8, wallThickness *8), Color.RED, world);

        ashley.addEntity(leftWall.entity);
        ashley.addEntity(rightWall.entity);
        ashley.addEntity(topWall.entity);
        ashley.addEntity(bottomWall.entity);
        ashley.addEntity(columnTop.entity);
        ashley.addEntity(columnBot.entity);
    }

    public void gameOver(){
        ashley.removeAllEntities();
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        bodies.forEach(world::destroyBody);
        initialized = false;
        game.setState(GameState.MAIN_MENU);
    }

    public void initialize(){
        player = new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager.get("images/player.png", Texture.class), world);
        ashley.addEntity(player.entity);
        setupMap();
        round = 1;
        initialized = true;
    }

    public void update(float delta) {
        if(!initialized){
            initialize();
        }
        if(player.entity.getComponent(HealthComponent.class).health <= 0) {
            gameOver();
            return;
        }

        ImmutableArray<Entity> enemies = ashley.getEntitiesFor(Family.all(EnemyComponent.class).get());
        if(enemies.size() == 0){
            round += 1;
            initializeGameRound(10);
        }

        ashley.update(delta);
        world.step(1 / 60f, 6, 2);
    }

    public void initializeGameRound(int numEnemies){
        Texture enemyTexture = assetManager.get("images/player.png", Texture.class);
        for (int i = 0; i < numEnemies; i++) {
            Enemy enemy = new Enemy(new Vector2((i+1) * 80,  400), new Vector2(20, 20), enemyTexture, world);
            ashley.addEntity(enemy.entity);
        }
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
