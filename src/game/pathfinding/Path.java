package game.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import game.ingame.world.Tile;
import game.ingame.world.World;
import game.ingame.world.WorldObject;
import game.main.Handler;
import game.main.Utils;
import game.pathfinding.Node.NodeType;

public class Path extends WorldObject {

	private World world;
	private int startX, startY, endX, endY;
	private ArrayList<Node> allNodes;
	private Node start, end, current;
	private boolean done = false, diagonalPathfinding = false, invalidPath = false;
	private ArrayList<Node> closed;
	private PriorityQueue<Node> open;
	private ArrayList<Node> foundedPath;
	private ArrayList<Node> shortestPath;
	private Long started, time = 0L;

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
		this.allNodes = new ArrayList<Node>();
		if (world.getTileAt(startX, startY) == null || world.getTileAt(endX, endY) == null) {
			invalidPath = true;
			System.out.println("invalid start/end node");
			return;
		}
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
		foundedPath = new ArrayList<Node>();
		shortestPath = new ArrayList<Node>();
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
		started = System.currentTimeMillis();
	}
	
	public World getWorld() {
		return world;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public ArrayList<Node> getShortestPath() {
		return shortestPath;
	}

	public void findPath() {
		if (done)
			return;
		current = open.poll();
		if (current == null) {
			done = true;
			System.out.println("could not find a path");
			return;
		}
		open.remove(current);
		closed.add(current);
		if (current.type == NodeType.END) {
			System.out.println("\n\n\n");
			System.out.println((System.currentTimeMillis() - started) + "ms astar finnished");
			done = true;
			// reconstuct path
			current.isPath = true;
			foundedPath.add(current);
			Node reconstruct = current.parent;
			while (reconstruct != null) {
				foundedPath.add(reconstruct);
				reconstruct.isPath = true;
				reconstruct = reconstruct.parent;
			}
			System.out.println((System.currentTimeMillis() - started) + "ms reconstruct finnished");
			// try to search shortest path with lineOfSight
			shortestPath.clear();
			shortestPath.add(start);
			Node currentNode = null;
			// for loop from start to end
			for (int i = foundedPath.size() - 1; i >= 0; i--) {
				currentNode = foundedPath.get(i);
				// for loop from end to currentNode
				for (int i2 = 0; i2 < foundedPath.size(); i2++) {
					Node checkNode = foundedPath.get(i2);
					if (world.canSeeOtherNode(currentNode, checkNode)) {
						// add last tile to shortestpath
						shortestPath.add(checkNode);
						i = i2;
						break;
					}
				}
			}
			System.out.println((System.currentTimeMillis() - started) + "ms lineofsight finnished");
			return;
		}

		processNeighbour(current, -1, 0, 1);
		processNeighbour(current, 1, 0, 1);
		processNeighbour(current, 0, -1, 1);
		processNeighbour(current, 0, 1, 1);

		// diagonal nodes / not always the shortest path
		// TODO: test 1.4
		if (diagonalPathfinding) {
			double diagonalGCost = 1.9;
			Node rn = getNeighbour(current, 1, 0);
			Node ln = getNeighbour(current, -1, 0);
			Node tn = getNeighbour(current, 0, -1);
			Node bn = getNeighbour(current, 0, 1);
			if (canMoveOn(ln) || canMoveOn(tn))
				processNeighbour(current, -1, -1, diagonalGCost);
			if (canMoveOn(tn) || canMoveOn(rn))
				processNeighbour(current, 1, -1, diagonalGCost);
			if (canMoveOn(ln) || canMoveOn(bn))
				processNeighbour(current, -1, 1, diagonalGCost);
			if (canMoveOn(rn) || canMoveOn(bn))
				processNeighbour(current, 1, 1, diagonalGCost);
		}
		time = System.currentTimeMillis() - started;
	}

	public void processNeighbour(Node current, int xOffset, int yOffset, double addGCost) {
		Node neighbour = getNeighbour(current, xOffset, yOffset);
		if (neighbour != null && checkNeighbour(neighbour)) {
			if (!open.contains(neighbour) /* or neighbour shorter */) {
				neighbour.hCost = calcHCost(neighbour);
				neighbour.gCost = current.gCost + addGCost;
				neighbour.parent = current;
				if (!open.contains(neighbour))
					open.add(neighbour);
			}

		}
	}

	public double calcHCost(Node n) {
		// calc raw distance
		int k1 = Math.abs(n.x - end.x);
		int k2 = Math.abs(n.y - end.y);
		return Math.sqrt((k1 * k1) + (k2 * k2));
	}

	public boolean checkNeighbour(Node n) {
		return canMoveOn(n) && !closed.contains(n);
	}

	public boolean canMoveOn(Node n) {
		if (n == null)
			return false;
		return n.type != NodeType.SOLID;
	}

	public Node getNeighbour(Node n, int xOffset, int yOffset) {
		for (Node check : allNodes)
			if (check.x == n.x + xOffset && check.y == n.y + yOffset)
				return check;
		return null;
	}

	@Override
	public void tick() {
		while (!done && !invalidPath)
			findPath();
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (invalidPath)
			return;
		// for (int i = 0; i < closed.size(); i++)
		// closed.get(i).render(g, this);
		for (int i = 0; i < foundedPath.size(); i++) {
			// Utils.fillRect(g, c.tile.getRenderRect());
			foundedPath.get(i).render(g, this);
		}

		// render start
		g2d.setColor(Color.cyan);
		start.render(g2d, this);
		end.render(g2d, this);
		// render tiles
		for (Tile t : world.getTiles()) {
			g2d.setStroke(new BasicStroke(1F));
			g.setColor(Color.BLACK);
			// render tile borders
			//Utils.renderRect(g, t.getRenderRect());
		}
		for (int i = 0; i < shortestPath.size(); i++) {
			g2d.setStroke(new BasicStroke(2F));
			g2d.setColor(Color.white);
			if (i > 0) {
				Node lastNode = shortestPath.get(i - 1);
				Node thisNode = shortestPath.get(i);
				g.drawLine(lastNode.tile.xRenderPos + lastNode.tile.getWidth() / 2,
						lastNode.tile.yRenderPos + lastNode.tile.getHeight() / 2,
						thisNode.tile.xRenderPos + thisNode.tile.getWidth() / 2,
						thisNode.tile.yRenderPos + thisNode.tile.getHeight() / 2);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString(i + "", thisNode.tile.xRenderPos, thisNode.tile.yRenderPos);
			}
		}

		Handler.debugInfoList.put(10, "path: start: " + startX + "x" + startY + "  end: " + endX + "x" + endY);
		Handler.debugInfoList.put(11, "last path searching for " + time + "ms");

		super.render(g);
	}

}
