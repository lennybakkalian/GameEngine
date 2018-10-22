package game.ingame.world;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.ingame.GameObject;
import game.main.Handler;
import game.main.Utils;

public class WorldObject extends GameObject {

	// offset = camera pos
	public int xRenderPos = 0, yRenderPos = 0;

	public Rectangle collisionBox = new Rectangle(0, 0, 0, 0);
	

	public WorldObject(Handler handler) {
		super(handler);
	}

	@Override
	public void tick() {
		if (handler.getWorld() != null) {
			xRenderPos = getX() - handler.getWorld().getCamera().getxOffset();
			yRenderPos = getY() - handler.getWorld().getCamera().getyOffset();
		}
	}
	
	@Override
	public void render(Graphics g) {
		Rectangle renderCollisionBox = new Rectangle(0, 0, 0, 0);
		renderCollisionBox.x = xRenderPos + collisionBox.x;
		renderCollisionBox.y = yRenderPos + collisionBox.y;
		renderCollisionBox.width = collisionBox.width;
		renderCollisionBox.height = collisionBox.height;
		Utils.renderRect(g, renderCollisionBox);
		super.render(g);
	}
	
}
