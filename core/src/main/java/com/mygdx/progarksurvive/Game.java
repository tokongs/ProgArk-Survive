package main.java.com.mygdx.progarksurvive.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.controller.ExampleController;
import com.mygdx.progarksurvive.model.ExampleModel;
import com.mygdx.progarksurvive.screen.ExampleScreen;

public class Game extends ApplicationAdapter {
	ExampleScreen exScreen;
	ExampleModel exModel;
	ExampleController exController;

	@Override
	public void create () {
		exModel = new ExampleModel();
		exController = new ExampleController(this);
		exScreen = new ExampleScreen(this);
	}

	@Override
	public void render () {
		exController.updatePosition();
		Gdx.input.setOnscreenKeyboardVisible(false);
		exScreen.render(1);
	}
	
	@Override
	public void dispose () {
		exScreen.dispose();
	}

	public ExampleScreen getScreen() {
		return exScreen;
	}

	public ExampleModel getModel() {
		return exModel;
	}

	public ExampleController getController() {
		return exController;
	}
}
