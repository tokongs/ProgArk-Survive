package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    private float x = 0.0f;
    private float y = 0.0f;

    public void changePos(float x, float y){
        this.x += x;
        this.y += y;
    }

    public void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }
}
