package game.ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.ui.UIAnimation;
import game.ui.UIComponent;

public class UIInputField extends UIComponent {

	private String charPlaceholder = null, text = "";
	private Font font;

	public UIInputField(int x, int y, int width, int height) {
		super(x, y, width, height);
		font = new Font("Arial", Font.BOLD, height);
		ActiveInputAnimation activeInputAnim = new ActiveInputAnimation();
		activeInputAnim.init(this);
		activeInputAnim.setInfinite();
		addAnimation(AnimationType.INPUT_ACTIVE, activeInputAnim);
	}

	@Override
	public void render(Graphics g) {

		g.setColor(getAnimationColor());

		Graphics2D g2d = (Graphics2D) g;
		g2d.setClip(x - 1, y - 1, width + 2, height + 2);
		g2d.setStroke(new BasicStroke(2F));
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g2d.setStroke(new BasicStroke(1F));
		g.setFont(font);
		
		FontMetrics fm = g.getFontMetrics(font);
		int xRenderText = x;
		if(fm.stringWidth(text) > width)
			xRenderText = x + (width - fm.stringWidth(text));
		g2d.drawString(text, xRenderText, y + font.getSize() - 5);
		g2d.setClip(null);
		
		renderAnimByType(AnimationType.INPUT_ACTIVE, g);

		super.render(g);
	}

	@Override
	public void tick() throws Exception {
		if (focused)
			callbackByAnimType(AnimationType.INPUT_ACTIVE, new AnimTask() {
				@Override
				public void doTask(UIAnimation anim) {
					((ActiveInputAnimation) anim).focused = true;
				}
			});
		else
			callbackByAnimType(AnimationType.INPUT_ACTIVE, new AnimTask() {
				@Override
				public void doTask(UIAnimation anim) {
					((ActiveInputAnimation) anim).focused = false;
				}
			});
		super.tick();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		// shift
		case 16:
		// capslock
		case 20:
		// alt gr
		case 17:
		case 18:
		// tab
		case 9:
			break;
		case 8:
			if (text.length() > 0)
				text = text.substring(0, text.length() - 1);
			break;
		default:
			text += e.getKeyChar();
		}
		System.out.println(e.getKeyCode());
		super.keyPressed(e);
	}

	private class ActiveInputAnimation extends UIAnimation {

		private boolean show = false;
		public boolean focused = true;

		public ActiveInputAnimation() {
			super(40, null, null);
		}

		@Override
		public void render(Graphics g) {
			if (getCounter() == getDuration()) {
				show = !show;
			}
			if (show && focused) {
				FontMetrics fm = g.getFontMetrics(font);
				int textwidth = fm.stringWidth(text);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(2F));
				g2d.drawLine(x + 5 + textwidth, y + 2, x + 5 + textwidth, y + height - 4);
			}
			super.render(g);
		}

	}

}
