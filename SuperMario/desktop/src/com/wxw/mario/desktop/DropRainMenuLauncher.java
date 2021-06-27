package com.wxw.mario.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wxw.mario.Mario;
import com.wxw.mario.other.Drop;

public class DropRainMenuLauncher {

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Drop";
        config.width = 1600;
        config.height = 900;
        new LwjglApplication(new Drop(), config);
    }
}
