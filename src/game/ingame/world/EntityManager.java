package game.ingame.world;

import java.awt.Graphics;
import java.util.ArrayList;

import game.ingame.entity.Entity;
import game.main.Handler;
import game.other.RenderClass;
import game.other.TickClass;

public class EntityManager implements TickClass, RenderClass {

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> addQue = new ArrayList<Entity>();
	private ArrayList<Entity> removeQue = new ArrayList<Entity>();
	private boolean renderEntities = true, isRendering = false;

	public EntityManager(Handler handler) {

	}

	public void registerEntity(Entity entity) {
		addQue.add(entity);
	}

	public void removeEntity(Entity entity) {
		removeQue.add(entity);
	}

	@Override
	public void tick() {

		if (addQue.size() > 0 || removeQue.size() > 0) {
			renderEntities = false;
			if (!isRendering) {
				// if not currently rendering, do changes
				entities.addAll(addQue);
				entities.removeAll(removeQue);
				addQue.clear();
				removeQue.clear();
				renderEntities = true;
			}
		}

		if (renderEntities)
			for (Entity e : entities)
				e.tick();
	}

	@Override
	public void render(Graphics g) {
		if (renderEntities) {
			isRendering = true;
			for (Entity e : entities)
				e.render(g);
			isRendering = false;
		}
	}
}
