package game.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.ingame.gameplay.characters.Character;
import game.ingame.world.Tile;
import game.main.Handler;
import game.main.Utils;
import game.pathfinding.Node;
import game.pathfinding.Path;

public class Player extends Entity {

	public static int DEFAULT_HEALTH = 1000;
	private String username;
	private Character character;
	private boolean self = false;

	// pathfinding
	private boolean followPath = false;
	private Path currentPath;
	private int currentNode = 0;

	public Player(Handler handler, String username) {
		super(handler, DEFAULT_HEALTH);
		this.username = username;
		collisionBox = new Rectangle(0, 0, 20, 20);
		debugRender = true;
	}

	public Rectangle getPositionCollisionBox() {
		return new Rectangle((int) getX() + collisionBox.x, (int) getY() + collisionBox.y, collisionBox.width,
				collisionBox.height);
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
			currentPath = new Path(handler, handler.getWorld(), (int) getX() + (collisionBox.width / 2),
					(int) getY() + (collisionBox.height / 2), 170, 220);

		// TODO:debug
		if (handler.getKeyManager().justPressed("q")) {
			setX(handler.getWorld().getCamera().getxOffset() + handler.getMouseX());
			setY(handler.getWorld().getCamera().getyOffset() + handler.getMouseY());
		}
		if (handler.getKeyManager().justPressed("w")) {
			int xGoal = handler.getWorld().getCamera().getxOffset() + handler.getMouseX();
			int yGoal = handler.getWorld().getCamera().getyOffset() + handler.getMouseY();

			currentPath = new Path(handler, handler.getWorld(), (int) getX() + (collisionBox.width / 2),
					(int) getY() + (collisionBox.height / 2), xGoal, yGoal);
			if (currentPath != null) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						followPath = false;
						currentPath.tick();
						followPath = true;
						currentNode = 0;
					}
				}).start();
			}

		}

		super.tick();
	}

	@Override
	public void render(Graphics g) {
		if (character != null)
			character.render(g);
		if (currentPath != null)
			currentPath.render(g);

		g.setColor(Color.red);
		Utils.fillRect(g, new Rectangle(xRenderPos + collisionBox.x, yRenderPos + collisionBox.y, collisionBox.width,
				collisionBox.height));

		// follow path
		if (followPath && currentPath.isDone() && currentPath.getShortestPath().size() > 0) {
			if (currentNode < currentPath.getShortestPath().size()) {
				Node n = currentPath.getShortestPath().get(currentNode);
				// check if player is on current node
				if (getPositionCollisionBox().intersects(n.tile.getRect())) {
					// set next node
					System.out.println("n");
					if (currentNode < currentPath.getShortestPath().size())
						currentNode++;
					else
						System.out.println("end");
				} else {
					// follow path
					double endX = n.tile.getX();
					double endY = n.tile.getY();
					double startX = getX();
					double startY = getY();
					double speed = 1;
					double angle = Math.atan2(endX - startX, endY - startY);
					// get distance to end
					double dist = Math.sqrt((Math.abs(startX - endX) * Math.abs(startX - endX))
							+ ((Math.abs(startY - endY) * (Math.abs(startY - endY)))));
					double newX = startX + Math.sin(angle) * speed;
					double newY = startY + Math.cos(angle) * speed;
					// check if collide with tile
					boolean moveX = true, moveY = true;

					for (Tile t : currentPath.getWorld().getTiles()) {
						if (t.isFullySolid()
								&& new Rectangle((int) newX, (int) getY(), collisionBox.width, collisionBox.height)
										.intersects(t.getRect())) {
							moveX = false;
						}
						if (t.isFullySolid()
								&& new Rectangle((int) getX(), (int) newY, collisionBox.width, collisionBox.height)
										.intersects(t.getRect())) {
							moveY = false;
						}

					}
					if (moveX)
						setX(newX);
					if (moveY)
						setY(newY);

					g.drawLine(
							(int) (getX() + collisionBox.getWidth() / 2) - handler.getWorld().getCamera().getxOffset(),
							(int) (getY() + collisionBox.getHeight() / 2) - handler.getWorld().getCamera().getyOffset(),
							(int) (endX + n.tile.getWidth() / 2) - handler.getWorld().getCamera().getxOffset(),
							(int) (endY + n.tile.getHeight() / 2) - handler.getWorld().getCamera().getyOffset());
				}
			} else {
				System.out.println("n is null");
			}
		}

		super.render(g);
	}

}
