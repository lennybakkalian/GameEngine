package game.ingame.gameplay.abilities;

import java.awt.Graphics;

import game.ingame.KeyboardSettings.ACTION;
import game.ingame.entity.Entity;
import game.main.Handler;
import game.main.Utils;

public class FireballAbility extends Ability {

	public FireballAbility(Handler handler, Entity source, ACTION keybound) {
		super(handler, 5000L, source, keybound);
		collisionBox.setBounds(0, 0, 10, 10);
	}

	@Override
	public void use() {

		super.use();
	}

	@Override
	public void tick() {
		if (handler.getKeyManager().justPressed("e"))
			requestUse();
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}
}
