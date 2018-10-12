package game.resources.pack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import game.resources.Resource;

public class DefaultResourcePack {

	private ArrayList<Resource> res = new ArrayList<Resource>();
	private File RESOURCE_PATH;

	public DefaultResourcePack(File RESOURCE_PATH) throws IOException {
		this.RESOURCE_PATH = RESOURCE_PATH;

		add("grass", "grass.png");
		add("rock", "rock.png");

	}

	public void add(String name, String file) throws IOException {
		res.add(new Resource(name, new File(RESOURCE_PATH.getAbsolutePath() + "/" + file)));
	}

	public File getRESOURCE_PATH() {
		return RESOURCE_PATH;
	}

	public ArrayList<Resource> getResources() {
		return res;
	}
}
