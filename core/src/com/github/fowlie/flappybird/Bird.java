package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private final Animation birdAnim = Assets.ANIM_BIRD;
    private float elapsedTime = 0f, verticalSpeed;
    private boolean animated = true;
    private double jump = 200;
    private Vector2 birdPos;
    private float gravity = 8f;
    private int score = 0;

    public Bird(Vector2 birdPos) {
        this.birdPos = birdPos;
    }

    public void stopAnimate() {
        animated = false;
    }

    public void animate() {
        animated = true;
    }

    public int getWidth() {
        return birdAnim.getKeyFrame(elapsedTime).getRegionWidth();
    }

    public int getHeight() {
        return birdAnim.getKeyFrame(elapsedTime).getRegionHeight();
    }

    public void draw(SpriteBatch spriteBatch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        spriteBatch.draw(birdAnim.getKeyFrame(elapsedTime, animated), birdPos.x, birdPos.y,
                getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1, 1, verticalSpeed * 10);
    }

    public void jump() {
        verticalSpeed = (float) (jump * Gdx.graphics.getDeltaTime());
    }

    public void stopFalling() {
        verticalSpeed = 0f;
    }

    public void flyUpAndDown() {
        birdPos.y += Math.round(elapsedTime % 10) % 2 == 0 ? 1 : -1; //Fly up and down
    }

    public void setPosition(Vector2 pos) {
        birdPos = pos;
    }

    public void applyGravity() {
        verticalSpeed -= gravity * Gdx.graphics.getDeltaTime();
        birdPos.y += verticalSpeed*2;
    }

    public Vector2 getPosition() {
        return birdPos;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public void addToScore(int i) {
        score += i;
    }
}
