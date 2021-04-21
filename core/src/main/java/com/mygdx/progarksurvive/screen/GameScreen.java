package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.progarksurvive.controller.GameController;
import com.mygdx.progarksurvive.model.GameModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameScreen implements Screen {

    private final GameController controller;
    private final GameModel model;
    private final OrthographicCamera camera = new OrthographicCamera(100.0f, 100.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth());
    private final SpriteBatch batch;
    BitmapFont font = new BitmapFont();

    @Inject
    public GameScreen(GameController controller, GameModel model, SpriteBatch batch){
        this.controller = controller;
        this.model = model;
        this.batch = batch;
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 0.0f);
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
        controller.update(delta, camera);
        batch.begin();
        font.draw(batch, String.valueOf(model.getScore()), 2, 100.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth() - 4);
        batch.end();
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