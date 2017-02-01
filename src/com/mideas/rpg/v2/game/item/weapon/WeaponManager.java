package com.mideas.rpg.v2.game.item.weapon;

import java.sql.SQLException;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.gem.GemBonusType;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.game.unit.Joueur;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class WeaponManager {
	
	private static HashMap<Integer, Stuff> weaponList = new HashMap<Integer, Stuff>();
	
	public static void loadWeapons() {
		try {
			JDOStatement statement = Mideas.getJDO().prepare("SELECT id, name, sprite_id, class, type, slot, quality, color1, color2, color3, gem_bonus_type, gem_bonus_value, level, armor, stamina, mana, critical, strength, sellprice FROM item_weapon");
			statement.execute();
			while(statement.fetch()) {
				int id = statement.getInt();
				String name = statement.getString();
				String sprite_id = statement.getString();
				short classeTemp = statement.getShort();
				ClassType[] classeType = StuffManager.getClasses(classeTemp);
				String tempType = statement.getString();
				WeaponType type = getType(tempType);
				String tempSlot = statement.getString();
				WeaponSlot slot = getSlot(tempSlot);
				byte quality = statement.getByte();
				String tempColor = statement.getString();
				GemColor color1 = GemManager.convColor(tempColor);
				tempColor = statement.getString();
				GemColor color2 = GemManager.convColor(tempColor);
				tempColor = statement.getString();
				GemColor color3 = GemManager.convColor(tempColor);
				String tempBonusType = statement.getString();
				GemBonusType bonusType = StuffManager.convBonusType(tempBonusType);
				int bonusValue = statement.getInt();
				byte level = statement.getByte();
				int armor = statement.getInt();
				int stamina = statement.getInt();
				int mana = statement.getInt();
				int critical = statement.getInt();
				int strength = statement.getInt();
				int sellPrice = statement.getInt();
				Stuff newPiece = new Stuff(id, name, sprite_id, classeType, type, slot, quality, color1, color2, color3, bonusType, bonusValue, level, armor, stamina, mana, critical, strength, sellPrice);
				weaponList.put(id, newPiece);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static Stuff getWeapon(int id) {
		if(weaponList.containsKey(id)) {
			return weaponList.get(id);
		}
		return null;
	}
	
	public static boolean exists(int id) {
		return weaponList.containsKey(id);
	}
	
	public static Stuff getClone(int id) {
		Stuff temp = getWeapon(id);
		if(temp != null) {
			return new Stuff(temp, 0);
		}
		return null;
	}
	
	public static boolean canEquipWeapon(Stuff weapon) {
		if(Mideas.joueur1().getLevel() >= weapon.getLevel() && weapon.canWearWeapon() && weapon.canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString()))) {
			return true;
		}
		return false;
	}
	
	public static void storeNewPiece(Stuff weapon) {
		if(weapon != null) {
			weaponList.put(weapon.getId(), weapon);
		}
	}
	
	private static WeaponType getType(String type) {
		if(type.equals("DAGGER")) {
			return WeaponType.DAGGER;
		}
		if(type.equals("FISTWEAPON")) {
			return WeaponType.FISTWEAPON;
		}
		if(type.equals("ONEHANDEDAXE")) {
			return WeaponType.ONEHANDEDAXE;
		}
		if(type.equals("TWOHANDEDAXE")) {
			return WeaponType.TWOHANDEDAXE;
		}
		if(type.equals("ONEHANDEDSWORD")) {
			return WeaponType.ONEHANDEDSWORD;
		}
		if(type.equals("TWOHANDEDSWORD")) {
			return WeaponType.TWOHANDEDSWORD;
		}
		if(type.equals("ONEHANDEDMACE")) {
			return WeaponType.ONEHANDEDMACE;
		}
		if(type.equals("TWOHANDEDMACE")) {
			return WeaponType.TWOHANDEDMACE;
		}
		if(type.equals("POLEARM")) {
			return WeaponType.POLEARM;
		}
		if(type.equals("STAFF")) {
			return WeaponType.STAFF;
		}
		if(type.equals("BOW")) {
			return WeaponType.BOW;
		}
		if(type.equals("CROSSBOW")) {
			return WeaponType.CROSSBOW;
		}
		if(type.equals("GUN")) {
			return WeaponType.GUN;
		}
		if(type.equals("THROWN")) {
			return WeaponType.THROWN;
		}
		if(type.equals("WAND")) {
			return WeaponType.WAND;
		}
		return null;
	}
	
	private static WeaponSlot getSlot(String slot) {
		if(slot.equals("OFFHAND")) {
			return WeaponSlot.OFFHAND;
		}
		if(slot.equals("MAINHAND")) {
			return WeaponSlot.MAINHAND;
		}
		if(slot.equals("RANGED")) {
			return WeaponSlot.RANGED;
		}
		if(slot.equals("BOTH")) {
			return WeaponSlot.BOTH;
		}
		return null;
	}
	
	public static HashMap<Integer, Stuff> getWeaponMap() {
		return weaponList;
	}
}
