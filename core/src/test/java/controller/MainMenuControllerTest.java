package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryo.Kryo;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.controller.MainMenuController;
import com.mygdx.progarksurvive.model.MainMenuModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainMenuControllerTest {

    @Mock MainMenuModel model;

    @BeforeAll
    static void setupBeforeAll(){
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getHeight()).thenReturn(1080);
        when(Gdx.graphics.getHeight()).thenReturn(768);
    }

    @BeforeEach
    void setup(){
        when(model.getPlayBtnRect()).thenReturn(new Rectangle(0, 0, 100, 100));
        when(model.getSettingsBtnRect()).thenReturn(new Rectangle(100, 100, 100, 100));
    }

    @AfterEach
    void tearDown(){
        reset(model);
    }

    @Test
    public void testClickOutsideButtons(@Mock Main game){
        MainMenuController controller = new MainMenuController(model, game);
        assertFalse(controller.touchDown(300, 768 - 300, 0,0));
        verify(game, times(0)).setState(GameState.SETTINGS);
        verify(game, times(0)).setState(GameState.GAME);
    }

    @Test
    public void testClickPlayButton(@Mock Main game){
        MainMenuController controller = new MainMenuController(model, game);
        assertFalse(controller.touchDown(300, 768 - 300, 0,0));
        controller.touchDown(20, 768 - 20, 0, 0);
        verify(game, times(1)).setState(GameState.GAME);
        verify(game, times(0)).setState(GameState.SETTINGS);
    }

    @Test
    public void testClickSettingsButton(@Mock Main game) {
        MainMenuController controller = new MainMenuController(model, game);
        assertFalse(controller.touchDown(300, 768 - 300, 0,0));
        controller.touchDown(120, 768 - 120, 0, 0);
        verify(game, times(0)).setState(GameState.GAME);
        verify(game, times(1)).setState(GameState.SETTINGS);
    }

}
