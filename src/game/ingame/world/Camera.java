package game.ingame.world;

import java.awt.Graphics;
import java.awt.MouseInfo;
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
			int mco = handler.getGame().moveCameraOffset;
			int frameX = handler.getGame().getFrameRect().x;
			int frameY = handler.getGame().getFrameRect().y;
			int frameWidth = handler.getGame().getWidth();
			int frameHeight = handler.getGame().getHeight();

			if (handler.getMouseX() <= mco && handler.getMouseManager().mouseSeen)
				xOffset -= handler.getGame().cameraSpeed;
			if (handler.getMouseX() >= handler.getGame().getWidth() - mco && handler.getMouseManager().mouseSeen)
				xOffset += handler.getGame().cameraSpeed;
			if (handler.getMouseY() <= mco && handler.getMouseManager().mouseSeen)
				yOffset -= handler.getGame().cameraSpeed;
			if (handler.getMouseY() >= handler.getGame().getHeight() - mco && handler.getMouseManager().mouseSeen)
				yOffset += handler.getGame().cameraSpeed;

			// check if mouse is outside frame
			if (handler.getGame().getDisplay().getFrame().isFocused()) {
				// x<
				if (MouseInfo.getPointerInfo().getLocation().x < frameX + mco)
					handler.getGame().robot.mouseMove(frameX + mco, MouseInfo.getPointerInfo().getLocation().y);
				// x>
				if (MouseInfo.getPointerInfo().getLocation().x > frameX + handler.getGame().getWidth() - mco)
					handler.getGame().robot.mouseMove(
							handler.getGame().getFrameRect().x + handler.getGame().getWidth() - mco + 10,
							MouseInfo.getPointerInfo().getLocation().y);
				// y<
				if (MouseInfo.getPointerInfo().getLocation().y < handler.getGame().getFrameRect().y + mco)
					handler.getGame().robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x,
							handler.getGame().getFrameRect().y + mco);
				// y>
				if (MouseInfo.getPointerInfo().getLocation().y < handler.getGame().getFrameRect().y
						+ handler.getGame().getHeight() - mco)
					handler.getGame().robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x,
							handler.getGame().getFrameRect().y + handler.getGame().getHeight() - mco + 10);

			}

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
