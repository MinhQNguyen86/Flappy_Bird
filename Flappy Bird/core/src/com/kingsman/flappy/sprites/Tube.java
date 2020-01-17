package com.kingsman.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

public class Tube {

    public static int X_SPACE_PAIRS = 140;
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    private int botArea = LOWEST_OPENING + TUBE_GAP;
    private Texture topTube, botTube;
    private Vector2 topTubePos, botTubePos;
    private Rectangle topBound, botBound;
    private Queue<Tube> tubes;

    public Tube(float xpos) {
        topTube = new Texture("toptube.png");
        botTube = new Texture("bottomtube.png");

        topTubePos = new Vector2(xpos, MathUtils.random(botArea, FLUCTUATION + botArea));
        botTubePos = new Vector2(xpos, topTubePos.y - TUBE_GAP - botTube.getHeight());

        topBound = new Rectangle(topTubePos.x, topTubePos.y, topTube.getWidth(), topTube.getHeight());
        botBound = new Rectangle(botTubePos.x, botTubePos.y, botTube.getWidth(), botTube.getHeight());
    }

    public Tube reposition(float xpos) {
        // when tube offscreen, move to end of line
        topTubePos.set(xpos, MathUtils.random(botArea, FLUCTUATION + botArea));
        botTubePos.set(xpos, topTubePos.y - TUBE_GAP - botTube.getHeight());

        topBound.setPosition(topTubePos.x, topTubePos.y);
        botBound.setPosition(botTubePos.x, botTubePos.y);

        return this;
    }

    public Texture getTopTube() {
        return topTube;
    }
    public Texture getBotTube() {
        return botTube;
    }
    public Vector2 getTopTubePos() {
        return topTubePos;
    }
    public Vector2 getBotTubePos() {
        return botTubePos;
    }
    public Rectangle getTopBound() {
        return topBound;
    }
    public Rectangle getBotBound() {
        return botBound;
    }

    public void dispose() {
        topTube.dispose();
        botTube.dispose();
    }

}
