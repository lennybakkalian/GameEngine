package game.ingame.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;

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
	
	public int calcXRenderPos(int xWorld) {
		return xWorld - xOffset;
	}
	
	public int calcYRenderPos(int yWorld) {
		return yWorld - yOffset;
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
		} else {
			if(handler.getMouseX() <= 5 && handler.getMouseManager().mouseSeen)
				xOffset--;
			if(handler.getMouseX() >= handler.getGame().getWidth() - 5 && handler.getMouseManager().mouseSeen)
				xOffset++;
			if(handler.getMouseY() <= 5 && handler.getMouseManager().mouseSeen)
				yOffset--;
			if(handler.getMouseY() >= handler.getGame().getHeight() - 5 && handler.getMouseManager().mouseSeen)
				yOffset++;
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
