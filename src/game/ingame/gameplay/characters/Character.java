package game.ingame.gameplay.characters;

import game.ingame.entity.Entity;
import game.ingame.gameplay.abilities.Ability;
import game.main.Handler;

public class Character {

	private String name;
	private Ability[] abilities = new Ability[6];
	private Handler handler;
	private int id;

	public Character(String name, int id, Handler handler) {
		this.name = name;
		this.id = id;
		this.handler = handler;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Ability[] getAbilities() {
		return abilities;
	}

	public void useAbility(int slot, Entity source) {
		if (getAbilities()[slot] != null)
			getAbilities()[slot].use(source);
		else
			System.out.println("[Character] error while using ability. slot: " + slot);
	}

	public void registerAbility(int slot, Ability ability) {
		abilities[slot] = ability;
	}
}
