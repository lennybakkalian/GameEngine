package game.ingame.gameplay.abilities;

import game.ingame.KeyboardSettings.ACTION;
import game.ingame.entity.Entity;
import game.main.Handler;

public class WatermelonHealAbility extends Ability{

	public WatermelonHealAbility(Handler handler, Entity source, ACTION keybound) {
		super(handler, 8000L, source, keybound);
	}
	
	@Override
	public void use() {
		
		super.use();
	}

}
