package game.ingame.world;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.ingame.GameObject;
import game.main.Handler;

public class Camera extends GameObject {

	private Handler handler;
	private GameObject focusedElement;
	private Rectangle viewArea;
	private int viewAreaSize = 1;
	private int xOffset, yOffset;
	private boolean cameraLocked = true;

	public Camera(Handler handler, GameObject focusedElement) {
		super(handler);
		this.handler = handler;
		this.focusedElement = focusedElement;
	}

	@Override
	public void tick() {
		int dWidth = handler.getGame().getWidth();
		int dHeight = handler.getGame().getHeight();

		// debug
		cameraLocked = false;

		if (cameraLocked) {
			// update viewArea
			viewArea = new Rectangle(dWidth / 2 - viewAreaSize / 2, dHeight / 2 - viewAreaSize / 2, viewAreaSize,
					viewAreaSize);
		}
	}

	@Override
	public void render(Graphics g) {
		if (viewArea != null) {
			// debug render
			g.drawRect(viewArea.x, viewArea.y, viewArea.width, viewArea.height);
		}
		super.render(g);
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

}
