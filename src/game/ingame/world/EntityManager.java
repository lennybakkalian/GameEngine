package game.ingame.world;

import java.awt.Graphics;
import java.util.ArrayList;

import game.ingame.entity.Entity;
import game.ingame.entity.Player;
import game.ingame.gameplay.characters.Character;
import game.ingame.gameplay.characters.NibbaChamp;
import game.main.Handler;
import game.other.RenderClass;
import game.other.TickClass;

public class EntityManager implements TickClass, RenderClass {

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> addQue = new ArrayList<Entity>();
	private ArrayList<Entity> removeQue = new ArrayList<Entity>();
	private Player self;
	private Handler handler;
	private boolean renderEntities = true, isRendering = false;

	public EntityManager(Handler handler) {
		this.handler = handler;
		// TODO: add username
		this.self = new Player(handler, "Test");
		this.self.setCharacter(Character.getNewInstanceById(handler, this.self, 1));
		this.self.setSelf(true);
		registerEntity(this.self);
	}

	public void registerEntity(Entity entity) {
		addQue.add(entity);
	}

	public void removeEntity(Entity entity) {
		removeQue.add(entity);
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
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
