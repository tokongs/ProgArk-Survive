package com.mygdx.progarksurvive.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.Prefs;
import com.mygdx.progarksurvive.model.SettingsModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SettingsControllerTest {

    @Mock SettingsModel model;
    @Mock Main game;
    @Mock Prefs prefs;


    @BeforeAll
    static void setupBeforeAll(){
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getHeight()).thenReturn(768);
        when(Gdx.graphics.getWidth()).thenReturn(1080);
    }

    @BeforeEach
    void setup(){
        when(model.getBackToRect()).thenReturn(new Rectangle(0,0,100,100));
        when(model.getGameRect()).thenReturn(new Rectangle(100, 0, 100, 100));
        when(model.getMusicRect()).thenReturn(new Rectangle(200,0,100,100));
        when(model.getToMenuRect()).thenReturn(new Rectangle(300, 0, 100, 100));
        when(game.getPrefs()).thenReturn(prefs);
    }

    @AfterEach
    void tearDown(){
        reset(model);
        reset(game);
    }

    @Test
    public void testClickOutsideButtons(){
        SettingsController controller = new SettingsController(model, game);
        // Stuff get's called in the constructor, but we don't want to verify any of that
        // so mocks have to be reset
        tearDown();
        setup();
        assertFalse(controller.touchDown(500, 200,0,0));
        verify(game, times(0)).setState(GameState.GAME);
        verify(prefs, times(0)).setSound(anyBoolean());
        verify(model, times(0)).setGameVolume(anyBoolean());
        verify(prefs, times(0)).setMusic(anyBoolean());
        verify(model, times(0)).setMusicVolume(anyBoolean());
    }

    @Test
    public void testClickMainMenu(){
        SettingsController controller = new SettingsController(model,game);
        assertFalse(controller.touchDown(350, 768 - 50,0,0));
        verify(game, times(1)).setState(GameState.MAIN_MENU);
    }

    @Test
    public void testClickGameVolumeOff(){
        when(prefs.hasSound()).thenReturn(true);
        when(model.isGameVolume()).thenReturn(true);
        SettingsController controller = new SettingsController(model,game);
        assertFalse(controller.touchDown(150, 768 - 50,0,0));
        verify(prefs, times(1)).setSound(false);
        verify(model, times(1)).setGameVolume(false);
    }


    @Test
    public void testClickGameVolumeOn(){
        when(prefs.hasSound()).thenReturn(false);
        when(model.isGameVolume()).thenReturn(false);
        SettingsController controller = new SettingsController(model,game);
        assertFalse(controller.touchDown(150, 768 - 50,0,0));
        verify(prefs, times(1)).setSound(true);
        verify(model, times(1)).setGameVolume(true);
    }

    @Test
    public void testClickMusicVolumeOff(){
        when(prefs.hasMusic()).thenReturn(true);
        when(model.isMusicVolume()).thenReturn(true);
        SettingsController controller = new SettingsController(model,game);
        assertFalse(controller.touchDown(250, 768 - 50,0,0));
        verify(prefs, times(1)).setMusic(false);
        verify(model, times(1)).setMusicVolume(false);
    }

    @Test
    public void testClickMusicVolumeOn(){
        when(prefs.hasMusic()).thenReturn(false);
        when(model.isMusicVolume()).thenReturn(false);
        SettingsController controller = new SettingsController(model,game);
        assertFalse(controller.touchDown(250, 768 - 50,0,0));
        verify(prefs, times(1)).setMusic(true);
        verify(model, times(1)).setMusicVolume(true);
    }

}
