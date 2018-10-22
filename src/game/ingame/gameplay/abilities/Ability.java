package game.ingame.gameplay.abilities;

import java.awt.Graphics;

import game.ingame.KeyboardSettings;
import game.ingame.KeyboardSettings.ACTION;
import game.ingame.entity.Entity;
import game.ingame.world.WorldObject;
import game.main.Handler;
import game.resources.Resource;

public class Ability extends WorldObject {

	private Long cooldown;
	private Long lastUse = 0L;
	private Resource inventarSplashArt;
	public Entity caster;
	public ACTION keybound;

	public Ability(Handler handler, Long cooldown, Entity caster, ACTION keybound) {
		super(handler);
		this.cooldown = cooldown;
		this.caster = caster;
		this.keybound = keybound;
	}

	public void use() {

	}

	public void requestUse() {
		Long currentTime = System.currentTimeMillis();
		if(currentTime > lastUse + cooldown) {
			lastUse = currentTime;
			System.out.println("USE");
		}
		System.out.println(lastUse + cooldown - currentTime);
	}

	public Resource getInventarSplashArt() {
		return inventarSplashArt;
	}

	public boolean canUse() {
		return lastUse + cooldown < System.currentTimeMillis();
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().justPressed(KeyboardSettings.keybounds.get(keybound))) {
			requestUse();
		}
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}

}
