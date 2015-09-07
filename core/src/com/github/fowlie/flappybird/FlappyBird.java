package com.github.fowlie.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.fowlie.flappybird.state.StateManager;

public class FlappyBird extends ApplicationAdapter {
    public static int WIDTH = 320, HEIGHT = 480;
    private FitViewport viewport;
    private StateManager stateManager;
    private SpriteBatch spriteBatch;
    private World world;
    private GUI gui;
    private OrthographicCamera camera;

    @Override
    public void create() {
        stateManager = new StateManager();
        spriteBatch = new SpriteBatch();
        world = new World();
        gui = new GUI(spriteBatch);
        camera = new OrthographicCamera(320, 480);
        camera.position.set(320/2, 480/2, 0);
        viewport = new FitViewport(320, 480, camera);
        viewport.apply();
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
        world.render(spriteBatch);
        world.update();
        stateManager.update(world, gui);
        spriteBatch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        Assets.dispose();
    }
}
