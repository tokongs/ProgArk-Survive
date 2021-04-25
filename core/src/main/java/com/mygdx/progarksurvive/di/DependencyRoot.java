package com.mygdx.progarksurvive.di;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;

public class DependencyRoot extends ApplicationAdapter {

    private Game game;

    @Override
    public void create() {
        GameFactory gameFactory = DaggerGameFactory.create();
        game = gameFactory.game();
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
