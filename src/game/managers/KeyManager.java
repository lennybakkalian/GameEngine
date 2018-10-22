package game.managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import game.main.Handler;
import game.other.TickClass;

public class KeyManager implements KeyListener, TickClass {

	private boolean[] pressedKeys, justPressed, hasPressed;
	private HashMap<String, Integer> charToKeyCode;
	private Handler handler;

	public KeyManager(Handler handler) {
		this.handler = handler;
		pressedKeys = new boolean[1000];
		justPressed = new boolean[1000];
		hasPressed = new boolean[1000];
		this.charToKeyCode = new HashMap<String, Integer>();
	}

	public boolean[] getPressedKeys() {
		return pressedKeys;
	}

	public boolean charPressed(String character) {
		if (charToKeyCode.get(character.toLowerCase()) != null) {
			return pressedKeys[charToKeyCode.get(character.toLowerCase())];
		}
		return false;
	}

	public boolean justPressed(String character) {
		return charToKeyCode.get(character.toLowerCase()) != null
				&& justPressed[charToKeyCode.get(character.toLowerCase())];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			pressedKeys[e.getKeyCode()] = true;
			if (charToKeyCode.get(String.valueOf(e.getKeyChar())) == null && e.getKeyChar() < pressedKeys.length) {
				charToKeyCode.put(String.valueOf(e.getKeyChar()).toLowerCase(), e.getKeyCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (handler.getCurrentUIHandler() != null)
			handler.getCurrentUIHandler().keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys[e.getKeyCode()] = false;
		if (handler.getCurrentUIHandler() != null)
			handler.getCurrentUIHandler().keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void tick() {
		for (int i = 0; i < pressedKeys.length; i++) {
			justPressed[i] = false;
			if (pressedKeys[i]) {
				if (!hasPressed[i]) {
					justPressed[i] = true;
				}
				hasPressed[i] = true;
			} else {
				hasPressed[i] = false;
			}
		}
	}
}
