package game.ingame.gameplay.abilities;

import java.awt.Graphics;

import game.ingame.entity.Entity;
import game.ingame.world.WorldObject;
import game.main.Handler;
import game.resources.Resource;

public class Ability extends WorldObject {

	private float cooldown;
	private Long lastUse = 0L;
	private Resource inventarSplashArt;

	public Ability(Handler handler, float cooldown) {
		super(handler);
		this.cooldown = cooldown;
	}

	public void use(Entity source) {
		if (cooldown <= 0L) {
			
		}
	}

	public Resource getInventarSplashArt() {
		return inventarSplashArt;
	}

	public boolean canUse() {
		return lastUse + cooldown < System.currentTimeMillis();
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

}
