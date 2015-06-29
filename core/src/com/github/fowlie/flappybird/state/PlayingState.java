package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.github.fowlie.flappybird.Bird;
import com.github.fowlie.flappybird.GUI;
import com.github.fowlie.flappybird.World;

public class PlayingState implements State {
    int width = Gdx.app.getGraphics().getWidth();
    int height = Gdx.app.getGraphics().getHeight();

    public PlayingState() {
        Gdx.app.log("PlayingState", "Entered the playing state");
    }

    @Override
    public State handleInput(World world) {
        if (Gdx.input.justTouched()) {
            world.getBird().jump();
        }
        return this;
    }

    @Override
    public State update(World world, GUI gui) {
        world.enableScrolling();
        Bird bird = world.getBird();
        if (world.collisionDetection(bird.getPosition(), bird.getHeight(), bird.getWidth())) {
            return new GameOverState();
        }
        if (!world.hasPassedPipes() && bird.getPosition().x - bird.getWidth() > world.getTopPipePos().x) {
            bird.addToScore(1);
            world.passedPipes();
        }
        gui.drawDropShadowString(Integer.toString(bird.getScore()), new Color(1, 1, 1, .9f), 3, width / 2, height - height / 10);
        bird.applyGravity();
        return this;
    }
}
