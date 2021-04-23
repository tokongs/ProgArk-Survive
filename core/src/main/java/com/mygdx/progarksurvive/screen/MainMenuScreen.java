package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.progarksurvive.controller.MainMenuController;
import com.mygdx.progarksurvive.model.MainMenuModel;

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
    public MainMenuScreen(MainMenuModel mainMenuModel, MainMenuController mainMenuController, SpriteBatch batch){
        model = mainMenuModel;
        controller = mainMenuController;
        this.batch = batch;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        playBtnTexture = new Texture("PLAY_W84_H28.png");
        settingsBtnTexture = new Texture("SETTINGS_W173_H28.png");

        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
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
    dispose();
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
