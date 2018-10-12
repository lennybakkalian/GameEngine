package game.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.main.Utils;

public class Resource {

	private String name;
	private File file;

	private BufferedImage image;

	public Resource(String name, File f) throws IOException {
		this.name = name;
		this.file = f;
		
		// check if image
		String fileName = f.getName();
		if(fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
			image = Utils.loadImage(f);
		}
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return file.getName();
	}

	public File getFile() {
		return file;
	}

	public boolean isImage() {
		return image != null;
	}

	public BufferedImage getImage() {
		if (isImage()) {
			return image;
		} else {
			System.out.println("[Resource.java] " + name + " isnt a image!");
		}
		return null;
	}

}
