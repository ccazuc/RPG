package com.mideas.rpg.v2.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class IconsManager {

	private static Map<String, Texture> sprites42 = new HashMap<String, Texture>();
	private static Map<String, Texture> sprites35 = new HashMap<String, Texture>();
	private static Map<String, Texture> sprites54 = new HashMap<String, Texture>();
	
	/*public static void loadSprites() throws IOException {
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
	}*/
	
	public static Texture getSprite42(String id) {
		if(sprites42.containsKey(id)) {
			return sprites42.get(id);
		}
		loadTexture42(id);
		if(sprites42.containsKey("inv_misc_questionmark")) {
			return sprites42.get("inv_misc_questionmark");
		}
		return null;
	}
	
	public static Texture getSprite35(String id) {
		if(sprites35.containsKey(id)) {
			return sprites35.get(id);
		}
		loadTexture35(id);
		if(sprites35.containsKey("inv_misc_questionmark")) {
			return sprites35.get("inv_misc_questionmark");
		}
		return null;
	}
	
	public static Texture getSprite54(String id) {
		if(sprites54.containsKey(id)) {
			return sprites54.get(id);
		}
		loadTexture54(id);
		if(sprites54.containsKey("inv_misc_questionmark")) {
			return sprites54.get("inv_misc_questionmark");
		}
		return null;
	}
	
	private static void loadTexture42(String id) {
		File file = new File("inventory_icons/"+id+".jpg");
		if(file.exists()) {
			Texture newTexture;
			try {
				newTexture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(file.getAbsolutePath()));
				sprites42.put(id, newTexture);
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadTexture35(String id) {
		File file = new File("bag_icons/"+id+".jpg");
		if(file.exists()) {
			Texture newTexture;
			try {
				newTexture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(file.getAbsolutePath()));
				sprites35.put(id, newTexture);
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadTexture54(String id) {
		File file = new File("spellbar_icons/"+id+".jpg");
		if(file.exists()) {
			Texture newTexture;
			try {
				newTexture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(file.getAbsolutePath()));
				sprites54.put(id, newTexture);
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
