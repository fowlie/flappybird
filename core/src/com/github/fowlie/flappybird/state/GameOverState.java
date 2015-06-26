package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.fowlie.flappybird.Bird;
import com.github.fowlie.flappybird.TextManager;
import com.github.fowlie.flappybird.WorldManager;

public class GameOverState implements State {
    int width = Gdx.app.getGraphics().getWidth();
    int height = Gdx.app.getGraphics().getHeight();

    public GameOverState() {
        Gdx.app.log("GameOverState", "Entered the game over state");
    }

    @Override
    public State handleInput(WorldManager worldManager, Bird bird) {
        if (Gdx.input.justTouched()) {
            bird.resetScore();
            bird.animate();
            bird.setPosition(new Vector2(width / 4, height / 2));
            bird.stopFalling();
            worldManager.resetPipePositions();
            return new MenuState();
        }
        return this;
    }

    @Override
    public State update(WorldManager worldManager, TextManager textManager, Bird bird) {
        worldManager.stopScrolling();
        bird.stopAnimate();
        textManager.drawDropShadowString("Game Over", TextManager.FONT_COLOR, 3, width / 5, height - height / 3);
        textManager.drawDropShadowString("Score: " + Integer.toString(bird.getScore()), TextManager.FONT_COLOR, 3, width / 5, height / 2);
        return this;
    }
}
