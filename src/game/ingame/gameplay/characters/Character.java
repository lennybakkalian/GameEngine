package game.ingame.gameplay.characters;

import java.awt.Graphics;

import game.ingame.entity.Entity;
import game.ingame.gameplay.abilities.Ability;
import game.main.Handler;

public class Character {

	private String name;
	private int id;

	public Character(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
