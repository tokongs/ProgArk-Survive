package com.mygdx.progarksurvive;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

import javax.inject.Singleton;

public class GameContainer {
    Main main;
    Test test;

    @AssistedInject
    public GameContainer(Main main, @Assisted Test test){
        this.main = main;
        main.test = test;
        this.test = test;
    }
}
