package com.mygdx.progarksurvive.model.entitycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.progarksurvive.model.entitysystems.AnimationSystem;

import java.util.List;

public class AnimationComponent implements Component {

    public List<Texture> textures;

    public float timePerTexture;
    public float timePast = 0;
    public int textureIndex = 0;
    public int defaultTexture;

    public AnimationComponent(float timePerTexture, List<Texture> textures, int defaultTexture){
        this.timePerTexture = timePerTexture;
        this.textures = textures;
        this.defaultTexture = defaultTexture;
    }

}
