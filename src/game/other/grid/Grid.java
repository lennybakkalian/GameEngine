package game.other.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import game.ingame.GameObject;

public class Grid extends GameObject {

	private int xPos, yPos, cellWidth, cellHeight, cellCount;
	private ArrayList<Cell> cells;

	public Grid(int xPos, int yPos, int cellWidth, int cellHeight, int cellCount, Color cellBackground) {
		super(null);
		this.xPos = xPos;
		this.yPos = yPos;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.cellCount = cellCount;
		this.cells = new ArrayList<Cell>();

		for (int widthI = 0; widthI < cellCount; widthI++) {
			for (int heightI = 0; heightI < cellCount; heightI++) {
				Cell c = new Cell(xPos + (widthI * cellWidth), yPos + (heightI * cellHeight), cellWidth, cellHeight);
				if (cellBackground != null)
					c.setFillColor(cellBackground);
				cells.add(c);
			}
		}
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	@Override
	public void render(Graphics g) {
		for (Cell c : cells) {
			c.render(g);
		}
	}
}
