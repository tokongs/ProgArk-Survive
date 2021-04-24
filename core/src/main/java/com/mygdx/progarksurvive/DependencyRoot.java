package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

public class DependencyRoot extends ApplicationAdapter {

    private Game game;

    private PlayServices playServices;

    public DependencyRoot(PlayServices playServices){
        this.playServices = playServices;
    }

    @Override
    public void create() {
        GameComponent gameComponent = DaggerGameComponent.create();
        game = gameComponent.gameContainerFactory().create(playServices).main;
        game.create();

    }

    @Override
    public void render() {
        game.render();
    }

    @Override
    public void dispose() {
        game.dispose();
    }

    
}
