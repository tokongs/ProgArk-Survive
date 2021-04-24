package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ImageComponent implements Component {

    public Texture texture;
    public Vector2 size;

    public ImageComponent(Texture texture, Vector2 size){
        this.texture = texture;
        this.size = size;
    }

    public ImageComponent(Color color, Vector2 size){
        Pixmap pixmap = new Pixmap((int)size.x, (int)size.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, (int)size.x, (int)size.y);
        texture = new Texture(pixmap);
        pixmap.dispose();
        this.size = size;
    }
}
