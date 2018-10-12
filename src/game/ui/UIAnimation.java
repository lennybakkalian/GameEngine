package game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class UIAnimation {

	private UIComponent comp;

	private Rectangle finalSize, tmpSize;
	private Color finalColor;
	private Color originColor;

	private boolean finnished = false, infinite = false;

	private int duration, counter = 0;

	public UIAnimation(int duration, Rectangle finalSize, Color finalColor) {
		this.duration = duration;
		this.finalSize = finalSize;
		this.finalColor = finalColor;
	}

	public void init(UIComponent comp) {
		this.comp = comp;
		this.tmpSize = comp.getRect();
	}

	public void setInfinite() {
		infinite = true;
	}

	public void resetComp() {
		comp.setSize(comp.getOriginSize());
	}

	public void resetAnimation() {
		tmpSize = comp.getRect();
		counter = 0;
		finnished = false;
	}

	public boolean isFinnished() {
		return finnished;
	}

	public void setFinnished(boolean finnished) {
		this.finnished = finnished;
	}

	public Color getFinalColor() {
		return finalColor;
	}

	public void setX(int x) {
		comp.x = x;
		comp.calcRect();
	}

	public void setY(int y) {
		comp.y = y;
		comp.calcRect();
	}

	public void setWidth(int width) {
		comp.width = width;
		comp.calcRect();
	}

	public void setHeight(int height) {
		comp.height = height;
		comp.calcRect();
	}

	public int getCounter() {
		return counter;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setTmpSize(Rectangle rect) {
		this.tmpSize = rect;
	}

	public void setOriginColor(Color originColor) {
		this.originColor = originColor;
	}

	public Color getOriginColor() {
		return originColor;
	}

	public float getPerc() {
		// return current progress in percent
		return (float) (counter) / (float) duration * 100;
	}

	public void setPerc(float perc) {
		counter = (int) ((float) duration / 100 * perc);
		finnished = false;
	}

	public void tick() {
		// change size
		if (finalSize != null) {
			setX((int) (tmpSize.x + (finalSize.x - tmpSize.x) * (getPerc() / 100)));
			setY((int) (tmpSize.y + (finalSize.y - tmpSize.y) * (getPerc() / 100)));
			setWidth((int) (tmpSize.width + (finalSize.width - tmpSize.width) * (getPerc() / 100)));
			setHeight((int) (tmpSize.height + (finalSize.height - tmpSize.height) * (getPerc() / 100)));
		}

		// change color
		if (finalColor != null) {
			if (originColor == null) {
				originColor = comp.getAnimationColor();
			}

			comp.setAnimationColor(new Color(
					(int) (originColor.getRed() + (finalColor.getRed() - originColor.getRed()) * (getPerc() / 100)),
					(int) (originColor.getGreen()
							+ (finalColor.getGreen() - originColor.getGreen()) * (getPerc() / 100)),
					(int) (originColor.getBlue()
							+ (finalColor.getBlue() - originColor.getBlue()) * (getPerc() / 100))));
		}

		if (counter < duration) {
			counter++;
		} else {
			if (infinite) {
				counter = 0;
			} else {
				finnished = true;
			}
		}
	}

	public void render(Graphics g) {
		
	}
}
