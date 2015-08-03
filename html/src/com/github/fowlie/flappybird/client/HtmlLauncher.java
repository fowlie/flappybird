package com.github.fowlie.flappybird.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.fowlie.flappybird.FlappyBird;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(288, 441);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new FlappyBird();
        }
}