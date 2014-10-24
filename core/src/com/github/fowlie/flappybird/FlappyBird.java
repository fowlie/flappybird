package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.assets.AssetManager;

public class FlappyBird extends ApplicationAdapter {
    Vector2 bgPos;
    boolean isRunning = false;
	int width, height;
    SpriteBatch spriteBatch;
	Texture bg, ground, topPipe;
    Animation birdAnim;
    TextureAtlas textureAtlas;
    float elapsedTime = 0;
    Vector2 birdPos, groundPos;
    float gravity = 5.8f;
    float jump = 200;
    float groundSpeed = 100;
    float verticalSpeed = 0;
    BitmapFont font;
    AssetManager assetManager;
    boolean gameOver = false;
    int score = 0;
    int birdWidth, birdHeight;

    public FlappyBird(int width, int height) {
        this.width = width;
        this.height = height;
        birdPos = new Vector2(width/4, height/2);
        bgPos = new Vector2(0, 57);
        groundPos = new Vector2(0, 0);
        assetManager = new AssetManager();
    }

    @Override
	public void create () {
		spriteBatch = new SpriteBatch();
		bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        topPipe = new Texture("pipe1.png");
        textureAtlas = new TextureAtlas("bird.txt");
        birdAnim = new Animation(1/15f, textureAtlas.getRegions());
        font = new BitmapFont(Gdx.files.internal("04B_19__-32.fnt"), Gdx.files.internal("font.png"), false);
        birdWidth = birdAnim.getKeyFrame(elapsedTime).getRegionWidth();
        birdHeight = birdAnim.getKeyFrame(elapsedTime).getRegionHeight();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
        spriteBatch.draw(bg, bgPos.x, bgPos.y);
        spriteBatch.draw(bg, bgPos.x + bg.getWidth(), bgPos.y); //Draw two times to animate it
        elapsedTime += dt();
        spriteBatch.draw(birdAnim.getKeyFrame(elapsedTime, !gameOver), birdPos.x, birdPos.y,
                birdWidth/2, birdHeight/2, birdWidth, birdHeight, 1, 1, verticalSpeed * 10 * 2);
        spriteBatch.draw(ground, groundPos.x, groundPos.y);

        if (!isRunning) {
            drawDropShadowString(spriteBatch, "Flappy Bird", new Color(1,1,.5f,.9f), 3, width / 5, height - height / 8);
            drawDropShadowString(spriteBatch, "by Fowlie", new Color(1,1,.5f,.9f), 2, width/5, height - height/4);
            drawDropShadowString(spriteBatch, "touch to play", new Color(1,1,.5f,.9f), 2, width/6, height/6);
            birdPos.y += Math.round(elapsedTime % 10) % 2 == 0 ? 1 : -1;

        } else if (isRunning && !gameOver) {
            drawDropShadowString(spriteBatch, Integer.toString(score), new Color(1, 1, 1, .9f), 3, width / 2, height - height / 10);
        }
        if (gameOver) {
            drawDropShadowString(spriteBatch, "Game Over", new Color(1,1,.5f,.9f), 3, width / 5, height - height / 3);
        }

        if (Gdx.input.justTouched() && !gameOver) {
            if (!isRunning) isRunning = true;
            verticalSpeed += jump * dt();
            score++;
        }

        //Pull the bird down
        if (isRunning && !gameOver) {
            verticalSpeed -= gravity * dt();
            birdPos.y += verticalSpeed*2;
        }

        //Move ground to the left
        if (!gameOver) {
            groundPos.x -= groundSpeed * dt();
            if (groundPos.x < -50) {
                groundPos.x = 0;
            }
        }

        //Move city background
//        bgPos.x -= .2f * groundSpeed * dt();
        if (bgPos.x < -bg.getWidth()) {
            bgPos.x = 0;
        }

        collisionDetection();

        spriteBatch.end();
    }

    private void collisionDetection() {
        if (birdPos.y < ground.getHeight() + (birdHeight / 3)) {
            gameOver = true;
        }
    }

    private void drawDropShadowString(SpriteBatch bgBatch, String text, Color color, int shadowSize, int x, int y) {
        font.setColor(0f, 0f, 0f, 1f);
        font.draw(bgBatch, text, x - shadowSize, y - shadowSize);
        font.setColor(color);
        font.draw(bgBatch, text, x, y);
    }

    private float dt() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        bg.dispose();
        font.dispose();
        ground.dispose();
    }
}