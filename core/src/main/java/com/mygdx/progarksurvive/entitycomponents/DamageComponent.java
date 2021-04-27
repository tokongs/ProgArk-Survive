package com.mygdx.progarksurvive.entitycomponents;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {
    public int damage;
    public int speed;

    public DamageComponent(int damage, int speed){
        this.damage = damage;
        this.speed = speed;
    }
}
