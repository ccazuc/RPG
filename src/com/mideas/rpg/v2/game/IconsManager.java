package com.mideas.rpg.v2.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mideas.rpg.v2.utils.Texture;

public class IconsManager {

	private static Map<String, Texture> sprites42 = new HashMap<String, Texture>();
	private static Map<String, Texture> sprites35 = new HashMap<String, Texture>();
	private static Map<String, Texture> sprites47 = new HashMap<String, Texture>();
	private static Map<String, Texture> sprites37 = new HashMap<String, Texture>();
	
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
		if(sprites42.containsKey(id)) {
			return sprites42.get(id);
		}
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
		if(sprites35.containsKey(id)) {
			return sprites35.get(id);
		}
		if(sprites35.containsKey("inv_misc_questionmark")) {
			return sprites35.get("inv_misc_questionmark");
		}
		return null;
	}
	
	public static Texture getSprite37(String id) {
		if(sprites37.containsKey(id)) {
			return sprites37.get(id);
		}
		loadTexture37(id);
		if(sprites37.containsKey(id)) {
			return sprites37.get(id);
		}
		if(sprites37.containsKey("inv_misc_questionmark")) {
			return sprites37.get("inv_misc_questionmark");
		}
		return null;
	}
	
	public static Texture getSprite47(String id) {
		if(sprites47.containsKey(id)) {
			return sprites47.get(id);
		}
		loadTexture47(id);
		if(sprites47.containsKey(id)) {
			return sprites47.get(id);
		}
		if(sprites47.containsKey("inv_misc_questionmark")) {
			return sprites47.get("inv_misc_questionmark");
		}
		loadTexture47("inv_misc_questionmark");
		if(sprites47.containsKey("inv_misc_questionmark")) {
			return sprites47.get("inv_misc_questionmark");
		}
		return null;
	}
	
	private static void loadTexture42(String id) {
		File file = new File("inventory_icons/"+id+".jpg");
		if(file.exists()) {
			try {
				sprites42.put(id, new Texture(file.getAbsolutePath()));
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadTexture35(String id) {
		File file = new File("bag_icons/"+id+".jpg");
		if(file.exists()) {
			try {
				sprites35.put(id, new Texture(file.getAbsolutePath()));
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadTexture37(String id) {
		File file = new File("Icons 37-35/"+id+".jpg");
		if(file.exists()) {
			try {
				sprites37.put(id, new Texture(file.getAbsolutePath()));
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadTexture47(String id) {
		File file = new File("new spellbar_icons/"+id+".jpg");
		if(file.exists()) {
			try {
				sprites47.put(id, new Texture(file.getAbsolutePath()));
			} 
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
