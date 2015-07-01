package com.github.fowlie.flappybird.state;

import com.github.fowlie.flappybird.GUI;
import com.github.fowlie.flappybird.World;

public class StateManager {
    private State state = new MenuState();

    public void update(World world, GUI gui) {
        state = state.handleInput(world).update(world, gui);
    }
}
