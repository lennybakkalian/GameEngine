package game.ingame.gameplay.characters;

import game.ingame.gameplay.abilities.FireballAbility;
import game.main.Handler;

public class NibbaChamp extends Character {

	public NibbaChamp(Handler handler) {
		super("Nibba", 1, handler);

		registerAbility(0, new FireballAbility(handler));
		
	}

}
