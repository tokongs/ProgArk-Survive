package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class ImageComponent implements Component {

    public Texture texture;

    public ImageComponent(Texture texture){
        this.texture = texture;
    }
}
