package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.github.fowlie.flappybird.FlappyBird;
import com.github.fowlie.flappybird.GUI;
import com.github.fowlie.flappybird.World;

public class MenuState implements State {
    public MenuState() {
        Gdx.app.log("MenuState", "Entered the menu state");
    }

    @Override
    public State handleInput(World world) {
        if (Gdx.input.justTouched()) {
            world.getBird().jump();
            return new PlayingState();
        }
        return this;
    }

    @Override
    public State update(World world, GUI gui) {
        world.enableBackgroundScrolling();
        gui.drawDropShadowString("Flappy Bird", 3, FlappyBird.WIDTH / 5, FlappyBird.HEIGHT - FlappyBird.HEIGHT/ 8);
        gui.drawDropShadowString("by Fowlie", FlappyBird.WIDTH / 5, FlappyBird.HEIGHT - FlappyBird.HEIGHT / 4);
        gui.drawDropShadowString("touch to play", FlappyBird.WIDTH /6, FlappyBird.HEIGHT / 6);
        world.getBird().flyUpAndDown();
        return this;
    }
}
