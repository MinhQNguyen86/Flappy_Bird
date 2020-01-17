package com.kingsman.flappy.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kingsman.flappy.FlappyBird;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = FlappyBird.V_HEIGHT;
		config.width = FlappyBird.V_WIDTH;
		config.title = FlappyBird.TITLE;
		config.resizable = false;
		config.addIcon("icon-128.png", Files.FileType.Internal);
		config.addIcon("icon-32.png", Files.FileType.Internal);
		new LwjglApplication(new FlappyBird(), config);
	}
}
