package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    private float health = 100.0f;

    public void damage(float damage){
        this.health -= damage;
    }

    public Float getHealth(){
        return this.health;
    }
}
