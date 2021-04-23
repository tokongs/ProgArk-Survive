package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Alignment;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameOverScreen implements Screen {

    private final GameModel model;
    private final SpriteBatch batch;
    private final OrthographicCamera camera = new OrthographicCamera(1000.0f, 1000.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth());
    private final BitmapFont font;

    private int round = 0;
    private int score = 0;
    private int health = 0;

    private final Stage stage = new Stage(new StretchViewport(800.0f, 800.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth()));;
    private final TextButton mainMenuButton;
    private final Label scoreLabel;
    private final Label healthLabel;
    private final Label roundLabel;
    private final Label scoreValueLabel;
    private final Label healthValueLabel;
    private final Label roundValueLabel;

    @Inject
    public GameOverScreen(GameModel model, SpriteBatch batch, AssetManager assetManager){
        this.model = model;
        this.batch = batch;
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 0.0f);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("UbuntuMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        generator.dispose();

        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        scoreLabel = createLabel("Score", skin);
        roundLabel = createLabel("Round", skin);
        healthLabel = createLabel("Remaining health", skin);

        scoreValueLabel = createLabel(": 0", skin);
        roundValueLabel = createLabel(": 0", skin);
        healthValueLabel = createLabel(": 0", skin);

        mainMenuButton = new TextButton("Back to main menu", skin);
        Table table = new Table();
        table.setFillParent(true);
        table.add(scoreLabel).size(280, 60);
        table.add(scoreValueLabel);

        table.row();
        table.add(roundLabel).size(280, 60);
        table.add(roundValueLabel);

        table.row();
        table.add(healthLabel).size(280, 60);
        table.add(healthValueLabel);

        table.row();
        table.add(mainMenuButton).size(200, 100).padTop(50);

        stage.addActor(table);
    }

    private Label createLabel(String text, Skin skin){
        Label label = new Label(text,skin);
        label.setFontScaleX(2);
        label.setFontScaleY(2);
        label.setColor(Color.GREEN);
        return label;
    }

    private void setText(){
        scoreValueLabel.setText(": " + model.getPlayerScore());
        healthValueLabel.setText(": " + model.getPlayerHealth());
        roundValueLabel.setText(": " + model.getRound());
    }

    @Override
    public void show() {
        setText();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.GREEN);
        //font.draw(batch, "Score            : " + score, (1000 / 2f) - 160, 620.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth());
        //font.draw(batch, "Round            : " + round, (1000 / 2f) - 160, 520.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth());
       // font.draw(batch, "Remaining health : " + health, (1000 / 2f) - 160, 420.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth());
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
