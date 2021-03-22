package com.mygdx.progarksurvive;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = GameModule.class)
public interface GameFactory {
    Game game();
}
