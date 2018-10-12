package game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import game.ingame.GameObject;
import game.main.Handler;
import javafx.animation.Animation;

public class UIComponent extends GameObject {

	public int x, y, width, height;
	private Rectangle rect, originSize;
	public boolean hoover = false, ignoreLayers = false, focused = false;

	// animation vars
	private HashMap<UIAnimation, AnimationType> animations;
	private Color animationColor = Color.BLACK;

	public static enum AnimationType {
		ON_HOVER, ON_HOVER_LEAVE, INPUT_ACTIVE
	}

	public UIComponent(int x, int y, int width, int height) {
		super(null);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect = new Rectangle(x, y, width, height);
		this.originSize = new Rectangle(rect);
		this.animations = new HashMap<UIAnimation, AnimationType>();
	}

	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	public boolean isFocused() {
		return focused;
	}

	public Rectangle getRect() {
		calcRect();
		return rect;
	}

	public void setAnimationColor(Color animationColor) {
		this.animationColor = animationColor;
	}

	public Color getAnimationColor() {
		return animationColor;
	}

	public void calcRect() {
		this.rect = new Rectangle(x, y, width, height);
	}

	public Rectangle getOriginSize() {
		return originSize;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void addAnimation(AnimationType animationType, UIAnimation animation) {
		animation.init(this);
		if (animationType == AnimationType.ON_HOVER_LEAVE) {
			animation.setFinnished(true);
			animationColor = animation.getFinalColor();
		}
		animations.put(animation, animationType);
	}

	@Override
	public void tick() throws Exception {
		for (UIAnimation animation : animations.keySet())
			if (!animation.isFinnished()) {
				switch (animations.get(animation)) {
				case ON_HOVER:
					if (hoover) {
						animation.tick();
						setPercToAnimationType(AnimationType.ON_HOVER_LEAVE, 100F - animation.getPerc());
						setTmpSizeByType(AnimationType.ON_HOVER_LEAVE, getRect());
						setFinnishedByType(AnimationType.ON_HOVER_LEAVE, false);
						setColorByType(AnimationType.ON_HOVER_LEAVE, getAnimationColor());
					}
					continue;
				case ON_HOVER_LEAVE:
					if (!hoover) {
						animation.tick();
						setPercToAnimationType(AnimationType.ON_HOVER, 100F - animation.getPerc());
						setTmpSizeByType(AnimationType.ON_HOVER, getRect());
						setFinnishedByType(AnimationType.ON_HOVER, false);
						setColorByType(AnimationType.ON_HOVER, getAnimationColor());
					}
					continue;
				default:
					animation.tick();
				}
			}
		super.tick();
	}

	@Override
	public void render(Graphics g) {

		for (UIAnimation animation : animations.keySet())
			if (!animation.isFinnished()) {
				switch (animations.get(animation)) {
				// add global anims here, - on hoover?
				}
			}

		super.render(g);
	}

	public ArrayList<UIAnimation> getAnimationsByType(AnimationType type) {
		ArrayList<UIAnimation> anims = new ArrayList<UIAnimation>();
		for (UIAnimation animation : animations.keySet())
			if (animations.get(animation).equals(type))
				anims.add(animation);
		return anims;
	}

	public void callbackByAnimType(AnimationType type, AnimTask task) {
		ArrayList<UIAnimation> anims = getAnimationsByType(type);
		for (UIAnimation a : anims)
			task.doTask(a);
	}

	public interface AnimTask {
		public void doTask(UIAnimation anim);
	}

	public void setPercToAnimationType(AnimationType type, float perc) {
		ArrayList<UIAnimation> anims = getAnimationsByType(type);
		for (UIAnimation a : anims)
			a.setPerc(perc);
	}

	public void setFinnishedByType(AnimationType type, boolean finnished) {
		ArrayList<UIAnimation> anims = getAnimationsByType(type);
		for (UIAnimation a : anims)
			a.setFinnished(finnished);
	}

	public void setTmpSizeByType(AnimationType type, Rectangle tmpSize) {
		ArrayList<UIAnimation> anims = getAnimationsByType(type);
		for (UIAnimation a : anims)
			a.setTmpSize(tmpSize);
	}

	public void setColorByType(AnimationType type, Color color) {
		ArrayList<UIAnimation> anims = getAnimationsByType(type);
		for (UIAnimation a : anims)
			a.setOriginColor(color);
	}

	public void renderAnimByType(AnimationType type, Graphics g) {
		ArrayList<UIAnimation> anims = getAnimationsByType(type);
		for (UIAnimation a : anims)
			a.render(g);
	}

	public boolean isIgnoreLayers() {
		return ignoreLayers;
	}

	public void setIgnoreLayers(boolean ignoreLayers) {
		this.ignoreLayers = ignoreLayers;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setSize(Rectangle rect) {
		x = rect.x;
		y = rect.y;
		width = rect.width;
		height = rect.height;
		this.rect = rect;
	}

	public void setHoover(boolean hoover) {
		this.hoover = hoover;
	}

	public void onMouseMove(MouseEvent e) {

	}

	public void onMouseDrag(MouseEvent e) {

	}

	public void onMouseClick(MouseEvent e) {

	}

	public void onMouseRelease(MouseEvent e) {

	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

}
