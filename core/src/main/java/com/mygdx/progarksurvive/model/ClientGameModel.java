package com.mygdx.progarksurvive.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;

import java.util.ArrayList;
import java.util.List;

public class ClientGameModel {

    private List<Vector2> players = new ArrayList<>();
    private List<Vector2> enemies = new ArrayList<>();
    private List<Vector2> projectiles = new ArrayList<>();

    private final Texture playerTexture;
    private final Texture enemyTexture;
    private final Texture projectileTexture;

    public ClientGameModel(NetworkedGameClient client, AssetManager assetManager){

        playerTexture = assetManager.get("images/player.png", Texture.class);
        enemyTexture = assetManager.get("images/player.png", Texture.class);
        projectileTexture = assetManager.get("images/player.png", Texture.class);

        client.setEventHandler((Id, event) -> {
            players = event.playerPositions;
            enemies = event.enemyPositions;
            projectiles = event.projectilePositions;
        });

    }

    public void render(float delta, SpriteBatch batch){
        batch.begin();
        players.forEach(position -> {
            batch.draw(playerTexture, position.x, position.y, 50, 50);
        });

        enemies.forEach(position -> {
            batch.draw(enemyTexture, position.x, position.y, 20, 20);
        });

        projectiles.forEach(position -> {
            batch.draw(projectileTexture, position.x, position.y, 5, 5);
        });

        batch.end();
    }
}
