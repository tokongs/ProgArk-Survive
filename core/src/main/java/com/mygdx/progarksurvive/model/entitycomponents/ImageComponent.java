package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class ImageComponent implements Component {

    public Texture texture;
    public String path;

    public ImageComponent(String path){
        this.path = path;
    }
}
