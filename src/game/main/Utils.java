package game.main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class Utils {
	public static BufferedImage loadImage(File f) throws IOException {
		return ImageIO.read(f);
	}

	public static void drawCenterString(Graphics g, String text, Rectangle rect) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(text, x, y);
	}

	public static void drawRightString(Graphics g, String text, int x, int y) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int xRender = x - metrics.stringWidth(text);
		g.drawString(text, xRender, y);
	}

	public static boolean isSameSize(Rectangle rect1, Rectangle rect2) {
		return (rect1.x == rect2.x && rect1.width == rect2.width && rect1.y == rect2.y && rect1.height == rect2.height);
	}

	public static void renderRect(Graphics g, Rectangle r) {
		g.setColor(Color.red);
		g.drawRect(r.x, r.y, r.width, r.height);
	}

	public static void fillRect(Graphics g, Rectangle r) {
		g.fillRect(r.x, r.y, r.width, r.height);
	}

}
