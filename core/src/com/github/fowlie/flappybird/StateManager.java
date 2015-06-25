package com.github.fowlie.flappybird;

import java.util.logging.Logger;

enum GameState {MENU,PLAYING,GAME_OVER}

public class StateManager {
    private final Logger logger = Logger.getLogger(StateManager.class.getName());
    private GameState gameState = GameState.MENU;

    public GameState getState() {
        return gameState;
    }

    public void nextState() {
        switch (gameState) {
            case MENU:      gameState = GameState.PLAYING; break;
            case PLAYING:   gameState = GameState.GAME_OVER; break;
            case GAME_OVER: gameState = GameState.MENU; break;
        }
        logger.info("State set to " + gameState);
    }
}
