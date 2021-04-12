package main.java.com.mygdx.progarksurvive.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.controller.ExampleController;
import com.mygdx.progarksurvive.model.ExampleModel;
import com.mygdx.progarksurvive.screen.ExampleScreen;
import com.mygdx.progarksurvive.screen.MainMenuScreen;

public class Game extends ApplicationAdapter {
	//variables for the entire game
	private	SpriteBatch batch;

	//variables for MainMenu
	private MainMenuScreen mainmenuscreen;

	//variables for example
	private ExampleScreen exScreen;
	private ExampleModel exModel;
	private ExampleController exController;

	@Override
	public void create () {
		batch = new SpriteBatch();

		//create example objects
		exModel = new ExampleModel();
		exController = new ExampleController(this);
		exScreen = new ExampleScreen(this);

		//create mainmenu objects
		mainmenuscreen = new MainMenuScreen(this);
	}

	@Override
	public void render () {
		exController.updatePosition();
		mainmenuscreen.render(1);
	}
	
	@Override
	public void dispose () {
		exScreen.dispose();
		mainmenuscreen.dispose();
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

	public SpriteBatch getBatch(){ return batch; }
}
