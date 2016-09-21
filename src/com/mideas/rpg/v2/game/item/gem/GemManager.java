package com.mideas.rpg.v2.game.item.gem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class GemManager {

	private static HashMap<Integer, Gem> gemList = new HashMap<Integer, Gem>();
	private static HashMap<Integer, Texture> gemSprites = new HashMap<Integer, Texture>();
	
	public static void loadGems() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, quality, color, sellprice, pa, intellect, stamina, defense, mp5, mana, critical, spell_critical, spell_damage, heal FROM item_gem");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String sprite_id = statement.getString();
			String name = statement.getString();
			int quality = statement.getInt();
			String tempColor = statement.getString();
			GemColor color = convColor(tempColor);
			int sellPrice = statement.getInt();
			int pa = statement.getInt();
			int intellect = statement.getInt();
			int stamina = statement.getInt();
			int defense = statement.getInt();
			int mp5 = statement.getInt();
			int mana = statement.getInt();
			int critical = statement.getInt();
			int spell_critical = statement.getInt();
			int spell_damage = statement.getInt();
			int heal = statement.getInt();
			gemList.put(id, new Gem(id, sprite_id, name, quality, color, pa, stamina, defense, mana, critical, sellPrice));
		}
	}
	
	public static Texture getGemSprite(int id) {
		return gemSprites.get(id);
	}
	
	public static void loadGemSprites() {
		gemSprites.put(50001, Sprites.crimson_spinel);
		gemSprites.put(50101, Sprites.pyrestone);
		gemSprites.put(50201, Sprites.empyrean_sapphire);
	}
	
	public static GemColor convColor(String color) {
		if(color.equals("RED")) {
			return GemColor.RED;
		}
		if(color.equals("YELLOW")) {
			return GemColor.YELLOW;
		}
		if(color.equals("GREEN")) {
			return GemColor.GREEN;
		}
		if(color.equals("BLUE")) {
			return GemColor.BLUE;
		}
		if(color.equals("PURPLE")) {
			return GemColor.PURPLE;
		}
		if(color.equals("ORANGE")) {
			return GemColor.ORANGE;
		}
		if(color.equals("NONE")) {
			return GemColor.NONE;
		}
		return null;
	}
	
	public static Gem getGem(int id) {
		if(gemList.containsKey(id)) {
			return gemList.get(id);
		}
		return null;
	}
	
	public static Gem getClone(int id) {
		Gem temp = getGem(id);
		if(temp != null) {
			return new Gem(temp);
		}
		return null;
	}
	
	public static boolean exists(int id) {
		return gemList.containsKey(id);
	}
}
