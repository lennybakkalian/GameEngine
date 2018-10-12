package game.ingame.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.main.Handler;
import game.resources.Resource;

public class Tile extends WorldObject {

	private int width, height;
	private Resource texture;
	private Rectangle solidRect;

	public Tile(Handler handler, int x, int y, int width, int height, Resource texture) {
		super(handler);
		setX(x);
		setY(y);
		this.width = width;
		this.height = height;
		this.texture = texture;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setSolidRect(Rectangle solidRect) {
		this.solidRect = solidRect;
	}

	public Rectangle getSolidRect() {
		return solidRect;
	}

	public void setTexture(Resource texture) {
		this.texture = texture;
	}

	public Resource getTexture() {
		return texture;
	}

	@Override
	public void render(Graphics g) {
		if (texture != null) {
			g.drawImage(texture.getImage(), xRenderPos, yRenderPos, width, height, null);
		} else {
			g.setColor(Color.PINK);
			g.fillRect(xRenderPos, yRenderPos, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(xRenderPos, yRenderPos, width, height);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			String errText = " ! ";
			g.fillRect(xRenderPos, yRenderPos, g.getFontMetrics().stringWidth(errText), 25);
			g.setColor(Color.RED);
			g.drawString(errText, xRenderPos, yRenderPos + 20);
			
		}
	}

}
