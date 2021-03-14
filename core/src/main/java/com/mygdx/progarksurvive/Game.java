package main.java.com.mygdx.progarksurvive.progarksurvive;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.controller.GameController;
import com.mygdx.progarksurvive.model.EntityModel;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	GameController gameController;
	EntityModel entityModel;

	@Override
	public void create () {
		entityModel = new EntityModel();
		gameController = new GameController(entityModel);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();

		gameController.render(entityModel, deltaTime);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
