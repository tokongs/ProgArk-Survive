package controller;

import com.badlogic.gdx.Gdx;
import com.mygdx.progarksurvive.GameState;
import com.mygdx.progarksurvive.Main;
import com.mygdx.progarksurvive.controller.MainMenuController;
import com.mygdx.progarksurvive.model.MainMenuModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainMenuControllerTest {

    @Test
    public void testButtons(@Mock MainMenuModel mockModel, @Mock Main mockMain){
        MainMenuController testController = new MainMenuController(mockModel, mockMain);
        testController.touchDown(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 30, 0, 0);
        verify(mockMain, times(1)).setState(GameState.GAME);

        testController.touchDown(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 -30, 0, 0);
        verify(mockMain, times(1)).setState(GameState.SETTINGS);

        testController.touchDown(130, 130, 0,0);
        verify(mockMain, times(0)).setState(GameState.SETTINGS);
        verify(mockMain, times(0)).setState(GameState.GAME);
    }

    @Test
    public void testPlayButton(@Mock MainMenuModel mockModel, @Mock Main mockMain){
        MainMenuController testController = new MainMenuController(mockModel, mockMain);
        testController.touchDown(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 +30,0,1);
        verify(mockMain, times(1)).setState(GameState.GAME);
    }

    @Test
    public void testSettingsButton(@Mock MainMenuModel mockModel, @Mock Main mockMain) {
        MainMenuController testController = new MainMenuController(mockModel, mockMain);
        testController.touchDown(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 -30, 0, 0);
        verify(mockMain, times(1)).setState(GameState.SETTINGS);
    }

}
