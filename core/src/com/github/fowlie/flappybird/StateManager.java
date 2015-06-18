package com.github.fowlie.flappybird;

enum GameState {MENU,PLAYING,GAME_OVER}

public class StateManager {
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
    }
}
