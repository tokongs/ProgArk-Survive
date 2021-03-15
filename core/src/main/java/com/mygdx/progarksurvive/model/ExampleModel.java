package com.mygdx.progarksurvive.model;

import com.badlogic.gdx.math.Vector2;

public class ExampleModel {

    private Vector2 squarePosition;

    public ExampleModel(){
        squarePosition = new Vector2(100, 100);
    }

    public Vector2 getSquarePosition(){
        return squarePosition;
    }

    public void setSquarePosition(float x, float y) {
        squarePosition.x = x;
        squarePosition.y = y;
    }

}
