package com.mygdx.progarksurvive.model;

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
            }

        });

    }

    public void render(float delta, SpriteBatch batch){
        batch.begin();
        players.forEach(position -> {
            batch.draw(playerTexture, position.x-25, position.y-25, 50, 50);
        });

        enemies.forEach(position -> {
            batch.draw(enemyTexture, position.x-10, position.y-10, 20, 20);
        });

        projectiles.forEach(position -> {
            batch.draw(projectileTexture, position.x -2.5f, position.y-2.5f, 5, 5);
        });



        batch.end();
    }
}
