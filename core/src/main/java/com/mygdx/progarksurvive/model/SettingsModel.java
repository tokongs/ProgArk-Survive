package com.mygdx.progarksurvive.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsModel {
    private boolean gameVolume;
    private boolean musicVolume;
    private boolean menuIsParent = false;
    private Vector2 gamePos;
    private Vector2 musicPos;
    private Vector2 toMenuPos;
    private Vector2 backToGame;
    private Rectangle gameRect;
    private Rectangle musicRect;
    private Rectangle toMenuRect;
    private Rectangle backToRect;

    @Inject
    public SettingsModel(){
        this.gameVolume = true;
        this.musicVolume = true;
        float playBtnW = 180;
        float playBtnH = 30;
        this.gamePos = new Vector2((float) Gdx.graphics.getWidth()/2, (float)Gdx.graphics.getHeight()/2- playBtnH /2 + 30);
        this.musicPos = new Vector2((float) Gdx.graphics.getWidth()/2 , (float)Gdx.graphics.getHeight()/2- playBtnH /2 - 30);
        this.toMenuPos = new Vector2((float) Gdx.graphics.getWidth()/2 , (float)Gdx.graphics.getHeight()/2- playBtnH /2 - 90);
        this.backToGame = new Vector2((float) Gdx.graphics.getWidth()/2 , (float)Gdx.graphics.getHeight()/2- playBtnH /2 - 150);



        this.gameRect = new Rectangle(gamePos.x-playBtnW/2f, gamePos.y , playBtnW, playBtnH);
        this.musicRect = new Rectangle(musicPos.x-playBtnW/2f, musicPos.y , playBtnW, playBtnH);
        this.toMenuRect = new Rectangle(toMenuPos.x-playBtnW/2f, toMenuPos.y , playBtnW, playBtnH);
        this.backToRect = new Rectangle(backToGame.x-playBtnW/2f, backToGame.y , playBtnW, playBtnH);
    }


    public Vector2 getGamePos(){return gamePos;}
    public Vector2 getMusicPos(){return musicPos;}
    public Vector2 getToMenuPos() {
        return toMenuPos;
    }

    public Vector2 getBackToGame() {
        return backToGame;
    }

    public Rectangle getGameRect() {
        return gameRect;
    }

    public Rectangle getMusicRect() {
        return musicRect;
    }

    public Rectangle getToMenuRect() {
        return toMenuRect;
    }

    public Rectangle getBackToRect() {
        return backToRect;
    }

    public boolean isGameVolume() {
        return gameVolume;
    }

    public void setGameVolume(boolean mute){
        this.gameVolume = mute;
    }

    public boolean isMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(boolean mute) {
        this.musicVolume = mute;
    }

    public boolean isMenuIsParent() {
        return menuIsParent;
    }

    public void setMenuIsParent(boolean menuIsParent) {
        this.menuIsParent = menuIsParent;
    }
}
