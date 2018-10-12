package game.managers;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import game.main.Handler;
import game.other.TickClass;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener, TickClass {

	private Handler handler;
	private boolean[] pressedButtons = new boolean[2];
	private boolean leftBtn = false, rightBtn = false;
	public Rectangle currentPos = new Rectangle(0, 0, 1, 1);

	public MouseManager(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void tick() {
		leftBtn = false;
		rightBtn = false;
	}

	public boolean leftButtonJustPressed() {
		return leftBtn;
	}

	public boolean rightButtonJustPressed() {
		return rightBtn;
	}

	public boolean leftButtonPressed() {
		return pressedButtons[1];
	}

	public boolean rightButtonPressed() {
		return pressedButtons[2];
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

		if (handler.getCurrentUIHandler() != null)
			handler.getCurrentUIHandler().onMouseDrag(arg0);
		currentPos.x = arg0.getX();
		currentPos.y = arg0.getY();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (handler.getCurrentUIHandler() != null)
			handler.getCurrentUIHandler().onMouseMove(arg0);
		currentPos.x = arg0.getX();
		currentPos.y = arg0.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (handler.getCurrentUIHandler() != null)
			handler.getCurrentUIHandler().onMouseClick(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		try {
			pressedButtons[arg0.getButton()] = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[MouseManager.java] Invalid button pressed");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (handler.getCurrentUIHandler() != null)
			handler.getCurrentUIHandler().onMouseRelease(arg0);

		try {
			pressedButtons[arg0.getButton()] = false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[MouseManager.java] Invalid button pressed");
		}
		if (arg0.getButton() == 1) {
			leftBtn = true;
		} else if (arg0.getButton() == 3) {
			rightBtn = true;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	}

}
