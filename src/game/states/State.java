package game.states;

import java.awt.Graphics;

import game.main.Handler;
import game.ui.UIHandler;

public abstract class State {
	
	public Handler handler;
	public UIHandler uiHandler;
	
	public State(Handler handler) {
		this.handler = handler;
		this.uiHandler = new UIHandler(handler);
	}
	
	public abstract void tick() throws Exception;
	public abstract void render(Graphics g);
}
