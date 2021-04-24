package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.controller.GameController;
import com.mygdx.progarksurvive.model.ClientGameModel;
import com.mygdx.progarksurvive.model.GameModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameScreen implements Screen {

    private final GameController controller;
    private final GameModel model;
    private final OrthographicCamera camera = new OrthographicCamera(1000.0f, 1000.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth());
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Main game;

    @Inject
    public GameScreen(GameController controller, GameModel model,Main game, SpriteBatch batch) {
        this.controller = controller;
        this.model = model;
        this.game = game;
        this.batch = batch;
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 0.0f);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        controller.update(delta, camera);
        if (!game.getIsGameHost()) {
            renderGameClientModel();
        }

        font.setColor(Color.WHITE);
        font.draw(batch, String.valueOf(model.getPlayerScore()), 2, 1000.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth() - 4);
        font.setColor(Color.RED);
        font.draw(batch, String.valueOf(model.getPlayerHealth() / 100f), 900, 1000.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth() - 4);
        batch.end();

    }

    void renderGameClientModel() {
        ClientGameModel clientGameModel = model.getClientGameModel();
        clientGameModel.getPlayers().forEach(position -> {
            batch.draw(clientGameModel.getPlayerTexture(), position.x - 25, position.y - 25, 50, 50);
        });

        clientGameModel.getEnemies().forEach(position -> {
            batch.draw(clientGameModel.getEnemyTexture(), position.x - 10, position.y - 10, 20, 20);
        });

        clientGameModel.getProjectiles().forEach(position -> {
            batch.draw(clientGameModel.getProjectileTexture(), position.x - 2.5f, position.y - 2.5f, 5, 5);
        });
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}