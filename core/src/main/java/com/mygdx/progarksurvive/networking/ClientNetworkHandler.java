package com.mygdx.progarksurvive.networking;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.*;
import com.mygdx.progarksurvive.entities.Crawler;
import com.mygdx.progarksurvive.entities.Zombie;
import com.mygdx.progarksurvive.entities.Player;
import com.mygdx.progarksurvive.entities.Projectile;
import com.mygdx.progarksurvive.entitycomponents.*;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ClientNetworkHandler {
    private final NetworkedGameClient client;
    private final Engine ashley;
    private final AssetManager assetManager;
    
    private int health;
    private int score;
    private int round;
    private HostUpdateEvent latestHostUpdateEvent;

    @Inject
    public ClientNetworkHandler(NetworkedGameClient client, AssetManager assetManager, Main game, Engine ashley) {
        this.ashley = ashley;
        this.client = client;
        this.assetManager = assetManager;
        client.setEventHandler((id, event) -> {
            if (event instanceof HostUpdateEvent) {
                latestHostUpdateEvent = (HostUpdateEvent) event;
            } else if (event instanceof GameOverEvent) {
                GameOverEvent e = (GameOverEvent) event;
                round = e.round;
                game.setState(GameState.GAME_OVER);
                client.leaveGameSession();
            }
        });
    }

    public void processIncomingUpdateEvents() {
        if (latestHostUpdateEvent == null) return;
        Map<Long, Entity> entities = new HashMap<>();
        for (Entity entity : ashley.getEntitiesFor(Family.all(EntityIdComponent.class).get())) {
            entities.put(entity.getComponent(EntityIdComponent.class).getId(), entity);
        }
        latestHostUpdateEvent.entities.forEach(info -> {
            long entityId = ((EntityIdComponent) info.components.get(0)).getId();
            if (!entities.containsKey(entityId)) {
                Entity newEntity = new Entity();
                info.components.forEach(newEntity::add);
                switch (newEntity.getComponent(TypeComponent.class).type) {
                    case PLAYER:
                        newEntity.add(Player.createAnimationComponent(assetManager));
                        newEntity.add(new ImageComponent(Color.RED, new Vector2(50, 50)));
                        break;
                    case ZOMBIE:
                        newEntity.add(Zombie.createAnimationComponent(assetManager));
                        newEntity.add(new ImageComponent(Color.RED, new Vector2(20, 20)));
                        break;
                    case PROJECTILE:
                        newEntity.add(Projectile.createImageComponent(assetManager));
                        break;
                    case CRAWLER:
                        newEntity.add(Crawler.createAnimationComponent(assetManager));
                        newEntity.add(new ImageComponent(Color.RED, new Vector2(7,21)));
                }
                ashley.addEntity(newEntity);
            } else {
                Entity entity = entities.get(entityId);
                info.components.forEach(entity::add);
                NetworkIdComponent networkId = entity.getComponent(NetworkIdComponent.class);
                if(networkId != null && networkId.getId() == client.getConnectionId()){
                    score = entity.getComponent(ScoreComponent.class).score;
                    health = entity.getComponent(HealthComponent.class).health;
                }
            }
            entities.remove(entityId);
        });

        entities.forEach((aLong, entity) -> ashley.removeEntity(entity));
    }

    public void sendUpdateEvent(ClientUpdateEvent event){
        client.update(event);
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public int getRound() {
        return round;
    }
}
