package com.kingsman.flappy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kingsman.flappy.FlappyBird;


public class Bird {
    public Vector3 position;
    private Vector3 velocity;
    private static final float GRAVITY = -15f;
    private int movement = 100;
    private Rectangle bound;
    private Sound flap;
    public Animation<TextureRegion> flapAnimation;
    private Texture flapSheet;
    private float elapsedTime = 0f;

    public Bird (int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        flapSheet = new Texture("birdanimation.png");
        bound = new Rectangle(x, y, flapSheet.getWidth() / 3f, flapSheet.getHeight() - 20); // hat
        // Animation Frames
        TextureRegion[][] tmp = TextureRegion.split(flapSheet, flapSheet.getWidth() / 3, flapSheet.getHeight());
        TextureRegion[] flapFrames = tmp[0];
        flapAnimation = new Animation<TextureRegion>(1f/3f, flapFrames);

    }

    public void speedUp(int speed) {
        movement += speed;
    }

    public void jump() {
        velocity.y = 250;
        flap.play(0.1f);
    }

    public boolean collides(Tube t) {
        return getBound().overlaps(t.getTopBound()) || getBound().overlaps(t.getBotBound());
    }

    public void update(float dt) {
        if (position.y > 40)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(movement * dt, velocity.y, 0);
        if (position.y <= 40)
            position.y = 40;
        velocity.scl(1/dt);
        // when bird moves so should rectangle
        bound.setPosition(position.x, position.y);
    }

    public void render(float dt, FlappyBird g) {
        elapsedTime += dt;
        g.batch.draw(flapAnimation.getKeyFrame(elapsedTime, true), this.getPosition().x, this.getPosition().y);
    }

    public Vector3 getPosition() {
        return position;
    }
    public Rectangle getBound() {
        return bound;
    }

    public void dispose() {
        flap.dispose();
        flapSheet.dispose();
    }

}
