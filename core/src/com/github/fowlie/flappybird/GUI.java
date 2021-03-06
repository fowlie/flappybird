package com.github.fowlie.flappybird;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GUI {
    public static final Color FONT_YELLOW = new Color(1, 1, .5f, .9f);
    public static final Color FONT_WHITE = new Color(1, 1, 1, .9f);
    private BitmapFont bitmapFont = Assets.FONT;
    private SpriteBatch spriteBatch;

    public GUI(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void drawDropShadowString(String text, int x, int y) {
        drawDropShadowString(text, FONT_YELLOW, 2, x, y);
    }

    public void drawDropShadowString(String text, int shadowSize, int x, int y) {
        drawDropShadowString(text, FONT_YELLOW, shadowSize, x, y);
    }

    public void drawDropShadowString(String text, Color color, int shadowSize, int x, int y) {
        bitmapFont.setColor(0f, 0f, 0f, 1f); //black
        bitmapFont.draw(spriteBatch, text, x - shadowSize, y - shadowSize);
        bitmapFont.setColor(color);
        bitmapFont.draw(spriteBatch, text, x, y);
    }
}
