package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.controller.SettingsController;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SettingsControllerTest {

    @Mock SettingsModel model;

    @BeforeAll
    static void setupBeforeAll(){
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getHeight()).thenReturn(1080);
        when(Gdx.graphics.getWidth()).thenReturn(768);
    }

    @BeforeEach
    void setup(){
        when(model.getBackToRect()).thenReturn(new Rectangle(0,0,100,100));
        when(model.getGameRect()).thenReturn(new Rectangle(100, 0, 100, 100));
        when(model.getMusicRect()).thenReturn(new Rectangle(200,0,100,100));
        when(model.getToMenuRect()).thenReturn(new Rectangle(300, 0, 100, 100));
        when(model.isGameVolume()).thenReturn(true);
        when(model.isMusicVolume()).thenReturn(true);
    }

    @AfterEach
    void tearDown(){
        reset(model);
    }

    @Test
    public void testClickOutsideButtons(@Mock Main game){
        SettingsController controller = new SettingsController(model, game);
        game.setState(GameState.SETTINGS);
        assertFalse(controller.touchDown(300, 400,0,0));
        verify(game, times(0)).setState(GameState.GAME);
        verify(game, times(0)).setState(GameState.MAIN_MENU);
        assertTrue(model.isMusicVolume());
        assertTrue(model.isGameVolume());
    }

    @Test
    public void testClickMainMenu(@Mock Main game){
        SettingsController controller = new SettingsController(model,game);
        game.setState(GameState.SETTINGS);
        assertFalse(controller.touchDown(300, 400,0,0));
        controller.touchDown(350, 50,0,0);
        verify(game, times(1)).setState(GameState.MAIN_MENU);
        verify(game,times(0)).setState(GameState.GAME);
        assertTrue(model.isMusicVolume());
        assertTrue(model.isGameVolume());
    }

    @Test
    public void testClickGameVolume(@Mock Main game){
        SettingsController controller = new SettingsController(model,game);
        game.setState(GameState.SETTINGS);
        assertFalse(controller.touchDown(300, 400,0,0));
        controller.touchDown(150, 50,0,0);
        verify(game, times(0)).setState(GameState.MAIN_MENU);
        verify(game,times(0)).setState(GameState.GAME);
        assertTrue(model.isMusicVolume());
        assertFalse(model.isGameVolume());
    }

    @Test
    public void testClickMusicVolume(@Mock Main game){
        SettingsController controller = new SettingsController(model,game);
        game.setState(GameState.SETTINGS);
        assertFalse(controller.touchDown(300, 400,0,0));
        controller.touchDown(250, 50,0,0);
        verify(game, times(0)).setState(GameState.MAIN_MENU);
        verify(game,times(0)).setState(GameState.GAME);
        assertFalse(model.isMusicVolume());
        assertTrue(model.isGameVolume());
    }












}
