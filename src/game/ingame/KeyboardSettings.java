package game.ingame;

import java.util.HashMap;

public class KeyboardSettings {

	public static enum ACTION {
		MOVE_FORWARD, MOVE_BACK, MOVE_LEFT, MOVE_RIGHT, USE_ABILITY1, USE_ABILITY2, USE_ABILITY3, USE_ABILITY4,

	}

	public static HashMap<ACTION, String> keybounds = new HashMap<ACTION, String>() {
		{
			put(ACTION.USE_ABILITY1, "1");
			put(ACTION.USE_ABILITY2, "2");
			put(ACTION.USE_ABILITY3, "3");
			put(ACTION.USE_ABILITY4, "4");
		}
	};
}
