package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.progarksurvive.model.GameModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {


    @BeforeAll
    static void setupBeforeAll(){
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(1080);
        when(Gdx.graphics.getHeight()).thenReturn(768);
    }

    @Test
    public void testUpdate(@Mock GameModel model){
        GameController controller = new GameController(model);
        controller.update(0.1f);
        verify(model, times(1)).update(0.1f);
    }

    @Test
    public void testChange(@Mock GameModel model, @Mock Touchpad actor, @Mock ChangeListener.ChangeEvent event){
        when(actor.getKnobPercentX()).thenReturn(1.0f);
        when(actor.getKnobPercentY()).thenReturn(0.5f);

        GameController controller = new GameController(model);
        controller.changed(event, actor);
        verify(model, times(1)).movePlayer(eq(new Vector2(1.0f, 0.5f)));
    }
}