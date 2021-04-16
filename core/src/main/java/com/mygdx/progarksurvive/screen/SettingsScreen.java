package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.controller.SettingsController;
import com.mygdx.progarksurvive.model.SettingsModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsScreen implements Screen {
    private SpriteBatch batch;
    private final SettingsModel model;
    private final SettingsController controller;

    private Texture mutegame;
    private Texture gameMute;
    private Texture musicMute;
    private Texture settingsBtnTexture;


    @Inject
    public SettingsScreen(SettingsModel settingsModel, SettingsController settingsController){
        model = settingsModel;
        controller = settingsController;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        mutegame = new Texture("PLAY_W84_H28.png");
        gameMute = new Texture("PLAY_W84_H28.png");
        musicMute = new Texture("PLAY_W84_H28.png");
        settingsBtnTexture = new Texture("PLAY_W84_H28.png");

        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0,1);
        batch.begin();
        if(model.isGameVolume()){
            batch.draw(gameMute,model.getGamePos().x-gameMute.getWidth()/2f, model.getGamePos().y);
        }
        if(!model.isGameVolume()){
            batch.draw(settingsBtnTexture,model.getGamePos().x-settingsBtnTexture.getWidth()/2f, model.getGamePos().y);
        }
        if(model.isMusicVolume()){
            batch.draw(musicMute,model.getMusicPos().x-musicMute.getWidth()/2f, model.getMusicPos().y);
        }
        if(!model.isMusicVolume()){
            batch.draw(settingsBtnTexture,model.getMusicPos().x-settingsBtnTexture.getWidth()/2f, model.getMusicPos().y);
        }
        batch.draw(mutegame,model.getToMenuPos().x-mutegame.getWidth()/2f, model.getToMenuPos().y);
        if(model.isMenuIsParent()){
            batch.draw(mutegame,model.getBackToGame().x-mutegame.getWidth()/2f, model.getBackToGame().y);
        }
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
        batch.dispose();
        gameMute.dispose();
        settingsBtnTexture.dispose();
    }
}