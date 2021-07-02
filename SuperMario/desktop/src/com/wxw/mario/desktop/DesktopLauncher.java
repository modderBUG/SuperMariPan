package com.wxw.mario.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wxw.mario.Mario;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Super Mario";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Mario(), config);
	}
}
