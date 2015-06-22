package com.github.fowlie.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {
    public static final BitmapFont FONT = new BitmapFont(Gdx.files.internal("04B_19__-32.fnt"), Gdx.files.internal("font.png"), false);
    public static final TextureAtlas TEXTURE_ATLAS_BIRD = new TextureAtlas("bird.txt");
    public static final Animation ANIM_BIRD = new Animation(1 / 15f, TEXTURE_ATLAS_BIRD.getRegions());
    public static final Texture TEXTURE_GROUND = new Texture("ground.png");
    public static final Texture TEXTURE_PIPE_TOP = new Texture("pipe1.png");
    public static final Texture TEXTURE_PIPE_BOTTOM = new Texture("pipe2.png");
    public static final Texture TEXTURE_BACKGROUND = new Texture("bg.png");

    public static void dispose() {
        FONT.dispose();
        TEXTURE_ATLAS_BIRD.dispose();
        TEXTURE_GROUND.dispose();
        TEXTURE_PIPE_TOP.dispose();
        TEXTURE_PIPE_BOTTOM.dispose();
        TEXTURE_BACKGROUND.dispose();
    }
}
