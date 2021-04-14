package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.progarksurvive.Game;
import com.mygdx.progarksurvive.controller.MainMenuController;
import com.mygdx.progarksurvive.model.MainMenuModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuScreen implements Screen {
    private final MainMenuController controller;
    private SpriteBatch batch;

    private final float playBtnW = 77;
    private final float playBtnH = 29;
    private final float settingsBtnW = 162;
    private final float settingsBtnH = 29;


    private Vector2 playBtnPosition;
    private Vector2 settingBtnPosition;
    private Rectangle playBtnRect;
    private Rectangle settingsBtnRect;

    private Texture playBtnTexture;
    private Texture settingsBtnTexture;

    @Inject
    public MainMenuScreen(MainMenuController controller){
        this.controller = controller;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        playBtnTexture = new Texture("playbtn_w77_h29.png");
        settingsBtnTexture = new Texture("settingbtn_w162_h29.png");

        playBtnPosition = new Vector2((float)Gdx.graphics.getWidth()/2 - playBtnW/2, (float)Gdx.graphics.getHeight()/2- playBtnH/2 + 30);
        settingBtnPosition = new Vector2((float)Gdx.graphics.getWidth()/2 -settingsBtnW/2, (float)Gdx.graphics.getHeight()/2 - settingsBtnH/2 - 30);

        playBtnRect = new Rectangle(playBtnPosition.x, playBtnPosition.y , playBtnW, playBtnH);
        settingsBtnRect = new Rectangle(settingBtnPosition.x, settingBtnPosition.y, settingsBtnW, settingsBtnH);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        batch.begin();
        batch.draw(playBtnTexture, playBtnPosition.x, playBtnPosition.y);
        batch.draw(settingsBtnTexture, settingBtnPosition.x, settingBtnPosition.y);
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
        playBtnTexture.dispose();
        settingsBtnTexture.dispose();
    }
}
