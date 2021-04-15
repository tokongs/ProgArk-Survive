package com.mygdx.progarksurvive.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuModel {

    private Vector2 playBtnPosition;
    private Vector2 settingBtnPosition;
    private Rectangle playBtnRect;
    private Rectangle settingsBtnRect;

    @Inject
    public MainMenuModel(){
    }

    public void createRectangles(){
        float playBtnW = 77;
        float playBtnH = 29;
        playBtnPosition = new Vector2((float)Gdx.graphics.getWidth()/2 - playBtnW /2, (float)Gdx.graphics.getHeight()/2- playBtnH /2 + 30);
        float settingsBtnW = 162;
        float settingsBtnH = 29;
        settingBtnPosition = new Vector2((float)Gdx.graphics.getWidth()/2 - settingsBtnW /2, (float)Gdx.graphics.getHeight()/2 - settingsBtnH /2 - 30);

        playBtnRect = new Rectangle(playBtnPosition.x, playBtnPosition.y , playBtnW, playBtnH);
        settingsBtnRect = new Rectangle(settingBtnPosition.x, settingBtnPosition.y, settingsBtnW, settingsBtnH);
    }

    public Vector2 getPlayBtnPosition() {
        return playBtnPosition;
    }

    public Vector2 getSettingBtnPosition() {
        return settingBtnPosition;
    }

    public Rectangle getPlayBtnRect() {
        return playBtnRect;
    }

    public Rectangle getSettingsBtnRect() {
        return settingsBtnRect;
    }
}
