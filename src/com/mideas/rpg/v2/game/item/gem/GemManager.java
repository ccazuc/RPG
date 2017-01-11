package com.mideas.rpg.v2.game.item.gem;

import java.sql.SQLException;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class GemManager {

	private static HashMap<Integer, Gem> gemList = new HashMap<Integer, Gem>();
	//private static HashMap<Integer, Texture> gemSprites = new HashMap<Integer, Texture>();
	
	public static void loadGems() {
		try {
			JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, quality, color, sellprice, stat1Type, stat1Value, stat2Type, stat2Value, stat3Type, stat3Value FROM item_gem");
			statement.execute();
			while(statement.fetch()) {
				int id = statement.getInt();
				String sprite_id = statement.getString();
				String name = statement.getString();
				byte quality = statement.getByte();
				String tempColor = statement.getString();
				GemColor color = convColor(tempColor);
				int sellPrice = statement.getInt();
				GemBonusType stat1Type = convGemBonusType(statement.getString());
				int stat1Value = statement.getInt();
				GemBonusType stat2Type = convGemBonusType(statement.getString());
				int stat2Value = statement.getInt();
				GemBonusType stat3Type = convGemBonusType(statement.getString());
				int stat3Value = statement.getInt();
				gemList.put(id, new Gem(id, sprite_id, name, quality, color, sellPrice, stat1Type, stat1Value, stat2Type, stat2Value, stat3Type, stat3Value));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*public static Texture getGemSprite(int id) {
		return gemSprites.get(id);
	}
	
	public static void loadGemSprites() {
		gemSprites.put(50001, Sprites.crimson_spinel);
		gemSprites.put(50101, Sprites.pyrestone);
		gemSprites.put(50201, Sprites.empyrean_sapphire);
	}*/
	
	public static void storeNewPiece(Gem gem) {
		if(gem != null) {
			gemList.put(gem.getId(), gem);
		}
	}
	
	public static GemBonusType convGemBonusType(String bonus) {
		if(bonus.equals("STRENGTH")) {
			return GemBonusType.STRENGTH;
		}
		if(bonus.equals("STAMINA")) {
			return GemBonusType.STAMINA;
		}
		if(bonus.equals("INTELLIGENCE")) {
			return GemBonusType.INTELLIGENCE;
		}
		if(bonus.equals("CRITICAL")) {
			return GemBonusType.CRITICAL;
		}
		if(bonus.equals("SPELL_CRITICAL")) {
			return GemBonusType.SPELL_CRITICAL;
		}
		if(bonus.equals("HASTE")) {
			return GemBonusType.HASTE;
		}
		if(bonus.equals("SPELL_HASTE")) {
			return GemBonusType.SPELL_HASTE;
		}
		if(bonus.equals("MP5")) {
			return GemBonusType.MP5;
		}
		if(bonus.equals("HEALING_POWER")) {
			return GemBonusType.HEALING_POWER;
		}
		if(bonus.equals("NONE")) {
			return GemBonusType.NONE;
		}
		System.out.println("Error GemManager:convGemBonusType value : "+bonus);
		return null;
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
	
	public static HashMap<Integer, Gem> getGemMap() {
		return gemList;
	}
}
