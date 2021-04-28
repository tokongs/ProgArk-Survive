package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.progarksurvive.*;
import com.mygdx.progarksurvive.entities.Crawler;
import com.mygdx.progarksurvive.entities.Zombie;
import com.mygdx.progarksurvive.entities.Player;
import com.mygdx.progarksurvive.entities.Wall;
import com.mygdx.progarksurvive.entitycomponents.*;
import com.mygdx.progarksurvive.entitysystems.*;
import com.mygdx.progarksurvive.networking.ClientNetworkHandler;
import com.mygdx.progarksurvive.networking.NetworkedEntityInfo;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class GameModel {

    public static final float worldHeight = 1000.0f * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    public static final float worldWidth = 1000.0f;

    private final World world;
    private final Engine ashley;
    private final NetworkedGameHost host;
    private final AssetManager assetManager;
    private final Main game;
    private final ClientNetworkHandler clientNetworkHandler;


    private Player player;
    public Map<Integer, Player> onlinePlayers = new HashMap<>();
    private int round = 0;
    private boolean initialized = false;


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
                onlinePlayers.get(id).setVelocity(event.direction);
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
        game.playServices.submitScore("CgkIpbOPqecIEAIQAQ", getPlayerScore());
        if (host.isActive()) {
            host.stopGameSession();
        }
    }

    public void initialize() {

        player = new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager, world, -1);

        if (game.getIsGameHost()) {
            host.getConnectionIds().forEach(id -> onlinePlayers.put(id, new Player(new Vector2(300, 300), new Vector2(50, 50), assetManager, world, id)));
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
                    e.getComponent(EntityIdComponent.class),
                    e.getComponent(NetworkIdComponent.class),
                    e.getComponent(TransformComponent.class),
                    e.getComponent(TypeComponent.class),
                    e.getComponent(ScoreComponent.class),
                    e.getComponent(HealthComponent.class)
            ))));
        }

        for (Entity e : ashley.getEntitiesFor(Family.one(EnemyComponent.class, ProjectileComponent.class).all(PhysicsBodyComponent.class).get())) {
            networkedEntities.add(new NetworkedEntityInfo(new ArrayList<>(Arrays.asList(
                    e.getComponent(EntityIdComponent.class),
                    e.getComponent(TypeComponent.class),
                    e.getComponent(TransformComponent.class)
            ))));
        }

        host.update(new HostUpdateEvent(networkedEntities));
    }

    public void update(float delta) {
        if(!game.getIsGameHost()){
            clientNetworkHandler.processIncomingUpdateEvents();
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

            boolean deadPlayers = player.entity.getComponent(HealthComponent.class).health <= 0 ||
                    onlinePlayers.values().stream().anyMatch(player -> player.entity.getComponent(HealthComponent.class).health <= 0);

            if (deadPlayers) {
                host.update(new GameOverEvent(round));
                gameOver();
            }
        }
        world.step(1 / 60f, 6, 2);
    }

    public void initializeGameRound(int numEnemies) {
        Random rand = new Random();
        for (int i = 0; i < numEnemies; i++) {
            int enemytype = rand.nextInt(2);
            if(enemytype == 0) {
                AnimationComponent animationComponent = Zombie.createAnimationComponent(assetManager);
                Vector2 position = new Vector2(rand.nextInt((int) worldWidth - 40) + 20, rand.nextInt((int) worldHeight - 40) + 20);
                Zombie enemy = new Zombie(position, new Vector2(20, 20), animationComponent, world);
                ashley.addEntity(enemy.entity);
            }
            else if(enemytype == 1){
                AnimationComponent animationComponent = Crawler.createAnimationComponent(assetManager);
                Vector2 position = new Vector2(rand.nextInt((int) worldWidth - 40) + 20, rand.nextInt((int) worldHeight - 40) + 20);
                Crawler enemy = new Crawler(position, new Vector2(53, 20), animationComponent, world);
                ashley.addEntity(enemy.entity);
            }
        }
    }

    public void movePlayer(Vector2 direction){
        if (game.getIsGameHost()) {
            player.setVelocity(direction);
        } else {
            clientNetworkHandler.sendUpdateEvent(new ClientUpdateEvent(direction));
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
