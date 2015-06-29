package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

import static com.github.fowlie.flappybird.Resources.*;

public class World {
    private Bird bird;
    private boolean scrollPipes = false, scrollGround = true, passedPipes = false;
    private final Vector2 groundPos;
    private LinkedList<Pipes> pipes = new LinkedList<Pipes>();
    private final Vector2 bgPos;
    private int pipeGap = FlappyBird.HEIGHT / 5;
    float groundSpeed = 100;

    public World() {
        bird = new Bird(new Vector2(FlappyBird.WIDTH /4, FlappyBird.HEIGHT/2));
        pipes.add(new Pipes(pipeGap));
        bgPos = new Vector2(0, 57);
        groundPos = new Vector2(0, 0);
    }

    public Bird getBird() {
        return bird;
    }

    public Vector2 getTopPipePos() {
        return pipes.getFirst().getTopPipe();
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

    public boolean collisionDetection(Vector2 birdPos, int birdHeight, int birdWidth) {
        boolean collision = false;
        // Ground detection
        if (birdPos.y < TEXTURE_GROUND.getHeight() + (birdHeight / 3)) {
            collision = true;
            Gdx.app.log("Physics", "Collided with the ground");
        }

        // Top pipe detection
        if (birdPos.y > pipes.getFirst().getTopPipe().y - birdHeight) {
            if (birdPos.x > pipes.getFirst().getTopPipe().x - birdWidth && birdPos.x < pipes.getFirst().getTopPipe().x + TEXTURE_PIPE_TOP.getWidth() - birdWidth/2) {
                collision = true;
                Gdx.app.log("Physics", "Collided with the top pipe");
            }
        }

        // Bottom pipe detection
        if (birdPos.y < pipes.getFirst().getBottomPipe().y - birdHeight) {
            if (birdPos.x > pipes.getFirst().getBottomPipe().x - birdWidth && birdPos.x < pipes.getFirst().getBottomPipe().x + TEXTURE_PIPE_BOTTOM.getWidth() - birdWidth/2) {
                collision = true;
                Gdx.app.log("Physics", "Collided with the bottom pipe");
            }
        }
        return collision;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(TEXTURE_BACKGROUND, bgPos.x, bgPos.y);
        for (Pipes pipe : pipes) pipe.draw(spriteBatch);
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
        pipes.getFirst().getTopPipe().x -= groundSpeed * Gdx.graphics.getDeltaTime();
        if (pipes.getFirst().getTopPipe().x < (0 - TEXTURE_PIPE_TOP.getWidth())) {
            pipes.removeFirst();
            pipes.add(new Pipes(pipeGap));
            passedPipes = false;
        }
        pipes.getFirst().getBottomPipe().x -= groundSpeed * Gdx.graphics.getDeltaTime();
    }

    public void resetPipePositions() {
        pipes.removeFirst();
        pipes.add(new Pipes(pipeGap));
    }
}
