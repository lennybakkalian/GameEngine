package game.ingame.entity;

import java.awt.Graphics;

import game.main.Handler;

public class Player extends Entity{

	public static int DEFAULT_HEALTH = 1000;
	private String username;
	
	public Player(Handler handler, String username) {
		super(handler, DEFAULT_HEALTH);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public void render(Graphics g) {
		
		super.render(g);
	}

}
