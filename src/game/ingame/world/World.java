package game.ingame.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import game.ingame.GameObject;
import game.ingame.world.Tile.TileProperties;
import game.main.Handler;
import game.main.Utils;
import game.pathfinding.Node;

public class World extends GameObject {

	private ArrayList<Tile> tiles;
	private Camera camera;
	private EntityManager entityManager;
	// TODO: hardcoded variable prevent glitch between two walls
	private int pathOffset = 10;

	public World(Handler handler, ArrayList<Tile> tiles) {
		super(handler);
		this.tiles = tiles;
		this.entityManager = new EntityManager(handler);

		// add default camera
		setCamera(new Camera(handler, null));

		// world size will be calculated by tileWidth*xTiles //
		// tileHeight*yTiles
		for (Tile t : tiles) {
			t.setX(t.getX() * t.getWidth());
			t.setY(t.getY() * t.getHeight());
		}
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public Tile getTileAt(int x, int y) {
		return getTileAt(x, y, 1, 1);
	}

	public Tile getTileAt(int x, int y, int width, int height) {
		for (Tile t : tiles) {
			if (t.getRect().intersects(new Rectangle(x, y, width, height)))
				return t;
		}
		return null;
	}

	public List<Tile> getTilesInArea(int x, int y, int width, int height) {
		List<Tile> result = new LinkedList<Tile>();
		for (Tile t : tiles)
			if (t.getRect().intersects(new Rectangle(x, y, width, height)))
				result.add(t);
		return result;
	}

	public List<Tile> getTilesOnLine(int startX, int startY, int endX, int endY) {
		List<Tile> result = new LinkedList<Tile>();
		double angle = Math.atan2(endX - startX, endY - startY);
		// get distance to end
		double dist = Math.sqrt((Math.abs(startX - endX) * Math.abs(startX - endX))
				+ ((Math.abs(startY - endY) * (Math.abs(startY - endY)))));
		for (int i = 1; i < (int) dist; i += 8) {
			double newX = startX + Math.sin(angle) * i;
			double newY = startY + Math.cos(angle) * i;
			List<Tile> resultTiles = getTilesInArea((int) newX - (pathOffset / 2), (int) newY - (pathOffset / 2),
					pathOffset, pathOffset);
			for (Tile t : resultTiles)
				result.add(t);
		}
		return result;
	}

	public boolean canSeeOtherTile(Tile t1, Tile t2) {
		List<Tile> tiles = getTilesOnLine((int) t1.getX() + (t1.getWidth() / 2), (int) t1.getY() + (t1.getHeight() / 2),
				(int) t2.getX() + (t2.getWidth() / 2), (int) t2.getY() + (t2.getHeight() / 2));
		for (Tile t : tiles)
			if (t.isFullySolid())
				return false;
		return true;
	}

	public boolean canSeeOtherNode(Node node1, Node node2) {
		return canSeeOtherTile(node1.tile, node2.tile);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Camera getCamera() {
		return camera;
	}

	@Override
	public void render(Graphics g) {
		// render tiles if they are in view
		int tilesRenderCount = 0;
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);
			if (t.getX() + t.getWidth() > camera.getxOffset()
					&& t.getX() < camera.getxOffset() + handler.getGame().getWidth()
					&& t.getY() + t.getHeight() > camera.getyOffset()
					&& t.getY() < camera.getyOffset() + handler.getGame().getHeight()) {
				t.render(g);
				tilesRenderCount++;
			}
		}
		entityManager.render(g);
		camera.render(g);
		Handler.debugInfoList.put(20, "rendering " + tilesRenderCount + "/" + tiles.size() + " tiles");

		// DEBUG
		Tile t = getTileAt(camera.getxOffset() + handler.getMouseX(), camera.getyOffset() + handler.getMouseY());
		if (t != null) {
			Handler.debugInfoList.put(30, "last tile: " + t.getX() + "x" + t.getY());
			g.setColor(Color.white);
			Utils.renderRect(g, t.getRenderRect());
		}

		super.render(g);
	}

	@Override
	public void tick() throws Exception {
		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).tick();
		entityManager.tick();
		camera.tick();
		super.tick();
	}

	// STATIC STUFF
	public static World flatGenerator(Handler handler, int width, int height, int tileWidth, int tileHeight) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		for (int iWidth = 0; iWidth < width; iWidth++)
			for (int iHeight = 0; iHeight < height; iHeight++) {
				tiles.add(new Tile(handler, iWidth, iHeight, tileWidth, tileHeight, Tile.tiles.get(1)));
			}
		return new World(handler, tiles);
	}

	public static World testWorldGenerator(Handler handler) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int width = 15, height = 15, tileWidth = 20, tileHeight = 20;
		for (int iWidth = 0; iWidth < width; iWidth++)
			for (int iHeight = 0; iHeight < height; iHeight++) {
				TileProperties t = Tile.tiles.get(1);
				if (iWidth == 0 || iHeight == 0 || iWidth == width - 1 || iHeight == height - 1)
					t = Tile.tiles.get(2);
				if (iWidth == 10 && iHeight > 3 && iHeight < 10)
					t = Tile.tiles.get(2);
				tiles.add(new Tile(handler, iWidth, iHeight, tileWidth, tileHeight, t));
			}
		return new World(handler, tiles);
	}

	public static World randomWorldGenerator(Handler handler) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int width = 60, height = 60, tileWidth = 20, tileHeight = 20;
		for (int iWidth = 0; iWidth < width; iWidth++)
			for (int iHeight = 0; iHeight < height; iHeight++) {
				TileProperties t = Tile.tiles.get(new Random().nextInt(30) > 2 ? 1 : 2);
				if (iWidth == 0 || iHeight == 0 || iWidth == width - 1 || iHeight == height - 1)
					t = Tile.tiles.get(2);
				tiles.add(new Tile(handler, iWidth, iHeight, tileWidth, tileHeight, t));
			}
		return new World(handler, tiles);
	}

}
