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

public class FlappyBird extends ApplicationAdapter {
    Vector2 bgPos;
    boolean isRunning = false;
	int width, height;
    SpriteBatch bgBatch;
	Texture bg, ground;
    Animation walkSheet;
    TextureAtlas textureAtlas;
    float elapsedTime = 0;
    Vector2 birdPos, groundPos;
    float gravity = 75;
    float jump = 2000;
    float groundSpeed = 100;
    float verticalSpeed = 0;
    BitmapFont font;

    public FlappyBird(int width, int height) {
        this.width = width;
        this.height = height;
        birdPos = new Vector2(width/2, height/2);
        bgPos = new Vector2(0, 57);
        groundPos = new Vector2(0, 0);
    }

    @Override
	public void create () {
		bgBatch = new SpriteBatch();
		bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        textureAtlas = new TextureAtlas("bird.txt");
        walkSheet = new Animation(1/15f, textureAtlas.getRegions());
        font = new BitmapFont(Gdx.files.internal("04B_19__-32.fnt"), Gdx.files.internal("font.png"), false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bgBatch.begin();
        bgBatch.draw(bg, bgPos.x, bgPos.y);
        bgBatch.draw(bg, bgPos.x + bg.getWidth(), bgPos.y); //Draw two times to animate it
        elapsedTime += dt();
        bgBatch.draw(walkSheet.getKeyFrame(elapsedTime, true), birdPos.x, birdPos.y);
        bgBatch.draw(ground, groundPos.x, groundPos.y);

        if (!isRunning) {
            drawDropShadowString(bgBatch, "Flappy Bird", new Color(1,1,.5f,.9f), 2, width / 5, height - height / 8);
            drawDropShadowString(bgBatch, "by Fowlie", new Color(1,1,.5f,.9f), 2, width/5, height - height/4);
            drawDropShadowString(bgBatch, "touch to play", new Color(1,1,.5f,.9f), 1, width/6, height/6);
        }

        if (Gdx.input.justTouched()) {
            verticalSpeed += jump * dt();
        }

        //Pull the bird down
        verticalSpeed -= gravity * dt();
        birdPos.y += verticalSpeed;

        //Move ground to the left
        groundPos.x -= groundSpeed * dt();
        if (groundPos.x < -50) {
            groundPos.x = 0;
        }

        //Move city background
        bgPos.x -= .2f * groundSpeed * dt();
        if (bgPos.x < -bg.getWidth()) {
            bgPos.x = 0;
        }

        bgBatch.end();
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
        bgBatch.dispose();
        bg.dispose();
        font.dispose();
        ground.dispose();
    }
}