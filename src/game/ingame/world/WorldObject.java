package game.ingame.world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.ingame.GameObject;
import game.main.Handler;
import game.main.Utils;

public class WorldObject extends GameObject {

	// offset = camera pos
	public int xRenderPos = 0, yRenderPos = 0;
	public boolean debugRender = false;

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
		if (!debugRender)
			return;
		Rectangle renderCollisionBox = new Rectangle(0, 0, 0, 0);
		renderCollisionBox.x = xRenderPos + collisionBox.x;
		renderCollisionBox.y = yRenderPos + collisionBox.y;
		renderCollisionBox.width = collisionBox.width;
		renderCollisionBox.height = collisionBox.height;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(5F));
		g2d.setColor(Color.red);
		Utils.renderRect(g, renderCollisionBox);
		g2d.setStroke(new BasicStroke(2F));
		g.drawLine(xRenderPos, 0, xRenderPos, yRenderPos);
		g.drawLine(0, yRenderPos, xRenderPos, yRenderPos);
		super.render(g);
	}

}
