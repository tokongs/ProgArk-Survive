package com.mygdx.progarksurvive.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import main.java.com.mygdx.progarksurvive.progarksurvive.Game;

public class MainMenuScreen implements Screen {
    private SpriteBatch batch;
    private Game game;
    private BitmapFont font;
    //textures
    /*private Texture playBtnTexture;
    private Texture settingsBtnTexture;
    private Texture playBtnActiveTexture;
    private Texture settingsBtnActiveTexture;

    sprites
    private Sprite playBtn;
    private Sprite settingsBtn;
     */

    public MainMenuScreen(Game game){
        this.game = game;
        this.batch = game.getBatch();

        font = new BitmapFont();
        //playBtnTexture = new Texture("playbtn.png");
        //playBtnActiveTexture = new Texture("playbtnactive.png");
        //settingsBtnTexture = new Texture();
        //settingsBtnActiveTexture = new Texture();

        //playBtn = new Sprite(playBtnTexture);
        //settingsBtn = new Sprite(settingsBtnTexture);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "PLAY", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //batch.draw(playBtn, Gdx.graphics.getWidth()/2 - playBtn.getWidth()/2, Gdx.graphics.getHeight()/2);
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
    public void show() {
    }

    @Override
    public void dispose() {
        /*playBtnActiveTexture.dispose();
        playBtnTexture.dispose();
        settingsBtnTexture.dispose();
        settingsBtnActiveTexture.dispose();

         */
    }
}
