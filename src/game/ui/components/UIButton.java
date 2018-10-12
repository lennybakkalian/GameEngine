package game.ui.components;

import java.awt.Color;
import java.awt.Graphics;

import game.ui.UIAnimation;
import game.ui.UIComponent;

public class UIButton extends UIComponent {


	public UIButton(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(getAnimationColor());
		g.fillRect(x, y, width, height);
		super.render(g);
	}

	@Override
	public void tick() throws Exception {
		super.tick();
	}

}
