package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.fowlie.flappybird.state.StateManager;

public class FlappyBird extends ApplicationAdapter {
    public static int WIDTH, HEIGHT;
    private StateManager stateManager;
    private SpriteBatch spriteBatch;
    private World world;
    private GUI gui;

    public FlappyBird(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    public void create() {
        stateManager = new StateManager();
        spriteBatch = new SpriteBatch();
        world = new World();
        gui = new GUI(spriteBatch);
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
        world.render(spriteBatch);
        stateManager.update(world, gui);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        Resources.dispose();
    }
}
