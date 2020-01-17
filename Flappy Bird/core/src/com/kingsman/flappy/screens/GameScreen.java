package com.kingsman.flappy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kingsman.flappy.FlappyBird;
import com.kingsman.flappy.sprites.Bird;
import com.kingsman.flappy.sprites.Tube;
import com.kingsman.flappy.sprites.Tubes;


public class GameScreen implements Screen {

    private static final int GROUND_OFFSET = -70;
    private OrthographicCamera cam;
    private OrthographicCamera guicam;
    private Texture ground;
    private Texture bg;
    private Music ambient;
    private Tubes tubes;
    private Bird bird;
    private int score = 0;
    private Vector2 groundPos1, groundPos2;
    final FlappyBird g;

    public GameScreen(FlappyBird g) {
        this.g = g;

        // set camera
        cam = new OrthographicCamera();
        cam.setToOrtho(false, FlappyBird.V_WIDTH / 2, FlappyBird.V_HEIGHT / 2);
        guicam = new OrthographicCamera();
        guicam.setToOrtho(false, FlappyBird.V_WIDTH / 2, FlappyBird.V_HEIGHT / 2);

        // load assets
        ground = new Texture("ground3.png");
        bg = new Texture("bg2.png");
        ambient = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        // Set grounds back to back
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_OFFSET);

        tubes = new Tubes();
        bird = new Bird(34,200);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
    }

    @Override
    public void show() {
        ambient.setVolume(0.25f);
        ambient.setLooping(true);
        ambient.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.position.x = bird.getPosition().x + 80;
        cam.update();

        handleInput();
        updateGround();
        updateTubes();

        g.batch.begin();
        g.batch.setProjectionMatrix(cam.combined);
        g.batch.draw(bg, cam.position.x - (cam.viewportWidth) / 2, 0, cam.viewportWidth, cam.viewportHeight);
        // Bird
        bird.render(delta, g);
        bird.update(delta);
        // Tubes
        for (Tube t : tubes.getTubes()) {
            g.batch.draw(t.getTopTube(), t.getTopTubePos().x, t.getTopTubePos().y);
            g.batch.draw(t.getBotTube(), t.getBotTubePos().x, t.getBotTubePos().y);
            if (bird.collides(t)) {
                System.out.println("Collision!");
                g.setScreen(new MainMenuScreen(g));
                dispose();
                break;
            }
        }
        // Ground
        g.batch.draw(ground, groundPos1.x, groundPos1.y);
        g.batch.draw(ground, groundPos2.x, groundPos2.y);
        // Gui
        guicam.update();
        g.batch.setProjectionMatrix(guicam.combined);
        g.font.draw(g.batch, "Score: " + score, 10, cam.viewportHeight - 10);
        g.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) bird.jump();
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

    private void updateTubes() {
        // Reposition first Tube offscreen then add to end of queue
        if (cam.position.x - cam.viewportWidth / 2 > tubes.first().getTopTubePos().x + Tube.TUBE_WIDTH) {
            tubes.addLast(tubes.removeFirst().reposition(tubes.last().getTopTubePos().x + Tube.TUBE_WIDTH + Tube.X_SPACE_PAIRS));
            score++;
            // Increase difficulty
            if (score % 5 == 0 && score <= 20) bird.speedUp(15);
            if (score == 10) Tube.X_SPACE_PAIRS -= 15;
        }
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
        g.setScreen(new GameScreen(g));
        tubes.dispose();
        bird.dispose();
        ambient.dispose();
        ground.dispose();
        bg.dispose();
        System.out.println("Game screen disposed");
    }
}
