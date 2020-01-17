package com.kingsman.flappy;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kingsman.flappy.screens.MainMenuScreen;


public class FlappyBird extends Game {

	public static final int V_WIDTH = 460;
	public static final int V_HEIGHT = 820;
	public static final String TITLE = "Flappy Bird";

	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () { super.render(); }
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		this.getScreen().dispose();
	}
}
