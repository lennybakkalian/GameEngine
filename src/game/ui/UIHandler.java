package game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

import game.ingame.GameObject;
import game.main.Handler;
import game.main.Utils;
import game.ui.components.UIInputField;

public class UIHandler extends GameObject {

	private Stack<UIComponent> comps;
	private ArrayList<UIComponent> addQue;
	private ArrayList<UIComponent> removeQue;

	private UIComponent focusedComponent;

	public UIHandler(Handler handler) {
		super(handler);
		this.comps = new Stack<UIComponent>();
		this.addQue = new ArrayList<UIComponent>();
		this.removeQue = new ArrayList<UIComponent>();
	}

	public void addComp(UIComponent comp) {
		comp.setHandler(handler);
		this.addQue.add(comp);
	}

	public void removeComp(UIComponent comp) {
		if (this.focusedComponent == comp)
			this.focusedComponent = null;
		this.removeQue.add(comp);
	}

	@Override
	public void tick() throws Exception {
		if (addQue.size() > 0) {
			for (int i = 0; i < addQue.size(); i++)
				comps.push(addQue.get(i));
			addQue.clear();
		}
		if (removeQue.size() > 0) {
			for (int i = 0; i < removeQue.size(); i++)
				comps.remove(removeQue.get(i));
			removeQue.clear();
		}

		for (int i = 0; i < comps.size(); i++) {
			comps.get(i).tick();
			if (comps.get(i) instanceof UIInputField && ((UIInputField) comps.get(i)).isFocused())
				this.focusedComponent = comps.get(i);
		}
	}

	@Override
	public void render(Graphics g) {
		for (int i = 0; i < comps.size(); i++) {
			UIComponent comp = comps.get(i);
			comp.render(g);

			if (handler.getGame().debug) {
				// render lines (debug)
				g.setColor(Color.GREEN);
				g.drawRect(comp.getOriginSize().x, comp.getOriginSize().y, comp.getOriginSize().width,
						comp.getOriginSize().height);
				g.drawString(comp.getOriginSize().height + "px", comp.getOriginSize().x + 5,
						comp.getOriginSize().y + comp.getOriginSize().height / 2);
				Utils.drawCenterString(g, comp.getOriginSize().width + "px",
						new Rectangle(comp.getOriginSize().x, comp.getOriginSize().y, comp.getOriginSize().width, 15));

				// draw outer rect
				if (!Utils.isSameSize(comp.getRect(), comp.getOriginSize())) {
					g.drawRect(comp.x, comp.y, comp.width, comp.height);
					g.drawString(comp.height + "px", comp.x + 5, comp.y + comp.height / 2 + 15);
					Utils.drawCenterString(g, comp.width + "px", new Rectangle(comp.x, comp.y, comp.width + 70, 15));
				}

				// draw lines
				g.drawLine(comp.x, comp.y, comp.getOriginSize().x, comp.getOriginSize().y);
				g.drawLine(comp.x, comp.y + comp.height, comp.getOriginSize().x,
						comp.getOriginSize().y + comp.getOriginSize().height);
				g.drawLine(comp.x + comp.width, comp.y, comp.getOriginSize().x + comp.getOriginSize().width,
						comp.getOriginSize().y);
				g.drawLine(comp.x + comp.width, comp.y + comp.height,
						comp.getOriginSize().x + comp.getOriginSize().width,
						comp.getOriginSize().y + comp.getOriginSize().height);
			}
		}
	}

	public void onMouseMove(MouseEvent e) {
		try {
			boolean firstLayer = true;
			for (int i = comps.size() - 1; i >= 0; i--) {
				UIComponent comp = comps.get(i);
				if ((firstLayer || comp.isIgnoreLayers())
						&& new Rectangle(e.getX(), e.getY(), 1, 1).intersects(comp.getOriginSize())) {
					comp.setHoover(true);
					comp.onMouseMove(e);
					if (!comp.isIgnoreLayers())
						firstLayer = false;
				} else {
					comp.setHoover(false);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void onMouseDrag(MouseEvent e) {
		try {
			boolean firstLayer = true;
			for (int i = comps.size() - 1; i >= 0; i--) {
				UIComponent comp = comps.get(i);
				if ((firstLayer || comp.isIgnoreLayers())
						&& new Rectangle(e.getX(), e.getY(), 1, 1).intersects(comp.getOriginSize())) {
					comp.setHoover(true);
					comp.onMouseDrag(e);
					if (!comp.isIgnoreLayers())
						firstLayer = false;
				} else {
					comp.setHoover(false);
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void onMouseClick(MouseEvent e) {
		try {
			boolean firstLayer = true;
			for (int i = comps.size() - 1; i >= 0; i--) {
				UIComponent comp = comps.get(i);
				if ((firstLayer || comp.isIgnoreLayers())
						&& new Rectangle(e.getX(), e.getY(), 1, 1).intersects(comp.getOriginSize())) {
					comp.onMouseClick(e);
					if (!comp.isIgnoreLayers())
						firstLayer = false;
				}
			}

			// check if mouse leave focused element
			if (focusedComponent != null
					&& !new Rectangle(e.getX(), e.getY(), 1, 1).intersects(focusedComponent.getRect()))
				focusedComponent = null;
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}

	public void onMouseRelease(MouseEvent e) {
		try {
			boolean firstLayer = true;
			boolean focusedSelected = false;
			for (int i = 0; i < comps.size(); i++) {
				UIComponent comp = comps.get(i);
				if ((firstLayer || comp.isIgnoreLayers())
						&& new Rectangle(e.getX(), e.getY(), 1, 1).intersects(comp.getRect())) {
					comp.onMouseRelease(e);
					if (!comp.isIgnoreLayers())
						firstLayer = false;
					if (!focusedSelected) {
						comp.setFocused(true);
						continue;
					}
				}
				comp.setFocused(false);
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO: continue here
		if (focusedComponent != null)
			focusedComponent.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		if (focusedComponent != null)
			focusedComponent.keyReleased(e);
	}
}
