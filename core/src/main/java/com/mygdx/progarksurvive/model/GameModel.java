package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.progarksurvive.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import com.mygdx.progarksurvive.model.entitysystems.*;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class GameModel {

    private final float worldHeight = 1000.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    private final float worldWidth = 1000.0f;
    public Player player;
    public Map<Integer, Player> onlinePlayers = new HashMap<>();
    private final World world;
    private final Engine ashley;
    private final NetworkedGameHost host;
    private final AssetManager assetManager;
    private final Main game;
    private int round = 0;
    private boolean initialized = false;

    private final SpriteBatch batch;
    private ClientGameModel clientGameModel;

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
                     Main game,
                     NetworkedGameHost host,
                     NetworkedGameClient client,
                     SpriteBatch batch) {
        this.world = world;
        this.ashley = ashley;
        this.assetManager = assetManager;
        this.game = game;
        this.host = host;
        this.batch = batch;

        if (game.getIsGameHost()) {
            host.setEventHandler((id, event) -> {
                PhysicsBodyComponent physicsBodyComponent = onlinePlayers.get(id).entity.getComponent(PhysicsBodyComponent.class);
                if (event.touchDown) {
                    Vector2 direction = new Vector2(event.touchX - physicsBodyComponent.body.getPosition().x, event.touchY - physicsBodyComponent.body.getPosition().y).limit(1);
                    physicsBodyComponent.body.setLinearVelocity(direction.scl(100));
                } else {
                    physicsBodyComponent.body.setLinearVelocity(new Vector2(0, 0));
                }
            });
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
        } else {
            clientGameModel = new ClientGameModel(client, assetManager, game);
        }
    }

    public void setupMap() {
        int wallThickness = 10;
        Wall leftWall = new Wall(new Vector2(wallThickness / 2f, worldHeight / 2f), new Vector2(wallThickness, worldHeight), Color.BLUE, world);
        Wall rightWall = new Wall(new Vector2(worldWidth - wallThickness / 2f, worldHeight / 2f), new Vector2(wallThickness, worldHeight), Color.BLUE, world);
        Wall topWall = new Wall(new Vector2(worldWidth / 2f + wallThickness / 2f, worldHeight - wallThickness / 2f), new Vector2(worldWidth - wallThickness, wallThickness), Color.BLUE, world);
        Wall bottomWall = new Wall(new Vector2(worldWidth / 2f + wallThickness / 2f, wallThickness / 2f), new Vector2(worldWidth - wallThickness, wallThickness), Color.BLUE, world);

        Wall columnTop = new Wall(new Vector2(500 - wallThickness * 4, 100 + wallThickness * 4), new Vector2(wallThickness * 8, wallThickness * 8), Color.GREEN, world);
        Wall columnBot = new Wall(new Vector2(500 - wallThickness * 4, worldHeight - 100 - (wallThickness * 4)), new Vector2(wallThickness * 8, wallThickness * 8), Color.RED, world);

        ashley.addEntity(leftWall.entity);
        ashley.addEntity(rightWall.entity);
        ashley.addEntity(topWall.entity);
        ashley.addEntity(bottomWall.entity);
        ashley.addEntity(columnTop.entity);
        ashley.addEntity(columnBot.entity);
    }

    public void gameOver() {
        ashley.removeAllEntities();
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        bodies.forEach(world::destroyBody);
        initialized = false;
        game.setState(GameState.GAME_OVER);
    }

    public void initialize() {
        player = new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager.get("images/player.png", Texture.class), world);

        if (game.getIsGameHost()) {
            host.getConnectionIds().forEach(id -> onlinePlayers.put(id, new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager.get("images/player.png", Texture.class), world)));
            onlinePlayers.forEach((id, player) -> ashley.addEntity(player.entity));
        }

        ashley.addEntity(player.entity);
        setupMap();
        round = 0;
        initialized = true;
    }

    public void update(float delta) {


        if (game.getIsGameHost()) {
            if (!initialized) {
                initialize();
            }
            ImmutableArray<Entity> enemies = ashley.getEntitiesFor(Family.all(EnemyComponent.class).get());
            if (enemies.size() == 0) {
                round += 1;
                initializeGameRound(9 + round);
            }

            List<Vector2> playerPositions = new ArrayList<>();
            List<Vector2> enemyPositions = new ArrayList<>();
            List<Vector2> projectilePositions = new ArrayList<>();
            Map<Integer, Integer> playerHealth = new HashMap<>();
            Map<Integer, Integer> playerScore = new HashMap<>();

            for (Entity e : ashley.getEntitiesFor(Family.all(PlayerComponent.class, PhysicsBodyComponent.class).get())) {
                playerPositions.add(e.getComponent(PhysicsBodyComponent.class).body.getPosition());
            }
            for (Entity e : ashley.getEntitiesFor(Family.all(EnemyComponent.class, PhysicsBodyComponent.class).get())) {
                enemyPositions.add(e.getComponent(PhysicsBodyComponent.class).body.getPosition());
            }
            for (Entity e : ashley.getEntitiesFor(Family.all(ProjectileComponent.class, PhysicsBodyComponent.class).get())) {
                projectilePositions.add(e.getComponent(PhysicsBodyComponent.class).body.getPosition());
            }

            boolean deadPlayers = false;
            for(Integer id: onlinePlayers.keySet()) {
                Entity entity = onlinePlayers.get(id).entity;
                int health = entity.getComponent(HealthComponent.class).health;
                if(health <= 0){
                    deadPlayers = true;
                }
                playerHealth.put(id, health);
                playerScore.put(id, entity.getComponent(ScoreComponent.class).score);
            }
            if(deadPlayers || player.entity.getComponent(HealthComponent.class).health <= 0){
                host.update(new GameOverEvent(round, playerScore, playerHealth));
                gameOver();
            }

            host.update(new HostUpdateEvent(playerPositions, enemyPositions, projectilePositions, playerHealth, playerScore));
            ashley.update(delta);
            world.step(1 / 60f, 6, 2);

        } else {
            clientGameModel.render(delta, batch);
        }
    }

    public void initializeGameRound(int numEnemies) {
        Texture enemyTexture = assetManager.get("images/player.png", Texture.class);
        Random rand = new Random();
        for (int i = 0; i < numEnemies; i++) {
            Vector2 position = new Vector2(rand.nextInt((int) worldWidth - 40) + 20, rand.nextInt((int) worldHeight - 40) + 20);
            Enemy enemy = new Enemy(position, new Vector2(20, 20), enemyTexture, world);
            ashley.addEntity(enemy.entity);
        }
    }

    public int getPlayerScore() {
        if(game.getIsGameHost()){
            return player.entity.getComponent(ScoreComponent.class).score;
        }
        return clientGameModel.getScore();
    }

    public int getPlayerHealth() {
        if(game.getIsGameHost()){
            return player.entity.getComponent(HealthComponent.class).health;
        }
        return clientGameModel.getHealth();
    }

    public int getRound() {
        if(game.getIsGameHost()){
            return round;
        }
        return clientGameModel.getRound();
    }
}
