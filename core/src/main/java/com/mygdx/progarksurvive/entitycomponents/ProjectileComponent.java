package com.mygdx.progarksurvive.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class ProjectileComponent implements Component {
    public int damage;
    public Entity shooter;

    public ProjectileComponent(int damage, Entity shooter){
        this.damage = damage;
        this.shooter = shooter;
    }
}
