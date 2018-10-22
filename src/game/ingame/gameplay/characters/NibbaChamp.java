package game.ingame.gameplay.characters;

import game.ingame.KeyboardSettings.ACTION;
import game.ingame.entity.Entity;
import game.ingame.gameplay.abilities.FireballAbility;
import game.main.Handler;

public class NibbaChamp extends Character {

	public NibbaChamp(Handler handler, Entity caster, int id) {
		super(handler, caster, "Nibba", id);
		registerAbility(new FireballAbility(handler, caster, ACTION.USE_ABILITY1));
	}

}
