package com.mygdx.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

public class DependencyRoot extends ApplicationAdapter {

    private Game game;
    private Test test;

    public DependencyRoot(Test test){
        this.test = test;
    }

    @Override
    public void create() {
        GameComponent gameComponent = DaggerGameComponent.create();
        game = gameComponent.gameFactory().create(test).main;
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
