package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component{
    public int health;
    public HealthComponent() {}
    public HealthComponent(int health){
        this.health = health;
    }
}
