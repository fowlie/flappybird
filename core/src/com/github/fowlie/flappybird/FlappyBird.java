package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.fowlie.flappybird.state.StateManager;


public class FlappyBird extends ApplicationAdapter {
    WorldManager worldManager;
    TextManager textManager;
    StateManager stateManager;
    Bird bird;
    SpriteBatch spriteBatch;
    int width, height;

    public FlappyBird(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
	public void create () {
		spriteBatch = new SpriteBatch();
        textManager = new TextManager(spriteBatch);
        stateManager = new StateManager();
        worldManager = new WorldManager();
        bird = new Bird(new Vector2(width /4, height /2));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
        worldManager.render(spriteBatch);
        bird.draw(spriteBatch);
        stateManager.update(worldManager, textManager, bird);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        Resources.dispose();
    }
}
