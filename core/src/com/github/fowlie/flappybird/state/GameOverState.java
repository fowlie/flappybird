package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.fowlie.flappybird.GUI;
import com.github.fowlie.flappybird.World;

public class GameOverState implements State {
    int width = Gdx.app.getGraphics().getWidth();
    int height = Gdx.app.getGraphics().getHeight();

    public GameOverState() {
        Gdx.app.log("GameOverState", "Entered the game over state");
    }

    @Override
    public State handleInput(World world) {
        if (Gdx.input.justTouched()) {
            world.getBird().resetScore();
            world.getBird().animate();
            world.getBird().setPosition(new Vector2(width / 4, height / 2));
            world.getBird().stopFalling();
            world.resetPipePositions();
            return new MenuState();
        }
        return this;
    }

    @Override
    public State update(World world, GUI gui) {
        world.stopScrolling();
        world.getBird().stopAnimate();
        gui.drawDropShadowString("Game Over", 3, width / 5, height - height / 3);
        gui.drawDropShadowString("Score: " + Integer.toString(world.getBird().getScore()), 3, width / 5, height / 2);
        return this;
    }
}
