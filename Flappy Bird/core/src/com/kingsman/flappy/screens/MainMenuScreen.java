package com.kingsman.flappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.kingsman.flappy.FlappyBird;

public class MainMenuScreen implements Screen {

    final FlappyBird g;
    private OrthographicCamera cam;
    private Texture bg;
    private Texture playbtn;

    public MainMenuScreen(FlappyBird game) {
        g = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, FlappyBird.V_WIDTH, FlappyBird.V_HEIGHT);

        // create texture
        bg = new Texture("bg2.png");
        playbtn = new Texture("playbtn.png");
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        g.batch.setProjectionMatrix(cam.combined);

        g.batch.begin();
        g.batch.draw(bg,0,0, FlappyBird.V_WIDTH, FlappyBird.V_HEIGHT);
        g.batch.draw(playbtn, cam.position.x - playbtn.getWidth() / 2, cam.position.y);
        g.batch.end();

        handleInput();
    }

    public void handleInput() {
        // Exits main menu, enters GameScreen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            g.setScreen(new GameScreen(g));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        playbtn.dispose();
        bg.dispose();
        System.out.println("Main menu disposed");
    }
}
