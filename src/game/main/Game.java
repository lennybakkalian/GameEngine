package game.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import game.managers.KeyManager;
import game.managers.MouseManager;
import game.resources.ResourceStorage;
import game.resources.pack.DefaultResourcePack;
import game.states.LoadingState;
import game.states.State;
import game.ui.UIHandler;

public class Game implements Runnable {

	private String title;
	private int width, height;
	private boolean fullscreen, running, doRender = false;
	public boolean debug = false;
	private Thread gameThread;
	private int fps = 60;
	private State state;
	private Display display;
	private Handler handler;

	private BufferStrategy bs;
	private Graphics g;

	private ResourceStorage resources;

	public File RESOURCE_PATH;

	public MouseManager mouseManager;
	public KeyManager keyManager;

	public UIHandler currentUIHandler;

	public Game(String title, int width, int height, boolean fullscreen) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		this.handler = new Handler(this);
		this.mouseManager = new MouseManager(handler);
		this.keyManager = new KeyManager(handler);
	}

	public void init() throws IOException {
		display = new Display(title, width, height, fullscreen);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getFrame().addKeyListener(keyManager);

		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseWheelListener(mouseManager);
		display.getCanvas().addKeyListener(keyManager);
		display.getCanvas().addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				width = display.getCanvas().getWidth();
				height = display.getCanvas().getHeight();
				System.out.println("[Game] Changed resolution to " + width + "x" + height);
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}
		});
		display.getFrame().setAlwaysOnTop(true);
		resources = new ResourceStorage(new DefaultResourcePack(RESOURCE_PATH));

		state = new LoadingState(handler);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ResourceStorage getResources() {
		return resources;
	}

	public Display getDisplay() {
		return display;
	}

	public void tick() throws Exception {
		if (state != null) {
			state.tick();
		}
		mouseManager.tick();
		keyManager.tick();
	}

	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		// g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		g.clearRect(0, 0, width, height);

		try {
			if (getState() != null) {
				if (getState().uiHandler != currentUIHandler) {
					currentUIHandler = getState().uiHandler;
				}
				getState().render(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// end draw

		bs.show();
		g.dispose();
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public int getFps() {
		return fps;
	}

	public void run() {
		try {
			init();

			double timePerTick = 1000000000 / fps;
			double delta = 0;
			long now;
			long lastTime = System.nanoTime();
			long timer = 0;
			int ticks = 0;

			// start render thread
			new RenderThread().start();
			while (running) {

				now = System.nanoTime();
				delta += (now - lastTime) / timePerTick;
				timer += now - lastTime;
				lastTime = now;

				if (delta >= 1) {
					try {
						tick();
						doRender = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
					ticks++;
					delta--;
				}

				if (timer >= 1000000000) {
					fps = ticks;
					ticks = 0;
					timer = 0;
				}

				// TODO: check on game laggs & send report to server
				if (delta > 1000) {
					System.out.println("[WARNING] delta value in Game.java is too high! (delta:" + delta + ")");
					System.out.println("[WARNING] set delta value to 0");
					delta = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private class RenderThread extends Thread {
		public void run() {
			while (running) {
				synchronized (this) {
					try {
						if (doRender) {
							render();
							doRender = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public synchronized void start() {
		if (running) {
			return;
		} else {
			running = true;
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;

		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
