package com.mygdx.progarksurvive;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

public class PlayServicesContainer {

    private PlayServices playServices;

    @AssistedInject
    public PlayServicesContainer(@Assisted PlayServices playServices){
        this.playServices = playServices;
    }

}
