package com.mygdx.progarksurvive;

import com.mygdx.progarksurvive.networking.NetworkModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = NetworkModule.class)
public interface GameFactory {
    Game game();
}
