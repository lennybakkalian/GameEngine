package game.ingame.world;

import java.awt.Graphics;
import java.util.ArrayList;

import game.ingame.GameObject;
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

		// world size will be calculated by tileWidth*xTiles // tileHeight*yTiles
		for (Tile t : tiles) {
			t.setX(t.getX() * t.getWidth());
			t.setY(t.getY() * t.getHeight());
		}
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
				tiles.add(new Tile(handler, iWidth, iHeight, tileWidth, tileHeight, handler.getResource("grass")));
			}
		return new World(handler, tiles);
	}

}
