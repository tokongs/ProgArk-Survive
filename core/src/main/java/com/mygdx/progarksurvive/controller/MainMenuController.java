package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.model.MainMenuModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuController implements InputProcessor {
    private final MainMenuModel model;
    private final Main game;

    @Inject
    public MainMenuController(MainMenuModel mainMenuModel, Main game){
        model = mainMenuModel;
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        if(model.getPlayBtnRect().contains(screenX, y)){
            game.setState(GameState.NETWORKING);
        }
        else if(model.getSettingsBtnRect().contains(screenX, y)){
            game.setState(GameState.SETTINGS);
        }
        else if(model.getHighscoreRect().contains(screenX, y)){
            game.playServices.signIn();
            game.playServices.showLeaderboard("CgkIpbOPqecIEAIQAQ");
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}