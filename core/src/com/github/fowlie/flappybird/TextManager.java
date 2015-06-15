package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextManager {
    private BitmapFont bitmapFont;

    public TextManager() {
        bitmapFont = new BitmapFont(Gdx.files.internal("04B_19__-32.fnt"), Gdx.files.internal("font.png"), false);
    }

    public void drawDropShadowString(SpriteBatch bgBatch, String text, Color color, int shadowSize, int x, int y) {
        bitmapFont.setColor(0f, 0f, 0f, 1f);
        bitmapFont.draw(bgBatch, text, x - shadowSize, y - shadowSize);
        bitmapFont.setColor(color);
        bitmapFont.draw(bgBatch, text, x, y);
    }

    public void dispose() {
        bitmapFont.dispose();
    }
}
