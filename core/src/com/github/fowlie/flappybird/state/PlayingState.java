package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.github.fowlie.flappybird.Bird;
import com.github.fowlie.flappybird.TextManager;
import com.github.fowlie.flappybird.WorldManager;

public class PlayingState implements State {
    int width = Gdx.app.getGraphics().getWidth();
    int height = Gdx.app.getGraphics().getHeight();

    public PlayingState() {
        Gdx.app.log("PlayingState", "Entered the playing state");
    }

    @Override
    public State handleInput(WorldManager worldManager, Bird bird) {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
        return this;
    }

    @Override
    public State update(WorldManager worldManager, TextManager textManager, Bird bird) {
        worldManager.enableScrolling();
        if (worldManager.collisionDetection(bird.getPosition(), bird.getHeight(), bird.getWidth())) {
            return new GameOverState();
        }
        if (!worldManager.hasPassedPipes() && bird.getPosition().x - bird.getWidth() > worldManager.getTopPipePos().x) {
            bird.addToScore(1);
            worldManager.passedPipes();
        }
        textManager.drawDropShadowString(Integer.toString(bird.getScore()), new Color(1, 1, 1, .9f), 3, width / 2, height - height / 10);
        bird.applyGravity();
        return this;
    }
}
