package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class WorldManager {
    private SpriteBatch spriteBatch;
    private boolean scrollPipes = false, scrollGround = true;
    private final Texture ground,topPipe,bottomPipe,bg;
    private final Vector2 groundPos;
    private Vector2 topPipePos1;
    private final Vector2 bottomPipePos1;
    private final Vector2 bgPos;
    private final int width,height;
    private int pipeGap = 20;
    float groundSpeed = 100;

    public WorldManager(SpriteBatch spriteBatch, int height, int width) {
        this.spriteBatch = spriteBatch;
        this.height = height;
        this.width = width;
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        topPipe = new Texture("pipe1.png");
        bottomPipe = new Texture("pipe2.png");
        topPipePos1 = new Vector2(width, getRandomTopPipeHeight());
        bottomPipePos1 = new Vector2(width, topPipePos1.y - bottomPipe.getHeight() - pipeGap);
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
        if (birdPos.y < ground.getHeight() + (birdHeight / 3)) {
            collision = true;
        }

        // Top pipe detection
        if (birdPos.y > topPipePos1.y - birdHeight) {
            if (birdPos.x > topPipePos1.x - birdWidth && birdPos.x < topPipePos1.x + topPipe.getWidth() - birdWidth/2) {
                collision = true;
            }
        }
        return collision;
    }

    public void render() {
        spriteBatch.draw(bg, bgPos.x, bgPos.y);
        spriteBatch.draw(bg, bgPos.x + bg.getWidth(), bgPos.y); //Draw two times to animate it
        spriteBatch.draw(topPipe, topPipePos1.x, topPipePos1.y);
        spriteBatch.draw(bottomPipe, bottomPipePos1.x, bottomPipePos1.y);
        spriteBatch.draw(ground, groundPos.x, groundPos.y);

        if (scrollGround) {
            groundPos.x -= groundSpeed * Gdx.graphics.getDeltaTime();
            if (groundPos.x < -50) {
                groundPos.x = 0;
            }
        }

        if (scrollPipes) {
            topPipePos1.x -= groundSpeed * Gdx.graphics.getDeltaTime();
            if (topPipePos1.x < (0 - topPipe.getWidth())) {
                topPipePos1.x = width;
                topPipePos1.y = getRandomTopPipeHeight();
                bottomPipePos1.y = topPipePos1.y - pipeGap;
            }
            bottomPipePos1.x -= groundSpeed * Gdx.graphics.getDeltaTime();
        }
    }

    public void dispose() {
        bg.dispose();
        ground.dispose();
        bottomPipe.dispose();
        topPipe.dispose();
    }

    public void resetPipePositions() {
        topPipePos1 = new Vector2(width, getRandomTopPipeHeight());
    }
}
