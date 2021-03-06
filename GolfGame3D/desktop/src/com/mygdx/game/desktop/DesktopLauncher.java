package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ScreenSpace;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ScreenSpace.WIDTH;
		config.height = ScreenSpace.HEIGHT;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;

		config.resizable = false;
		new LwjglApplication(new ScreenSpace(), config);
	}
}
