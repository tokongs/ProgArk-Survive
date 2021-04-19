package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ImageComponent implements Component {

    public Texture texture;
    public Vector2 size;
    public ImageComponent(Texture texture, Vector2 size){
        this.texture = texture;
        this.size = size;
    }
}
