package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import static com.github.fowlie.flappybird.Resources.*;

public class World {
    private Bird bird;
    private boolean scrollPipes = false, scrollGround = true, passedPipes = false;
    private final Vector2 groundPos;
    private Vector2 topPipePos1;
    private Vector2 bottomPipePos1;
    private final Vector2 bgPos;
    private int pipeGap = 40;
    float groundSpeed = 100;

    public World() {
        bird = new Bird(new Vector2(FlappyBird.WIDTH /4, FlappyBird.HEIGHT/2));
        topPipePos1 = new Vector2(FlappyBird.WIDTH, getRandomTopPipeHeight());
        bottomPipePos1 = getBottomPipePos();
        bgPos = new Vector2(0, 57);
        groundPos = new Vector2(0, 0);
    }

    public Bird getBird() {
        return bird;
    }

    private Vector2 getBottomPipePos() {
        return new Vector2(FlappyBird.WIDTH, topPipePos1.y - TEXTURE_PIPE_BOTTOM.getHeight() - pipeGap);
    }

    public Vector2 getTopPipePos() {
        return topPipePos1;
    }

    public boolean hasPassedPipes() {
        return passedPipes;
    }

    public void passedPipes() {
        passedPipes = true;
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
        Random random = new Random();
        int highest = FlappyBird.HEIGHT + (Resources.TEXTURE_PIPE_TOP.getHeight() / 2) - (FlappyBird.HEIGHT/ 10);
        int randomHeight = random.nextInt(highest - FlappyBird.HEIGHT) + FlappyBird.HEIGHT / 2;
        Gdx.app.log("World", "New top pipe height pos: " + randomHeight);
        return randomHeight;
    }

    public boolean collisionDetection(Vector2 birdPos, int birdHeight, int birdWidth) {
        boolean collision = false;
        // Ground detection
        if (birdPos.y < TEXTURE_GROUND.getHeight() + (birdHeight / 3)) {
            collision = true;
            Gdx.app.log("Physics", "Collided with the ground");
        }

        // Top pipe detection
        if (birdPos.y > topPipePos1.y - birdHeight) {
            if (birdPos.x > topPipePos1.x - birdWidth && birdPos.x < topPipePos1.x + TEXTURE_PIPE_TOP.getWidth() - birdWidth/2) {
                collision = true;
                Gdx.app.log("Physics", "Collided with the top pipe");
            }
        }

        // Bottom pipe detection
        if (birdPos.y < bottomPipePos1.y - birdHeight) {
            if (birdPos.x > bottomPipePos1.x - birdWidth && birdPos.x < bottomPipePos1.x + TEXTURE_PIPE_BOTTOM.getWidth() - birdWidth/2) {
                collision = true;
                Gdx.app.log("Physics", "Collided with the bottom pipe");
            }
        }
        return collision;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(TEXTURE_BACKGROUND, bgPos.x, bgPos.y);
        spriteBatch.draw(TEXTURE_PIPE_TOP, topPipePos1.x, topPipePos1.y);
        spriteBatch.draw(TEXTURE_PIPE_BOTTOM, bottomPipePos1.x, bottomPipePos1.y);
        spriteBatch.draw(TEXTURE_GROUND, groundPos.x, groundPos.y);
        bird.draw(spriteBatch);

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
            topPipePos1.x = FlappyBird.WIDTH;
            topPipePos1.y = getRandomTopPipeHeight();
            bottomPipePos1 = getBottomPipePos();
            passedPipes = false;
        }
        bottomPipePos1.x -= groundSpeed * Gdx.graphics.getDeltaTime();
    }

    public void resetPipePositions() {
        topPipePos1 = new Vector2(FlappyBird.WIDTH, getRandomTopPipeHeight());
    }
}
