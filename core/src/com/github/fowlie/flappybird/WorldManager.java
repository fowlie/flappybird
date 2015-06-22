package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.github.fowlie.flappybird.Resources.*;

public class WorldManager {
    private boolean scrollPipes = false, scrollGround = true;
    private final Vector2 groundPos;
    private Vector2 topPipePos1;
    private final Vector2 bottomPipePos1;
    private final Vector2 bgPos;
    private final int width,height;
    private int pipeGap = 20;
    float groundSpeed = 100;

    public WorldManager(int height, int width) {
        this.height = height;
        this.width = width;
        topPipePos1 = new Vector2(width, getRandomTopPipeHeight());
        bottomPipePos1 = new Vector2(width, topPipePos1.y - TEXTURE_PIPE_BOTTOM.getHeight() - pipeGap);
        bgPos = new Vector2(0, 57);
        groundPos = new Vector2(0, 0);
    }

    public void enableScrolling() {
        scrollPipes = true;
        scrollGround = true;
    }

    public void stopScrolling() {
        scrollPipes = false;
        scrollGround = false;
    }

    public int getRandomTopPipeHeight() {
        int random = (int) (Math.random() * height/3) + (height / 2);
        return random;
    }

    public boolean collisionDetection(Vector2 birdPos, int birdHeight, int birdWidth) {
        boolean collision = false;
        // Ground detection
        if (birdPos.y < TEXTURE_BACKGROUND.getHeight() + (birdHeight / 3)) {
            collision = true;
        }

        // Top pipe detection
        if (birdPos.y > topPipePos1.y - birdHeight) {
            if (birdPos.x > topPipePos1.x - birdWidth && birdPos.x < topPipePos1.x + TEXTURE_PIPE_TOP.getWidth() - birdWidth/2) {
                collision = true;
            }
        }
        return collision;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(TEXTURE_BACKGROUND, bgPos.x, bgPos.y);
        spriteBatch.draw(TEXTURE_BACKGROUND, bgPos.x + TEXTURE_BACKGROUND.getWidth(), bgPos.y); //Draw two times to animate it
        spriteBatch.draw(TEXTURE_PIPE_TOP, topPipePos1.x, topPipePos1.y);
        spriteBatch.draw(TEXTURE_PIPE_BOTTOM, bottomPipePos1.x, bottomPipePos1.y);
        spriteBatch.draw(TEXTURE_GROUND, groundPos.x, groundPos.y);

        if (scrollGround) scrollGround();
        if (scrollPipes) scrollPipes();
    }

    private void scrollGround() {
        groundPos.x -= groundSpeed * Gdx.graphics.getDeltaTime();
        if (groundPos.x < -50) {
            groundPos.x = 0;
        }
    }

    private void scrollPipes() {
        topPipePos1.x -= groundSpeed * Gdx.graphics.getDeltaTime();
        if (topPipePos1.x < (0 - TEXTURE_PIPE_TOP.getWidth())) {
            topPipePos1.x = width;
            topPipePos1.y = getRandomTopPipeHeight();
            bottomPipePos1.y = topPipePos1.y - pipeGap;
        }
        bottomPipePos1.x -= groundSpeed * Gdx.graphics.getDeltaTime();
    }

    public void resetPipePositions() {
        topPipePos1 = new Vector2(width, getRandomTopPipeHeight());
    }
}
