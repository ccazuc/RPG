package com.mideas.rpg.v2.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mideas.rpg.v2.utils.Texture;

public class IconsManager {

	private static Map<String, Texture> sprites37 = new HashMap<String, Texture>();
	
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
	
	private static void loadTexture37(String id) {
		File file = new File("Icons 37-35/"+id+".jpg");
		if(file.exists()) {
			sprites37.put(id, new Texture(file.getAbsolutePath(), true));
		}
	}
}
