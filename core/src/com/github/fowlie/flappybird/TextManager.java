package com.github.fowlie.flappybird;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextManager {
    public static final Color FONT_COLOR = new Color(1, 1, .5f, .9f);
    private BitmapFont bitmapFont = Resources.FONT;
    private SpriteBatch spriteBatch;

    public TextManager(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void drawDropShadowString(String text, Color color, int shadowSize, int x, int y) {
        bitmapFont.setColor(0f, 0f, 0f, 1f);
        bitmapFont.draw(spriteBatch, text, x - shadowSize, y - shadowSize);
        bitmapFont.setColor(color);
        bitmapFont.draw(spriteBatch, text, x, y);
    }
}
