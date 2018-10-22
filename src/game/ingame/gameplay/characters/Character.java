package game.ingame.gameplay.characters;

import java.awt.Graphics;
import java.util.ArrayList;

import game.ingame.entity.Entity;
import game.ingame.gameplay.abilities.Ability;
import game.main.Handler;
import game.other.RenderClass;
import game.other.TickClass;

public class Character implements TickClass, RenderClass {

	private String name;
	private int id;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	private Handler handler;
	private Entity caster;

	public Character(Handler handler, Entity caster, String name, int id) {
		this.name = name;
		this.id = id;
		this.handler = handler;
		this.caster = caster;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void registerAbility(Ability a) {
		abilities.add(a);
	}

	public void removeAbility(Ability a) {
		abilities.remove(a);
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public void tick() {
		for (Ability a : abilities)
			a.tick();
	}

	@Override
	public void render(Graphics g) {
		for (Ability a : abilities)
			a.render(g);
	}

	// static stuff
	public static Character getNewInstanceById(Handler handler, Entity caster, int id) {
		switch (id) {
		case 1:
			return new NibbaChamp(handler, caster, id);
		case 2:
			break;
		}
		return null;
	}
}
