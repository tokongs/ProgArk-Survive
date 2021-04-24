package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ClientGameModel {

    private List<Vector2> players = new ArrayList<>();
    private List<Vector2> enemies = new ArrayList<>();
    private List<Vector2> projectiles = new ArrayList<>();
    private int health;
    private int score;
    private int round;

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }
    public int getRound() {
        return round;
    }


    private final Texture playerTexture;
    private final Texture enemyTexture;
    private final Texture projectileTexture;

    public ClientGameModel(NetworkedGameClient client, AssetManager assetManager, Main game){

        playerTexture = assetManager.get("images/player.png", Texture.class);
        enemyTexture = assetManager.get("images/player.png", Texture.class);
        projectileTexture = assetManager.get("images/player.png", Texture.class);

        client.setEventHandler((id, event) -> {
            if(event instanceof HostUpdateEvent){
                HostUpdateEvent e = (HostUpdateEvent) event;
                players = e.playerPositions;
                enemies = e.enemyPositions;
                projectiles = e.projectilePositions;
                score = e.playerScore.get(id);
                health = e.playerHealth.get(id);
            } else if (event instanceof GameOverEvent){
                GameOverEvent e = (GameOverEvent) event;
                health = e.health.get(id);
                score = e.score.get(id);
                round = e.round;
                game.setState(GameState.GAME_OVER);
                client.leaveGameSession();
            }
        });
    }

    public List<Vector2> getPlayers() {
        return players;
    }

    public List<Vector2> getEnemies() {
        return enemies;
    }

    public List<Vector2> getProjectiles() {
        return projectiles;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Texture getEnemyTexture() {
        return enemyTexture;
    }

    public Texture getProjectileTexture() {
        return projectileTexture;
    }
}
