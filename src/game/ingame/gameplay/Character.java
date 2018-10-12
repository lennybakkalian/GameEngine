package game.ingame.gameplay;

import game.ingame.gameplay.abilities.Ability;
import game.resources.ResourceStorage;

public class Character {
	
	
	private String name;
	private ResourceStorage resources;
	private Ability[] abilities = new Ability[6];
	
	public Character(String name, ResourceStorage resources) {
		this.name = name;
		this.resources = resources;
	}
	
	public String getName() {
		return name;
	}
	
	public ResourceStorage getResources() {
		return resources;
	}
	
	public Ability[] getAbilities() {
		return abilities;
	}
	
	public void registerAbility(int slot, Ability ability) {
		abilities[slot] = ability;
	}
}
