package game.ingame.world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;

import game.main.Handler;
import game.resources.Resource;

public class Tile extends WorldObject {

	public static HashMap<Integer, TileProperties> tiles = new HashMap<Integer, TileProperties>() {
		{
			put(1, new TileProperties(1, "grass", false));
			put(2, new TileProperties(2, "rock", true));

		}
	};

	public static class TileProperties {
		public int id;
		public String resourcename;
		public boolean solid;

		public TileProperties(int id, String resourcename, boolean solid) {
			this.id = id;
			this.resourcename = resourcename;
			this.solid = solid;
		}
	}

	private int width, height;
	private TileProperties properties;
	private Rectangle solidRect;
	private Resource texture;
	private boolean fullySolid;

	public Tile(Handler handler, int x, int y, int width, int height, TileProperties properties) {
		super(handler);
		setX(x);
		setY(y);
		this.width = width;
		this.height = height;
		this.properties = properties;
		this.fullySolid = properties.solid;
		try {
			this.texture = handler.getResource(properties.resourcename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isFullySolid() {
		return fullySolid;
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

	public Rectangle getRect() {
		return new Rectangle(getX(), getY(), width, height);
	}

	public Rectangle getRenderRect() {
		return new Rectangle(xRenderPos, yRenderPos, width, height);
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
