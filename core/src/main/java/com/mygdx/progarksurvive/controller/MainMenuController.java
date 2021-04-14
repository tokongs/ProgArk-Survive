package com.mygdx.progarksurvive.controller;

import com.mygdx.progarksurvive.model.MainMenuModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainMenuController {
    private final MainMenuModel model;

    @Inject
    public MainMenuController(MainMenuModel model){
        this.model = model;


    }

    public void handleInput(){
        
    }


}