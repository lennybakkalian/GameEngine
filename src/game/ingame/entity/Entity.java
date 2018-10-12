package game.ingame.entity;

import game.ingame.world.WorldObject;
import game.main.Handler;

public abstract class Entity extends WorldObject {
	private int health = 1000;

	public Entity(Handler handler, int health) {
		super(handler);
	}

	public int getHealth() {
		return health;
	}
}
