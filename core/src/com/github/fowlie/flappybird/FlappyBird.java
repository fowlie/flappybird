package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class FlappyBird extends ApplicationAdapter {
    WorldManager worldManager;
    TextManager textManager;
    StateManager stateManager;
    Bird bird;
    SpriteBatch spriteBatch;
    int width, height, score = 0;
    private Color fontColor = new Color(1, 1, .5f, .9f);;

    public FlappyBird(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
	public void create () {
		spriteBatch = new SpriteBatch();
        textManager = new TextManager();
        stateManager = new StateManager();
        worldManager = new WorldManager(width, height);
        bird = new Bird(new Vector2(width/4, height/2));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
        worldManager.render(spriteBatch);
        bird.draw(spriteBatch);

        switch (stateManager.getState()) {
            case MENU:
                textManager.drawDropShadowString(spriteBatch, "Flappy Bird", fontColor, 3, width / 5, height - height / 8);
                textManager.drawDropShadowString(spriteBatch, "by Fowlie", fontColor, 2, width / 5, height - height/4);
                textManager.drawDropShadowString(spriteBatch, "touch to play", fontColor, 2, width /6, height/6);
                bird.flyUpAndDown();
                break;

            case PLAYING:
                worldManager.enableScrolling();
                if (worldManager.collisionDetection(bird.getPosition(), bird.getHeight(), bird.getWidth())) {
                    stateManager.nextState();
                } else {
                    textManager.drawDropShadowString(spriteBatch, Integer.toString(score), new Color(1, 1, 1, .9f), 3, width / 2, height - height / 10);
                    bird.applyGravity();
                }
                break;

            case GAME_OVER:
                worldManager.stopScrolling();
                bird.stopAnimate();
                textManager.drawDropShadowString(spriteBatch, "Game Over", fontColor, 3, width / 5, height - height / 3);
                textManager.drawDropShadowString(spriteBatch, "Score: " + Integer.toString(score), fontColor, 3, width / 5, height / 2);
                break;
        }

        processUserInput();

        spriteBatch.end();
    }

    private void processUserInput() {
        if (Gdx.input.justTouched()) {
            switch (stateManager.getState()) {
                case MENU:
                    stateManager.nextState();
                    bird.jump();
                    break;
                case PLAYING:
                    bird.jump();
                    score++;
                    break;
                case GAME_OVER:
                    resetGame();
                    break;
            }
        }
    }

    private void resetGame() {
        score = 0;
        bird.animate();
        bird.setPosition(new Vector2(width/4, height/2));
        bird.stopFalling();
        worldManager.resetPipePositions();
        stateManager.nextState();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        Resources.dispose();
    }
}