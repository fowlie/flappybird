package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FlappyBird extends ApplicationAdapter {
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
    BitmapFont font;

    public FlappyBird(int width, int height) {
        this.width = width;
        this.height = height;
        birdPos = new Vector2(width/2, height/2);
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
            bgBatch.draw(bg, 0, height - bg.getHeight());
            elapsedTime += dt();
            bgBatch.draw(walkSheet.getKeyFrame(elapsedTime, true), birdPos.x, birdPos.y);
            bgBatch.draw(ground, groundPos.x, groundPos.y);

            drawDropShadowString(bgBatch, "Flappy Bird", 2, width/4, height-height/8);

		bgBatch.end();


        if (Gdx.input.justTouched()) {
            birdPos.y += jump * dt();
        }

        //Pull the bird down
        birdPos.y -= gravity * dt();

        //Move ground to the left
        groundPos.x -= groundSpeed * dt();
        if (groundPos.x < -50) {
            groundPos.x = 0;
        }
    }

    private void drawDropShadowString(SpriteBatch bgBatch, String text, int shadowSize, int x, int y) {
        font.setColor(0f, 0f, 0f, 1f);
        font.draw((Batch) this.bgBatch, text, x-shadowSize, y-shadowSize);
        font.setColor(1.0f, 1.0f, 0.5f, 0.9f);
        font.draw((Batch) this.bgBatch, text, x, y);
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