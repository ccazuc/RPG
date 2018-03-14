package com.mideas.rpg.v2.game.race;

import com.mideas.rpg.v2.render.Sprites;
import com.mideas.rpg.v2.render.Texture;

public enum Classe {

	ROGUE(Sprites.select_screen_rogue, "Rogue"),
	PALADIN(Sprites.select_screen_paladin, "Paladin"),
	SHAMAN(Sprites.select_screen_shaman, "Shaman"),
	DRUID(Sprites.select_screen_druid, "Druid"),
	MAGE(Sprites.select_screen_mage, "Mage"),
	WARRIOR(Sprites.select_screen_warrior, "Warrior"),
	WARLOCK(Sprites.select_screen_warlock, "Warlock"),
	PRIEST(Sprites.select_screen_priest, "Priest"),
	HUNTER(Sprites.select_screen_hunter, "Hunter");
	
	private final Texture texture;
	private final String name;
	
	private Classe(Texture texture, String name) {
		this.texture = texture;
		this.name = name;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public String getName() {
		return this.name;
	}
}
