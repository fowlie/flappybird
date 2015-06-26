package com.github.fowlie.flappybird.state;

import com.github.fowlie.flappybird.Bird;
import com.github.fowlie.flappybird.TextManager;
import com.github.fowlie.flappybird.WorldManager;

public interface State {
    State handleInput(WorldManager worldManager, Bird bird);
    State update(WorldManager worldManager, TextManager textManager, Bird bird);
}
