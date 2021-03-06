package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.github.fowlie.flappybird.FlappyBird;
import com.github.fowlie.flappybird.GUI;
import com.github.fowlie.flappybird.World;

public class GameOverState implements State {
    private static final String HIGHSCORE = "highscore";
    private int highScore;

    public GameOverState(int score) {
        Gdx.app.log("GameOverState", "Entered the game over state");
        Preferences preferences = Gdx.app.getPreferences("FlappyBird");
        highScore = preferences.getInteger(HIGHSCORE);
        if (score > highScore) {
            Gdx.app.log("GameOverState", "Saving new high score: " + score);
            preferences.putInteger(HIGHSCORE, score);
            preferences.flush();
            highScore = score;
        }
    }

    @Override
    public State handleInput(World world) {
        if (Gdx.input.justTouched()) {
            world.getBird().resetScore();
            world.getBird().animate();
            world.getBird().setPosition(new Vector2(FlappyBird.WIDTH / 4, FlappyBird.HEIGHT / 2));
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
        gui.drawDropShadowString("Game Over", 3, FlappyBird.WIDTH / 5, FlappyBird.HEIGHT - FlappyBird.HEIGHT / 4);
        gui.drawDropShadowString("Best: " + highScore, 3, FlappyBird.WIDTH / 5, (int) (FlappyBird.HEIGHT - FlappyBird.HEIGHT / 3));
        String score = Integer.toString(world.getBird().getScore());
        gui.drawDropShadowString("Score: " + score, 3, FlappyBird.WIDTH / 5, FlappyBird.HEIGHT / 2);
        return this;
    }
}
