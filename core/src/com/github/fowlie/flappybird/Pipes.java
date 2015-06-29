package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import static com.github.fowlie.flappybird.Resources.TEXTURE_PIPE_BOTTOM;

public class Pipes {
    private Vector2 topPipe, bottomPipe;

    protected Pipes(int pipeGap) {
        this.topPipe = getRandomTopPipePos();
        this.bottomPipe = new Vector2(FlappyBird.WIDTH, topPipe.y - TEXTURE_PIPE_BOTTOM.getHeight() - pipeGap);
    }

    public Vector2 getRandomTopPipePos() {
        Random random = new Random();
        int highest = FlappyBird.HEIGHT + (Resources.TEXTURE_PIPE_TOP.getHeight() / 2) - (FlappyBird.HEIGHT/ 10);
        int randomHeight = random.nextInt(highest - FlappyBird.HEIGHT) + FlappyBird.HEIGHT / 2;
        Gdx.app.log("World", "New top pipe height pos: " + randomHeight);
        return new Vector2(FlappyBird.WIDTH, randomHeight);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Resources.TEXTURE_PIPE_TOP, topPipe.x, topPipe.y);
        spriteBatch.draw(Resources.TEXTURE_PIPE_BOTTOM, bottomPipe.x, bottomPipe.y);
    }

    public Vector2 getTopPipe() {
        return topPipe;
    }

    public Vector2 getBottomPipe() {
        return bottomPipe;
    }
}
