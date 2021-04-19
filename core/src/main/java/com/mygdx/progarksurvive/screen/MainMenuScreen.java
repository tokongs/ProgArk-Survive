package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public MainMenuScreen(MainMenuModel mainMenuModel, MainMenuController mainMenuController){
        model = mainMenuModel;
        controller = mainMenuController;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        playBtnTexture = new Texture("PLAY_W84_H28.png");
        settingsBtnTexture = new Texture("SETTINGS_W173_H28.png");

        model.createRectangles();

        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0,1);
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
