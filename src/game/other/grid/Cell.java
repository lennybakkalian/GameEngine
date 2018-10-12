package game.other.grid;

import java.awt.Color;
import java.awt.Graphics;

import game.ingame.GameObject;

public class Cell extends GameObject {

	private int xPos, yPos, width, height;

	private Color fillColor = Color.WHITE, borderColor = Color.BLACK;

	public Cell(int xPos, int yPos, int width, int height) {
		super(null);
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(xPos, yPos, width, height);
		g.setColor(borderColor);
		g.drawRect(xPos, yPos, width, height);
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

}
