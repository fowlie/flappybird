package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;

import static com.github.fowlie.flappybird.Assets.*;

public class World {
    private Bird bird;
    private boolean scrollPipes = false, scrollBackground = true, passedPipes = false;
    private final Vector2 groundPos, backgroundPos;
    private LinkedList<Pipes> pipes = new LinkedList<Pipes>();
    float groundSpeed = 100;

    public World() {
        bird = new Bird(new Vector2(FlappyBird.WIDTH /4, FlappyBird.HEIGHT/2));
        pipes.add(new Pipes());
        backgroundPos = new Vector2(0, 96);
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

    public void enableBackgroundScrolling() {
        scrollBackground = true;
    }

    public void enableScrolling() {
        scrollPipes = true;
        scrollBackground = true;
    }

    public void stopScrolling() {
        scrollPipes = false;
        scrollBackground = false;
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
        if (birdPos.y < pipes.getFirst().getBottomPipe().y + TEXTURE_PIPE_BOTTOM.getHeight()) {
            if (birdPos.x > pipes.getFirst().getBottomPipe().x - birdWidth && birdPos.x < pipes.getFirst().getBottomPipe().x + TEXTURE_PIPE_BOTTOM.getWidth() - birdWidth/2) {
                collision = true;
                Gdx.app.log("Physics", "Collided with the bottom pipe");
            }
        }
        return collision;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(TEXTURE_BACKGROUND, backgroundPos.x, backgroundPos.y);
        spriteBatch.draw(TEXTURE_BACKGROUND, backgroundPos.x + Assets.TEXTURE_BACKGROUND.getWidth(), backgroundPos.y);
        for (Pipes pipe : pipes) pipe.draw(spriteBatch);
        spriteBatch.draw(TEXTURE_GROUND, groundPos.x, groundPos.y);
        spriteBatch.draw(TEXTURE_GROUND, groundPos.x + Assets.TEXTURE_GROUND.getWidth(), groundPos.y);
        bird.draw(spriteBatch);

        if (scrollBackground) scrollBackground();
        if (scrollPipes) scrollPipes();
    }

    private void scrollBackground() {
        groundPos.x -= groundSpeed * Gdx.graphics.getDeltaTime();
        if (groundPos.x < -Assets.TEXTURE_GROUND.getWidth()) {
            groundPos.x = 0;
        }
        backgroundPos.x -= groundSpeed * 0.2 * Gdx.graphics.getDeltaTime();
        if (backgroundPos.x < -Assets.TEXTURE_BACKGROUND.getWidth()) {
            backgroundPos.x = 0;
        }
    }

    private void scrollPipes() {
        for (Pipes p : pipes) p.getTopPipe().x -= groundSpeed * Gdx.graphics.getDeltaTime();
        for (Pipes p : pipes) p.getBottomPipe().x -= groundSpeed * Gdx.graphics.getDeltaTime();
        if (pipes.getFirst().getTopPipe().x < FlappyBird.HEIGHT / 6 && pipes.size() < 2) {
            pipes.add(new Pipes());
        }
        if (pipes.getFirst().getTopPipe().x < -TEXTURE_PIPE_BOTTOM.getWidth()) {
            pipes.removeFirst();
            passedPipes = false;
        }
    }

    public void resetPipePositions() {
        pipes = new LinkedList<Pipes>();
        pipes.add(new Pipes());
    }
}
