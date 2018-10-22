package game.main;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventPostProcessor;
import java.awt.event.KeyEvent;
import java.io.File;

public class Main {
	
	public static void main(String[] args) {

		//int width = 1280, height = 720;
		int width = 400, height = 600;
		
		boolean fullscreen = false;
		
		Game g = new Game("", width, height, fullscreen);
		//g.RESOURCE_PATH = new File("C:\\Users\\lenny\\Desktop\\2D_GameEngine\\GameAssets");
		g.RESOURCE_PATH = new File("./assets");
		g.debug = false;
		g.start();
		
		
	}

}
