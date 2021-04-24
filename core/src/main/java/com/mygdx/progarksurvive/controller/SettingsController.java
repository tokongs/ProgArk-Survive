package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


import com.mygdx.progarksurvive.DependencyRoot;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.PlayServices;
import com.mygdx.progarksurvive.model.SettingsModel;











import javax.inject.Inject;
import javax.inject.Singleton;



import static com.badlogic.gdx.Input.Keys.R;

@Singleton
public class SettingsController implements InputProcessor {
    private final SettingsModel model;
    private final Main game;
    
    @Inject
    public SettingsController(SettingsModel settingsModel, Main game){
        this.model = settingsModel;
        this.game = game;
        model.setGameVolume(game.getPrefs().hasSound());
        model.setMusicVolume(game.getPrefs().hasMusic());
        model.setMenuIsParent(game.isGameRunning());
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
        if(model.getGameRect().contains(screenX, y)){
            System.out.println("MUTE GAME");
            game.getPrefs().setSound(!game.getPrefs().hasSound());
            model.setGameVolume(!model.isGameVolume());
        }
        else if(model.getMusicRect().contains(screenX, y)){
            System.out.println("MUTE MUSIC");
            game.getPrefs().setMusic(!game.getPrefs().hasMusic());
            model.setMusicVolume(!model.isMusicVolume());
        }
        else if(model.getToMenuRect().contains(screenX, y)){
            game.setState(GameState.MAIN_MENU);
        }
        else if(model.getBackToRect().contains(screenX, y)){
            if(game.isGameRunning()){
                System.out.println("TO THE GAME");
                game.setState(GameState.GAME);
            }

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
