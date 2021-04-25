package com.mygdx.progarksurvive.di;

import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.PlayServices;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

public class GameContainer {
    Main main;
    PlayServices playServices;

    @AssistedInject
    public GameContainer(Main main, @Assisted PlayServices playServices){
        this.main = main;
        main.playServices = playServices;
        this.playServices = playServices;
    }
}
