package game.resources;

import java.io.File;
import java.util.ArrayList;

import game.resources.pack.DefaultResourcePack;

public class ResourceStorage {

	private ArrayList<Resource> resources;

	public ResourceStorage(DefaultResourcePack... defaultResourcePacks) {
		resources = new ArrayList<Resource>();

		if (defaultResourcePacks != null) {
			for (DefaultResourcePack drp : defaultResourcePacks) {
				resources.addAll(drp.getResources());
			}
		}
	}

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public Resource getByName(String name) {
		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i).getName() == null) {
				if (resources.get(i).getFile() != null
						&& resources.get(i).getFileName().toLowerCase().equals(name.toLowerCase())) {
					return resources.get(i);
				}
			} else {
				if (resources.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
					return resources.get(i);
				}
			}
		}
		System.out.println("[ResourceStorage] Resource " + name + " not found");
		return null;
	}

	public Resource getByFileName(String fileName) {
		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i).getFile() != null
					&& resources.get(i).getFileName().toLowerCase().equals(fileName.toLowerCase())) {
				return resources.get(i);
			}
		}
		return null;
	}

	public void addResource(Resource resource) {
		resources.add(resource);
	}

}
