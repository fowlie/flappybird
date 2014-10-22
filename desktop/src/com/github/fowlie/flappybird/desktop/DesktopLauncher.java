package com.github.fowlie.flappybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.fowlie.flappybird.FlappyBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "FlappyBird";
        config.width = 288;
        config.height = 441;
		new LwjglApplication(new FlappyBird(config.width, config.height), config);
	}
}
