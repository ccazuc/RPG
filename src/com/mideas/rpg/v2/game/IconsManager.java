package com.mideas.rpg.v2.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class IconsManager {

	private static HashMap<String, Texture> sprites = new HashMap<String, Texture>();
	
	public static void loadSprites() throws IOException {
		File folder = new File("icons");
		if(folder.exists()) {
			File[] files = folder.listFiles();
			if(files != null) {
				for(File file : files) {
					if(file.getName().endsWith(".jpg")) {
						String id = file.getName().substring(0, file.getName().length()-4);
						if(!sprites.containsKey(id)) {
							sprites.put(id, TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(file.getAbsolutePath())));
						}
					}
				}
			}
		}
	}
	
	public static Texture getSprite(String id) {
		if(sprites.containsKey(id)) {
			return sprites.get(id);
		}
		if(sprites.containsKey("inv_misc_questionmark")) {
			return sprites.get("inv_misc_questionmark");
		}
		return null;
	}
}
