package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.controller.GameController;
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
    private final Stage stage;

    @Inject
    public GameScreen(GameController controller, GameModel model, SpriteBatch batch, AssetManager assetManager) {
        this.controller = controller;
        this.model = model;
        this.batch = batch;
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 0.0f);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        generator.dispose();

        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        stage = new Stage(new StretchViewport(800.0f, 800.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth()), batch);
        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);
        Touchpad touchpad = new Touchpad(5, skin);
        table.right().bottom().add(touchpad).size(50, 50).padRight(20).padBottom(20);

        touchpad.addListener(controller);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        controller.update(delta);

        font.setColor(Color.WHITE);
        font.draw(batch, String.valueOf(model.getPlayerScore()), 12, 1000.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth() - 12);
        font.setColor(Color.RED);
        font.draw(batch, String.valueOf(model.getPlayerHealth() / 100f), 890, 1000.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth() - 12);
        batch.end();
        stage.act();
        stage.draw();

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