package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.progarksurvive.*;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import com.mygdx.progarksurvive.model.entitysystems.*;
import com.mygdx.progarksurvive.networking.NetworkedEntityInfo;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

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

    private ClientNetworkHandler clientNetworkHandler;

    @Inject
    public GameModel(Engine ashley, AssetManager assetManager, ProjectileImpactSystem projectileImpactSystem,
                     RenderSystem renderSystem,
                     HealthSystem healthSystem,
                     TransformSystem transformSystem,
                     PlayerTargetingSystem playerTargetingSystem,
                     EnemyTargetingSystem enemyTargetingSystem,
                     EnemyMovementSystem enemyMovementSystem,
                     ShootingSystem shootingSystem,
                     PlayerDamageSystem playerDamageSystem,
                     World world,
                     Main game,
                     NetworkedGameHost host,
                     AnimationSystem animationSystem,
                     ClientNetworkHandler clientNetworkHandler) {
        this.world = world;
        this.ashley = ashley;
        this.assetManager = assetManager;
        this.game = game;
        this.host = host;
        this.clientNetworkHandler = clientNetworkHandler;

        if (game.getIsGameHost()) {
            host.setEventHandler((id, event) -> {
                PhysicsBodyComponent physicsBodyComponent = onlinePlayers.get(id).entity.getComponent(PhysicsBodyComponent.class);
                physicsBodyComponent.body.setLinearVelocity(event.direction.scl(100));
            });
            initialize();

            world.setContactListener(new CollisionListener());

            ashley.addSystem(renderSystem);
            ashley.addSystem(healthSystem);
            ashley.addSystem(transformSystem);
            ashley.addSystem(shootingSystem);
            ashley.addSystem(playerTargetingSystem);
            ashley.addSystem(enemyTargetingSystem);
            ashley.addSystem(enemyMovementSystem);
            ashley.addSystem(playerDamageSystem);
            ashley.addSystem(projectileImpactSystem);
            ashley.addSystem(animationSystem);
        } else {
            ashley.addSystem(renderSystem);
            ashley.addSystem(animationSystem);
            ashley.addSystem(transformSystem);
            setupMap();

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
        onlinePlayers.clear();
        if (host.isActive()) {
            host.stopGameSession();
        }
    }

    public void initialize() {

        player = new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager, world);

        if (game.getIsGameHost()) {
            host.getConnectionIds().forEach(id -> onlinePlayers.put(id, new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager, world)));
            onlinePlayers.forEach((id, player) -> ashley.addEntity(player.entity));
        }

        ashley.addEntity(player.entity);
        setupMap();
        round = 0;
        initialized = true;
    }

    public void broadCastUpdateEvent(){
        List<NetworkedEntityInfo> networkedEntities = new ArrayList<>();

        for (Entity e : ashley.getEntitiesFor(Family.all(PlayerComponent.class).get())) {
            networkedEntities.add(new NetworkedEntityInfo(new ArrayList<>(Arrays.asList(
                    e.getComponent(NetworkIdComponent.class),
                    e.getComponent(TransformComponent.class),
                    e.getComponent(TypeComponent.class),
                    e.getComponent(ScoreComponent.class),
                    e.getComponent(HealthComponent.class)
            ))));
        }

        for (Entity e : ashley.getEntitiesFor(Family.one(EnemyComponent.class, ProjectileComponent.class).all(PhysicsBodyComponent.class).get())) {
            networkedEntities.add(new NetworkedEntityInfo(new ArrayList<>(Arrays.asList(
                    e.getComponent(NetworkIdComponent.class),
                    e.getComponent(TypeComponent.class),
                    e.getComponent(TransformComponent.class)
            ))));
        }

        host.update(new HostUpdateEvent(networkedEntities));
    }

    public void update(float delta) {
        if(!game.getIsGameHost()){
            clientNetworkHandler.update();
        }
        ashley.update(delta);
        if (game.getIsGameHost()) {

            if (!initialized) {
                initialize();
            }
            ImmutableArray<Entity> enemies = ashley.getEntitiesFor(Family.all(EnemyComponent.class).get());
            if (enemies.size() == 0) {
                round += 1;
                initializeGameRound(9 + round);
            }

            broadCastUpdateEvent();
            Map<Integer, Integer> playerHealth = new HashMap<>();
            Map<Integer, Integer> playerScore = new HashMap<>();
            boolean deadPlayers = false;
            for (Integer id : onlinePlayers.keySet()) {
                Entity entity = onlinePlayers.get(id).entity;
                int health = entity.getComponent(HealthComponent.class).health;
                if (health <= 0) {
                    deadPlayers = true;
                }
                playerHealth.put(id, health);
                playerScore.put(id, entity.getComponent(ScoreComponent.class).score);
            }
            if (deadPlayers || player.entity.getComponent(HealthComponent.class).health <= 0) {
                host.update(new GameOverEvent(round, playerScore, playerHealth));
                gameOver();
            }
        }
        world.step(1 / 60f, 6, 2);
    }

    public void initializeGameRound(int numEnemies) {
        Random rand = new Random();
        for (int i = 0; i < numEnemies; i++) {
            AnimationComponent animationComponent = Enemy.createAnimationComponent(assetManager);
            Vector2 position = new Vector2(rand.nextInt((int) worldWidth - 40) + 20, rand.nextInt((int) worldHeight - 40) + 20);
            Enemy enemy = new Enemy(position, new Vector2(20, 20), animationComponent, world);
            ashley.addEntity(enemy.entity);
        }
    }

    public int getPlayerScore() {
        if (game.getIsGameHost()) {
            return player.entity.getComponent(ScoreComponent.class).score;
        }
        return clientNetworkHandler.getScore();
    }

    public int getPlayerHealth() {
        if (game.getIsGameHost()) {
            return player.entity.getComponent(HealthComponent.class).health;
        }
        return clientNetworkHandler.getHealth();
    }

    public int getRound() {
        if (game.getIsGameHost()) {
            return round;
        }
        return clientNetworkHandler.getRound();
    }
}
