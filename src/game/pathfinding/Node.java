package game.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
	public boolean isPath = false;

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
		switch (type) {
		case AIR:
			g.setColor(new Color(0,0,0,255));
			break;
		case SOLID:
			g.setColor(Color.black);
			break;
		case START:
			g.setColor(Color.green);
			break;
		case END:
			g.setColor(Color.orange);
			break;

		}
		Utils.fillRect(g, tile.getRenderRect());

		// draw arrow from parent
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setStroke(new BasicStroke(5F));
//		g2d.setColor(isPath ? Color.RED : Color.cyan);
//		if (parent != null)
//			Utils.drawArrow(g, parent.tile.xRenderPos + (parent.tile.getWidth() / 2),
//					parent.tile.yRenderPos + (parent.tile.getHeight() / 2), tile.xRenderPos + (tile.getWidth() / 2),
//					tile.yRenderPos + (tile.getHeight() / 2), 10);
//		
//		g.setFont(new Font("Arial", Font.PLAIN, 16));
		//g.setColor(Color.black);
		//g.drawString("" + (int) p.calcHCost(this), tile.xRenderPos + 1, tile.yRenderPos + 18);
		//g.setColor(Color.white);
		//g.drawString("" + (int) getFinalCost(), tile.xRenderPos + 1, tile.yRenderPos + 18);
		
		// g2d.drawLine(parent.tile.xRenderPos + (parent.tile.getWidth() / 2),
		// parent.tile.yRenderPos + (parent.tile.getHeight() / 2), tile.xRenderPos +
		// (tile.getWidth() / 2),
		// tile.yRenderPos + (tile.getHeight() / 2));
	}
}
