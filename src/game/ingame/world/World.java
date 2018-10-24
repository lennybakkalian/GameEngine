package game.ingame.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import game.ingame.GameObject;
import game.ingame.world.Tile.TileProperties;
import game.main.Handler;

public class World extends GameObject {

	private ArrayList<Tile> tiles;
	private Camera camera;
	private EntityManager entityManager;

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
		for (Tile t : tiles) {
			if (t.getRect().intersects(new Rectangle(x, y, 1, 1)))
				return t;
		}
		return null;
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
		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).render(g);
		entityManager.render(g);
		camera.render(g);
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
				if (iWidth == 4 && iHeight > 3 && iHeight < 10)
					t = Tile.tiles.get(2);
				tiles.add(new Tile(handler, iWidth, iHeight, tileWidth, tileHeight, t));
			}
		return new World(handler, tiles);
	}

	public static World randomWorldGenerator(Handler handler) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int width = 30, height = 30, tileWidth = 20, tileHeight = 20;
		for (int iWidth = 0; iWidth < width; iWidth++)
			for (int iHeight = 0; iHeight < height; iHeight++) {
				TileProperties t = Tile.tiles.get(new Random().nextInt(150) > 2 ? 1:2);
				if (iWidth == 0 || iHeight == 0 || iWidth == width - 1 || iHeight == height - 1)
					t = Tile.tiles.get(2);
				tiles.add(new Tile(handler, iWidth, iHeight, tileWidth, tileHeight, t));
			}
		return new World(handler, tiles);
	}

}
