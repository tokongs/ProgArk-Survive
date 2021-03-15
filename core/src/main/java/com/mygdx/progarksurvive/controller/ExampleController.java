package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.Gdx;
import com.mygdx.progarksurvive.model.ExampleModel;
import main.java.com.mygdx.progarksurvive.progarksurvive.Game;

import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.W;

public class ExampleController {
    private Game game;


    private float speed = 8;

    public ExampleController(Game game){
        this.game = game;

    }

    public void updatePosition(){
        if(Gdx.input.isKeyPressed(W)){
            game.getModel().setSquarePosition(game.getModel().getSquarePosition().x, game.getModel().getSquarePosition().y + speed);

        }

        else if(Gdx.input.isKeyPressed(S)){
            game.getModel().setSquarePosition(game.getModel().getSquarePosition().x, game.getModel().getSquarePosition().y - speed);
        }
    }




}
