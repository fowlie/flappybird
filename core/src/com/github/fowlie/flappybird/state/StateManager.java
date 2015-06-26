package com.github.fowlie.flappybird.state;

import com.github.fowlie.flappybird.Bird;
import com.github.fowlie.flappybird.TextManager;
import com.github.fowlie.flappybird.WorldManager;

public class StateManager {
    private State state = new MenuState();

    public void update(WorldManager worldManager, TextManager textManager, Bird bird) {
        state = state.handleInput(worldManager, bird);
        state = state.update(worldManager, textManager, bird);
    }
}
