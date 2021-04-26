package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.model.GameModel;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameController extends ChangeListener {

    private final GameModel model;
    @Inject
    public GameController(GameModel model) {
        this.model = model;
    }

    public void update(float delta) {
        model.update(delta);
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        Touchpad touchpad = (Touchpad) actor;
        Vector2 direction = new Vector2(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        model.movePlayer(direction);
    }
}
