package com.mygdx.progarksurvive;

import com.badlogic.gdx.Game;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = GameModule.class)
public interface GameFactory {
    com.badlogic.gdx.Game game();
}
