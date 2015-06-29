package com.github.fowlie.flappybird.state;

import com.github.fowlie.flappybird.GUI;
import com.github.fowlie.flappybird.World;

public interface State {
    State handleInput(World world);
    State update(World world, GUI gui);
}
