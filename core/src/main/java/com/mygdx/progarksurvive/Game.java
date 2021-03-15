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




	@Override
	public void create () {
		exScreen = new ExampleScreen(new ExampleController(), new ExampleModel());
	}

	@Override
	public void render () {
		exScreen.render(1);
	}
	
	@Override
	public void dispose () {
		exScreen.dispose();
	}
}
