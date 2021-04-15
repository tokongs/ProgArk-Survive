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
import com.sun.tools.javac.Main;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuScreen implements Screen {
    private SpriteBatch batch;
    private final MainMenuModel model;
    private final MainMenuController controller;

    private Texture playBtnTexture;
    private Texture settingsBtnTexture;

    @Inject
    public MainMenuScreen(MainMenuModel mainMenuModel, MainMenuController mainMenuController){
        model = mainMenuModel;
        controller = mainMenuController;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        playBtnTexture = new Texture("playbtn_w77_h29.png");
        settingsBtnTexture = new Texture("settingbtn_w162_h29.png");

        model.createRectangles();

        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        batch.begin();
        batch.draw(playBtnTexture, model.getPlayBtnPosition().x, model.getPlayBtnPosition().y);
        batch.draw(settingsBtnTexture, model.getSettingBtnPosition().x, model.getSettingBtnPosition().y);
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

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
        batch.dispose();
        playBtnTexture.dispose();
        settingsBtnTexture.dispose();
    }
}
