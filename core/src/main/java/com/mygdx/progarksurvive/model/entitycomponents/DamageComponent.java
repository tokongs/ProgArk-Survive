package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {
    private float damage = .1f;

    public void increaseDamage(float damage){
        this.damage += damage;
    }

    public void multiplyDamage(float damage){
        this.damage *= damage;
    }

    public void setDamage(float damage){
        this.damage = damage;
    }

    public float getDamage(){
        return damage;
    }
}
