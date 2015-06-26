package com.github.fowlie.flappybird.state;

import com.badlogic.gdx.Gdx;
import com.github.fowlie.flappybird.Bird;
import com.github.fowlie.flappybird.FlappyBird;
import com.github.fowlie.flappybird.TextManager;
import com.github.fowlie.flappybird.WorldManager;

public class MenuState implements State {
    int width = Gdx.app.getGraphics().getWidth();
    int height = Gdx.app.getGraphics().getHeight();

    public MenuState() {
        Gdx.app.log("MenuState", "Entered the menu state");
    }

    @Override
    public State handleInput(WorldManager worldManager, Bird bird) {
        if (Gdx.input.justTouched()) {
            bird.jump();
            return new PlayingState();
        }
        return this;
    }

    @Override
    public State update(WorldManager worldManager, TextManager textManager, Bird bird) {
        textManager.drawDropShadowString("Flappy Bird", TextManager.FONT_COLOR, 3, width / 5, height - height / 8);
        textManager.drawDropShadowString("by Fowlie", TextManager.FONT_COLOR, 2, width / 5, height - height / 4);
        textManager.drawDropShadowString("touch to play", TextManager.FONT_COLOR, 2, width /6, height / 6);
        bird.flyUpAndDown();
        return this;
    }
}
