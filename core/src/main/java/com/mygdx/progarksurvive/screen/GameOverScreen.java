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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.networking.events.GameOverEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameOverScreen implements Screen {

    private final GameModel model;

    private final Stage stage;
    private final TextButton mainMenuButton;
    private final Label scoreLabel;
    private final Label healthLabel;
    private final Label roundLabel;
    private final Label scoreValueLabel;
    private final Label healthValueLabel;
    private final Label roundValueLabel;

    @Inject
    public GameOverScreen(GameModel model, AssetManager assetManager, Main game, SpriteBatch batch){
        this.model = model;

        Skin skin = assetManager.get("skin/uiskin.json", Skin.class);

        stage =  new Stage(new StretchViewport(800.0f, 800.0f * (Gdx.graphics.getHeight()) / Gdx.graphics.getWidth()), batch);

        scoreLabel = createLabel("Score", skin);
        roundLabel = createLabel("Round", skin);
        healthLabel = createLabel("Remaining health", skin);

        scoreValueLabel = createLabel(": 0", skin);
        roundValueLabel = createLabel(": 0", skin);
        healthValueLabel = createLabel(": 0", skin);


        mainMenuButton = new TextButton("Back to main menu", skin);

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setState(GameState.MAIN_MENU);
                return false;
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.add(scoreLabel).size(280, 60);
        table.add(scoreValueLabel).size(100, 60);

        table.row();
        table.add(roundLabel).size(280, 60);
        table.add(roundValueLabel).size(100, 60);

        table.row();
        table.add(healthLabel).size(280, 60);
        table.add(healthValueLabel).size(100, 60);

        table.row();
        table.add(mainMenuButton).size(200, 100).padTop(50).colspan(2);

        stage.addActor(table);
    }

    private Label createLabel(String text, Skin skin){
        Label label = new Label(text,skin);
        label.setFontScaleX(2);
        label.setFontScaleY(2);
        label.setColor(Color.GREEN);
        label.setAlignment(Align.left);
        return label;
    }

    private void setText(){
        scoreValueLabel.setText(": " + model.getPlayerScore());
        healthValueLabel.setText(": " + Math.max(0, model.getPlayerHealth() / 100));
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
