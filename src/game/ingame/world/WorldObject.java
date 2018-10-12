package game.ingame.world;

import game.ingame.GameObject;
import game.main.Handler;

public class WorldObject extends GameObject {

	// offset = camera pos
	public int xRenderPos = 0, yRenderPos = 0;

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
}
