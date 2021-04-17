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

    private Texture toMenu;
    private Texture gameMute;
    private Texture musicMute;
    private Texture gameUnmute;
    private Texture backToGame;
    private Texture musicUnmute;


    @Inject
    public SettingsScreen(SettingsModel settingsModel, SettingsController settingsController){
        model = settingsModel;
        controller = settingsController;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        gameMute = new Texture("MUTESOUNDEFFECTS_W345_H28.png");
        gameUnmute = new Texture("UNMUTESOUNDEFFECTS_W345_H28.png");
        musicMute = new Texture("MUTEMUSIC_W166_H28.png");
        musicUnmute = new Texture("UNMUTEMUSIC_W166_H28.png");
        toMenu = new Texture("MAINMENU_W217_H28.png");
        backToGame = new Texture("BACKTOGAME_W151_H81.png");
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0,0,0,1);
        batch.begin();
        if(model.isGameVolume()){
            batch.draw(gameMute,model.getGamePos().x, model.getGamePos().y);
        }
        if(!model.isGameVolume()){
            batch.draw(gameUnmute,model.getGamePos().x, model.getGamePos().y);
        }
        if(model.isMusicVolume()){
            batch.draw(musicMute,model.getMusicPos().x, model.getMusicPos().y);
        }
        if(!model.isMusicVolume()){
            batch.draw(musicUnmute,model.getMusicPos().x, model.getMusicPos().y);
        }
        batch.draw(toMenu,model.getToMenuPos().x, model.getToMenuPos().y);
        if(model.isMenuIsParent()){
            batch.draw(backToGame,model.getBackToGame().x, model.getBackToGame().y);
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
        gameUnmute.dispose();
    }
}