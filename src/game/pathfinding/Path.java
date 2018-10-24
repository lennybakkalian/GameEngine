package game.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import game.ingame.world.Tile;
import game.ingame.world.World;
import game.ingame.world.WorldObject;
import game.main.Handler;
import game.pathfinding.Node.NodeType;

public class Path extends WorldObject {

	private World world;
	private int startX, startY, endX, endY;
	private ArrayList<Node> currentNodes;
	private ArrayList<Node> scannedNodes;
	private ArrayList<Node> allNodes;
	private Node start, end, current;
	private boolean foundPath = false;
	private ArrayList<Node> closed;
	private PriorityQueue<Node> open;

	@SuppressWarnings("unchecked")
	public Path(Handler handler, World world, int startX, int startY, int endX, int endY) {
		super(handler);
		this.world = world;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		setX(startX);
		setY(startY);
		this.currentNodes = new ArrayList<Node>();
		this.scannedNodes = new ArrayList<Node>();
		this.allNodes = new ArrayList<Node>();

		this.start = new Node(world.getTileAt(startX, startY), NodeType.START, null);
		this.end = new Node(world.getTileAt(endX, endY), NodeType.END, null);
		for (Tile t : world.getTiles()) {
			NodeType type = t.isFullySolid() ? NodeType.SOLID : NodeType.AIR;
			if (t == end.tile)
				type = NodeType.END;
			// check if start or end node
			if (t != start.tile)
				allNodes.add(new Node(t, type, null));

		}
		closed = new ArrayList<Node>();
		open = new PriorityQueue<Node>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Node n1 = (Node) o1;
				Node n2 = (Node) o2;
				return n1.getFinalCost() < n2.getFinalCost() ? -1 : n1.getFinalCost() > n2.getFinalCost() ? 1 : 0;
			}
		});
		open.add(start);
	}

	public void findPath(Graphics g) {
		for (Node c : closed)
			c.render(g, this);
		if (foundPath)
			return;
		current = open.poll();
		if (current == null)
			return;
		open.remove(current);
		closed.add(current);
		if (current.type == NodeType.END) {
			foundPath = true;
			System.out.println("found path");
			return;
		}

		processNeighbour(current, -1, 0, g);
		processNeighbour(current, 1, 0, g);
		processNeighbour(current, 0, -1, g);
		processNeighbour(current, 0, 1, g);

	}

	public void processNeighbour(Node current, int xOffset, int yOffset, Graphics g) {
		Node neighbour = getNeighbour(current, xOffset, yOffset);
		if (neighbour != null && checkNeighbour(neighbour)) {
			neighbour.render(g, this);
			if (!open.contains(neighbour) /* or neighbour shorter */) {
				neighbour.fCost = calcFCost(neighbour);
				neighbour.parent = current;
				if (!open.contains(neighbour))
					open.add(neighbour);
			}

		}
	}

	public int calcFCost(Node n) {
		// calc raw distance
		int k1 = Math.abs(n.x - end.x);
		int k2 = Math.abs(n.y - end.y);
		return (int) Math.sqrt((k1 * k1) + (k2 * k2));
	}

	public boolean checkNeighbour(Node n) {
		return n.type != NodeType.SOLID && !closed.contains(n);
	}

	public Node getNeighbour(Node n, int xOffset, int yOffset) {
		for (Node check : allNodes)
			if (check.x == n.x + xOffset && check.y == n.y + yOffset)
				return check;
		return null;
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		findPath(g);
		// render start
		g2d.setColor(Color.cyan);
		start.render(g2d, this);
		end.render(g2d, this);
		g2d.setColor(Color.blue);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(xRenderPos, yRenderPos, handler.getWorld().getCamera().calcXRenderPos(endX),
				handler.getWorld().getCamera().calcYRenderPos(endY));
		g2d.setStroke(new BasicStroke(1));
		// render tiles
		for (Tile t : world.getTiles()) {
			g.setColor(Color.BLACK);
			g.drawRect(t.xRenderPos, t.yRenderPos, t.getWidth(), t.getHeight());
		}
		Handler.debugInfoList.put(10, "path: start: " + startX + "x" + startY + "  end: " + endX + "x" + endY);

		super.render(g);
	}

}
