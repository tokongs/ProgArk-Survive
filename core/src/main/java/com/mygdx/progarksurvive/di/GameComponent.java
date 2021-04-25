package com.mygdx.progarksurvive.di;

import dagger.Component;


import javax.inject.Singleton;

@Singleton
@Component(modules = GameModule.class)
public interface GameComponent {
    GameContainerFactory gameContainerFactory();
}
