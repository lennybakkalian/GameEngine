package game.ingame;

import java.awt.Graphics;

import game.main.Handler;
import game.states.State;

public class GameObject extends CoordinateObject{

	public Handler handler;
	
	public GameObject(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() throws Exception{

	}
	
	public void render(Graphics g) {

	}

}
