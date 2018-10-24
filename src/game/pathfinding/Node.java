package game.pathfinding;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.ingame.world.Tile;
import game.main.Utils;

public class Node {

	public static enum NodeType {
		START, END, SOLID, AIR
	};

	public int x, y;
	public Tile tile;
	public Node parent;
	public NodeType type;
	public double hCost = 0;
	public double gCost = 0;

	public Node(Tile t, NodeType type, Node parent) {
		this.x = t.getX() / t.getWidth();
		this.y = t.getY() / t.getHeight();
		this.tile = t;
		this.type = type;
		this.parent = parent;
	}
	
	public double getFinalCost() {
		return hCost + gCost;
	}

	public void render(Graphics g, Path p) {
		switch(type) {
		case AIR:
			g.setColor(Color.white);
			break;
		case SOLID:
			g.setColor(Color.red);
			break;
		case START:
			g.setColor(Color.green);
			break;
		case END:
			g.setColor(Color.orange);
			break;
		
		}
		Utils.fillRect(g, tile.getRenderRect());
		
		
		g.setFont(new Font("Arial", Font.BOLD,18));
		g.setColor(Color.black);
		g.drawString("" + (int) p.calcHCost(this), tile.xRenderPos + 1, tile.yRenderPos + 18);
		
	}
}
