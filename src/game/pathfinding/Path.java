package game.pathfinding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.ingame.world.Tile;
import game.ingame.world.World;
import game.ingame.world.WorldObject;
import game.main.Handler;
import game.main.Utils;

public class Path extends WorldObject {

	private World world;
	private int startX, startY, endX, endY;
	private ArrayList<Node> currentNodes;
	private ArrayList<Node> scannedNodes;
	private ArrayList<Node> allNodes;
	private Tile start, end;
	
	
	

	public Path(Handler handler, World world, int startX, int startY, int endX, int endY) {
		super(handler);
		this.world = world;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		setX(startX);
		setY(startY);
		this.start = world.getTileAt(startX, startY);
		this.end = world.getTileAt(endX, endY);
		this.currentNodes = new ArrayList<Node> ();
		this.scannedNodes = new ArrayList<Node> ();
		this.allNodes = new ArrayList<Node> ();
		for(Tile t: world.getTiles())
			this.allNodes.add(new Node(t.getX(), t.getY(), null));
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// render start
		g2d.setColor(Color.cyan);
		
		Utils.fillRect(g2d, start.getRenderRect());
		Utils.fillRect(g2d, end.getRenderRect());
		
		
		g2d.setColor(Color.red);
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
