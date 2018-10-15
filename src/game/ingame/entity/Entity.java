package game.ingame.entity;

import game.ingame.gameplay.abilities.Ability;
import game.ingame.world.WorldObject;
import game.main.Handler;

public abstract class Entity extends WorldObject {
	private int health = 1000;
	private Ability[] abilities = new Ability[6];

	public Entity(Handler handler, int health) {
		super(handler);
	}
	
	public Ability[] getAbilities() {
		return abilities;
	}

	public void useAbility(int slot) {
		if (getAbilities()[slot] != null)
			getAbilities()[slot].use();
		else
			System.out.println("[Character] error while using ability. slot: " + slot);
	}

	public void registerAbility(int slot, Ability ability) {
		abilities[slot] = ability;
	}

	public int getHealth() {
		return health;
	}
}
