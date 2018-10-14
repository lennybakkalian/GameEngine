package game.main;

import game.ingame.world.EntityManager;
import game.ingame.world.World;
import game.managers.KeyManager;
import game.managers.MouseManager;
import game.resources.Resource;
import game.ui.UIHandler;

public class Handler {

	private Game game;
	private World world;

	public Handler(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getMouseX() {
		return getMouseManager().currentPos.x;
	}

	public int getMouseY() {
		return getMouseManager().currentPos.y;
	}
	
	public EntityManager getEntityManager() {
		return getWorld().getEntityManager();
	}
	
	public Resource getResource(String name) {
		Resource res = game.getResources().getByName(name);
		if (res == null) {
			return game.getResources().getByFileName(name);
		}
		return res;
	}

	public MouseManager getMouseManager() {
		return game.mouseManager;
	}

	public KeyManager getKeyManager() {
		return game.keyManager;
	}

	public UIHandler getCurrentUIHandler() {
		return game.currentUIHandler;
	}

}
