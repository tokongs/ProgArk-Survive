package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.progarksurvive.controller.ExampleController;
import com.mygdx.progarksurvive.model.ExampleModel;

public class ExampleScreen implements Screen {
    private ExampleController controller;
    private ExampleModel model;
    private SpriteBatch batch;

    private Texture texture;
    private Sprite sprite;

    public ExampleScreen(ExampleController controller, ExampleModel model){
        this.controller = controller;
        this.model = model;

        batch = new SpriteBatch();

        texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(sprite, model.getSquarePosition().x, model.getSquarePosition().y);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
