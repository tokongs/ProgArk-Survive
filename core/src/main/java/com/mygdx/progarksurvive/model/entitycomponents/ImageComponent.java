package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ImageComponent implements Component {

    public Sprite sprite;
    public Vector2 size;

    public ImageComponent(Texture texture, Vector2 size){
        this.sprite = new Sprite(texture);
        this.size = size;
        sprite.setSize(size.x, size.y);
        sprite.setOrigin(size.x/2, size.y/2);
    }

    public ImageComponent(Color color, Vector2 size){
        Pixmap pixmap = new Pixmap((int)size.x, (int)size.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, (int)size.x, (int)size.y);
        sprite = new Sprite(new Texture(pixmap));
        pixmap.dispose();
        this.size = size;
        sprite.setOrigin(size.x/2, size.y/2);
    }

    public void setTexture(Texture texture){
        sprite.setTexture(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOrigin(size.x/2, size.y/2);
    }
}
