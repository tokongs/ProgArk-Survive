package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.Gdx;
import com.mygdx.progarksurvive.model.ExampleModel;
import main.java.com.mygdx.progarksurvive.progarksurvive.Game;

import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.W;

public class ExampleController {
    private Game game;
    private ExampleModel model;

    private float speed = 3;

    public ExampleController(Game game){
        this.game = game;
        this.model = game.getModel();
    }

    public void updatePosition(){
        if(Gdx.input.isKeyPressed(W)){
            model.setSquarePosition(model.getSquarePosition().x, model.getSquarePosition().y - speed);
        }
        if(Gdx.input.isKeyPressed(S)){
            model.setSquarePosition(model.getSquarePosition().x, model.getSquarePosition().y + speed);
        }
    }




}
