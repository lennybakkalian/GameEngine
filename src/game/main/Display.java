package game.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.ScrollPaneAdjustable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.peer.FramePeer;
import java.awt.event.ActionEvent;

public class Display extends JFrame {

	private Canvas canvas;
	private int width, height;
	private String title;
	private JFrame frame;
	private boolean fullscreen;

	public Display(String title, int width, int height, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;
		createDisplay();
	}

	public void createDisplay() {
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setTitle(title);
		frame.setUndecorated(fullscreen);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setFocusTraversalKeysEnabled(false);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
	}

	public void paint(Graphics g) {
		// g.setColor(Color.RED);
		// g.drawLine(0, 480, 960, 480);
		// g.setColor(Color.BLUE);
		// g.drawLine(0, 0, 960, 560);
		// g.drawLine(300, 300, 500, 100);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
}
