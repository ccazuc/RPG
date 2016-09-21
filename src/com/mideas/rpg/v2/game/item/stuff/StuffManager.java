package com.mideas.rpg.v2.game.item.stuff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.classes.Wear;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.hud.DragManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class StuffManager {

	private static HashMap<Integer, Stuff> stuffList = new HashMap<Integer, Stuff>();
	private static int numberStuffLoaded;
	
	public static void loadStuffs() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, type, name, class, wear, sprite_id, quality, gem_slot1, gem_slot2, gem_slot3, gem_bonus_type, gem_bonus_value, level, armor, stamina, mana, critical, strength, sellprice FROM item_stuff");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String tempType = statement.getString();
			StuffType type = getType(tempType);
			String name = statement.getString();
			short classeTemp = statement.getShort();
			ClassType[] classeType = getClasses(classeTemp);
			String tempWear = statement.getString();
			Wear wear = getWear(tempWear);
			String sprite_id = statement.getString();
			int quality = statement.getInt();
			String tempColor = statement.getString();
			GemColor color1 = GemManager.convColor(tempColor);
			tempColor = statement.getString();
			GemColor color2 = GemManager.convColor(tempColor);
			tempColor = statement.getString();
			GemColor color3 = GemManager.convColor(tempColor);
			String tempBonusType = statement.getString();
			GemBonusType bonusType = convBonusType(tempBonusType);
			int bonusValue = statement.getInt();
			int level = statement.getInt();
			int armor = statement.getInt();
			int stamina = statement.getInt();
			int mana = statement.getInt();
			int critical = statement.getInt();
			int strength = statement.getInt();
			int sellPrice = statement.getInt();
			Stuff newPiece = new Stuff(type, classeType, sprite_id, id, name, quality, color1, color2, color3, bonusType, bonusValue, level, wear, critical, strength, stamina, armor, mana, sellPrice);
			stuffList.put(id, newPiece);
			numberStuffLoaded++;
		}
	}
	
	public static boolean canEquipStuff(Stuff stuff) {
		if(Mideas.getLevel() >= stuff.getLevel() && DragManager.canWear(stuff) && stuff.canEquipTo(DragManager.convClassType())) {
			return true;
		}
		return false;
	}
	
	public static Stuff getStuff(int id) {
		if(stuffList.containsKey(id)) {
			return stuffList.get(id);
		}
		return null;
	}
	
	public static void storeNewPiece(Stuff stuff) {
		stuffList.put(stuff.getId(), stuff);
	}
	
	public static boolean exists(int id) {
		return getStuff(id) != null;
	}
	
	public static Stuff getClone(int id) {
		Stuff temp = getStuff(id);
		if(temp != null) {
			return new Stuff(temp);
		}
		return null;
	}
	
	public static ClassType[] getClasses(short type) {
		int i = 0;
		int count = 0;
		while(i < 10) {
			if((type & (1 << i)) != 0) {
				count++;
			}
			i++;
		}
		ClassType[] tempClasses = new ClassType[count];
		count = 0;
		if((type & (1 << 0)) != 0) {
			tempClasses[count] = ClassType.DEATHKNIGHT;
			count++;
		}
		if((type & (1 << 1)) != 0) {
			tempClasses[count] = ClassType.GUERRIER;
			count++;
		}
		if((type & (1 << 2)) != 0) {
			tempClasses[count] = ClassType.HUNTER;
			count++;
		}
		if((type & (1 << 3)) != 0) {
			tempClasses[count] = ClassType.MAGE;
			count++;
		}
		if((type & (1 << 4)) != 0) {
			tempClasses[count] = ClassType.MONK;
			count++;
		}
		if((type & (1 << 5)) != 0) {
			tempClasses[count] = ClassType.PALADIN;
			count++;
		}
		if((type & (1 << 6)) != 0) {
			tempClasses[count] = ClassType.PRIEST;
			count++;
		}
		if((type & (1 << 7)) != 0) {
			tempClasses[count] = ClassType.ROGUE;
			count++;
		}
		if((type & (1 << 8)) != 0) {
			tempClasses[count] = ClassType.SHAMAN;
			count++;
		}
		if((type & (1 << 9)) != 0) {
			tempClasses[count] = ClassType.WARLOCK;
			count++;
		}
		return tempClasses;
	}
	
	private static StuffType getType(String type) {
		if(type.equals("HEAD")) {
			return StuffType.HEAD;
		}
		else if(type.equals("NECKLACE")) {
			return StuffType.NECKLACE;
		}
		else if(type.equals("SHOULDERS")) {
			return StuffType.SHOULDERS;
		}
		else if(type.equals("CHEST")) {
			return StuffType.CHEST;
		}
		else if(type.equals("BACK")) {
			return StuffType.BACK;
		}
		else if(type.equals("WRISTS")) {
			return StuffType.WRISTS;
		}
		else if(type.equals("GLOVES")) {
			return StuffType.GLOVES;
		}
		else if(type.equals("BELT")) {
			return StuffType.BELT;
		}
		else if(type.equals("LEGGINGS")) {
			return StuffType.LEGGINGS;
		}
		else if(type.equals("BOOTS")) {
			return StuffType.BOOTS;
		}
		else if(type.equals("RING")) {
			return StuffType.RING;
		}
		else if(type.equals("TRINKET")) {
			return StuffType.TRINKET;
		}
		else if(type.equals("MAINHAND")) {
			return StuffType.MAINHAND;
		}
		else if(type.equals("OFFHAND")) {
			return StuffType.OFFHAND;
		}
		else if(type.equals("RANGED")) {
			return StuffType.RANGED;
		}
		return null;
	}
	
	private static Wear getWear(String wear) {
		if(wear.equals("CLOTH")) {
			return Wear.CLOTH;
		}
		if(wear.equals("LEATHER")) {
			return Wear.LEATHER;
		}
		if(wear.equals("MAIL")) {
			return Wear.MAIL;
		}
		if(wear.equals("PLATE")) {
			return Wear.PLATE;
		}
		return Wear.NONE;
	}
	
	public static int getNumberStuffLoaded() {
		return numberStuffLoaded;
	}
	
	public static HashMap<Integer, Stuff> getStuffList() {
		return stuffList;
	}
	
	public static GemBonusType convBonusType(String bonus) {
		if(bonus.equals("STAMINA")) {
			return GemBonusType.STAMINA;
		}
		if(bonus.equals("STRENGTH")) {
			return GemBonusType.STRENGTH;
		}
		if(bonus.equals("ARMOR")) {
			return GemBonusType.ARMOR;
		}
		if(bonus.equals("CRITICAL")) {
			return GemBonusType.CRITICAL;
		}
		if(bonus.equals("MANA")) {
			return GemBonusType.MANA;
		}
		if(bonus.equals("NONE")) {
			return GemBonusType.NONE;
		}
		return null;
	}
}
