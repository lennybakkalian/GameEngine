package game.ingame.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.ingame.gameplay.characters.Character;
import game.main.Handler;
import game.pathfinding.Path;

public class Player extends Entity {

	public static int DEFAULT_HEALTH = 1000;
	private String username;
	private Character character;
	private boolean self = false;
	private Path currentPath;

	public Player(Handler handler, String username) {
		super(handler, DEFAULT_HEALTH);
		this.username = username;
		collisionBox = new Rectangle(0, 0, 20, 20);
	}

	public String getUsername() {
		return username;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public boolean isSelf() {
		return self;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Character getCharacter() {
		return character;
	}

	@Override
	public void tick() {
		if (character != null)
			character.tick();
		if (self) {
			// move character if there is a adviable path

		}

		if (currentPath == null)
			currentPath = new Path(handler, handler.getWorld(), getX() + (collisionBox.width / 2),
					getY() + (collisionBox.height / 2), 170, 220);

		// TODO:debug
		if (handler.getKeyManager().justPressed("q")) {
			setX(handler.getWorld().getCamera().getxOffset() + handler.getMouseX());
			setY(handler.getWorld().getCamera().getyOffset() + handler.getMouseY());
		}
		if (handler.getKeyManager().justPressed("w")) {
			int xGoal = handler.getWorld().getCamera().getxOffset() + handler.getMouseX();
			int yGoal = handler.getWorld().getCamera().getyOffset() + handler.getMouseY();

			currentPath = new Path(handler, handler.getWorld(), getX() + (collisionBox.width / 2),
					getY() + (collisionBox.height / 2), xGoal, yGoal);
		}

		if (currentPath != null)
			currentPath.tick();

		super.tick();
	}

	@Override
	public void render(Graphics g) {
		if (character != null)
			character.render(g);
		if (currentPath != null)
			currentPath.render(g);

		super.render(g);
	}

}
