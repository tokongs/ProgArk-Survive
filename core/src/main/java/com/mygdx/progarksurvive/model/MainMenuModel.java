package com.mygdx.progarksurvive.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuModel {

    private final Vector2 playBtnPosition;
    private final Vector2 settingBtnPosition;
    private final Vector2 highscorePos;
    private Rectangle playBtnRect;
    private Rectangle settingsBtnRect;
    private Rectangle highscoreRect;

    private final float playBtnW = 84;
    private final float playBtnH = 28;
    private final float settingsBtnW = 173;
    private final float settingsBtnH = 28;
    private final float highscorBtnW = 221;

    @Inject
    public MainMenuModel(){
        settingBtnPosition = new Vector2((float)Gdx.graphics.getWidth()/2 - settingsBtnW /2, (float)Gdx.graphics.getHeight()/2 - settingsBtnH /2 - 90);
        playBtnPosition = new Vector2((float)Gdx.graphics.getWidth()/2 - playBtnW /2, (float)Gdx.graphics.getHeight()/2- playBtnH /2 + 30);
        highscorePos = new Vector2((float)Gdx.graphics.getWidth()/2 - highscorBtnW /2, (float)Gdx.graphics.getHeight()/2- playBtnH /2 - 30);
        playBtnRect = createPlayBtnRect();
        settingsBtnRect = createSettingsBtnRect();
        highscoreRect = new Rectangle(highscorePos.x, highscorePos.y, highscorBtnW, settingsBtnH);

    }

    public Rectangle createSettingsBtnRect(){
        settingsBtnRect = new Rectangle(settingBtnPosition.x, settingBtnPosition.y, settingsBtnW, settingsBtnH);
        return settingsBtnRect;
    }

    public Rectangle createPlayBtnRect(){
        playBtnRect = new Rectangle(playBtnPosition.x, playBtnPosition.y , playBtnW, playBtnH);
        return playBtnRect;
    }

    public Vector2 getPlayBtnPosition() {
        return playBtnPosition;
    }

    public Vector2 getSettingBtnPosition() {
        return settingBtnPosition;
    }

    public Vector2 getHighscorePos() {
        return highscorePos;
    }

    public Rectangle getPlayBtnRect() {
        return playBtnRect;
    }

    public Rectangle getSettingsBtnRect() {
        return settingsBtnRect;
    }


    public Rectangle getHighscoreRect() {
        return highscoreRect;
    }
}
