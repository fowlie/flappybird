package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

enum GameState {MENU,PLAYING,GAME_OVER}

public class FlappyBird extends ApplicationAdapter {
    GameState gameState = GameState.MENU;
    WorldManager worldManager;
    Bird bird;
    SpriteBatch spriteBatch;
    BitmapFont font;
    int width, height, score = 0;

    public FlappyBird(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
	public void create () {
		spriteBatch = new SpriteBatch();
        worldManager = new WorldManager(spriteBatch, width, height);
        bird = new Bird(new Vector2(width/4, height/2));
        font = new BitmapFont(Gdx.files.internal("04B_19__-32.fnt"), Gdx.files.internal("font.png"), false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
        worldManager.render();
        bird.draw(spriteBatch);

        switch (gameState) {
            case MENU:
                drawDropShadowString(spriteBatch, "Flappy Bird", new Color(1,1,.5f,.9f), 3, width / 5, height - height / 8);
                drawDropShadowString(spriteBatch, "by Fowlie", new Color(1,1,.5f,.9f), 2, width/5, height - height/4);
                drawDropShadowString(spriteBatch, "touch to play", new Color(1,1,.5f,.9f), 2, width/6, height/6);
                bird.flyUpAndDown();
                break;

            case PLAYING:
                worldManager.enableScrolling();
                if (worldManager.collisionDetection(bird.getPosition(), bird.getHeight(), bird.getWidth())) {
                    nextGameState();
                } else {
                    drawDropShadowString(spriteBatch, Integer.toString(score), new Color(1, 1, 1, .9f), 3, width / 2, height - height / 10);
                    bird.applyGravity();
                }
                break;

            case GAME_OVER:
                worldManager.stopScrolling();
                bird.stopAnimate();
                drawDropShadowString(spriteBatch, "Game Over", new Color(1, 1, .5f, .9f), 3, width / 5, height - height / 3);
                drawDropShadowString(spriteBatch, "Score: " + Integer.toString(score), new Color(1,1,.5f,.9f), 3, width / 5, height / 2);
                break;
        }

        processUserInput();

        spriteBatch.end();
    }

    private void processUserInput() {
        if (Gdx.input.justTouched()) {
            switch (gameState) {
                case MENU:
                    nextGameState();
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

    private void nextGameState() {
        switch (gameState) {
            case MENU:      gameState = GameState.PLAYING; break;
            case PLAYING:   gameState = GameState.GAME_OVER; break;
            case GAME_OVER: gameState = GameState.MENU; break;
        }
    }

    private void resetGame() {
        score = 0;
        bird.animate();
        bird.setPosition(new Vector2(width/4, height/2));
        bird.stopFalling();
        worldManager.resetPipePositions();
        nextGameState();
    }

    private void drawDropShadowString(SpriteBatch bgBatch, String text, Color color, int shadowSize, int x, int y) {
        font.setColor(0f, 0f, 0f, 1f);
        font.draw(bgBatch, text, x - shadowSize, y - shadowSize);
        font.setColor(color);
        font.draw(bgBatch, text, x, y);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        worldManager.dispose();
        font.dispose();
    }
}