package com.mideas.rpg.v2.game.classes;

import java.sql.SQLException;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.weapon.WeaponType;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class ClassManager {

	private static HashMap<String, Joueur> playerList = new HashMap<String, Joueur>();
	private static HashMap<String, Joueur> iaList = new HashMap<String, Joueur>();
	
	public static void loadClasses() {
		try {
			JDOStatement statement = Mideas.getJDO().prepare("SELECT id, tempId, wear, weapon_type, stamina, mana, strength, armor, default_armor, critical, max_stamina, max_mana, exp_gained, gold_gained FROM player");
			statement.execute();
			while(statement.fetch()) {
				String id = statement.getString();
				int tempId = statement.getInt();
				String temp = statement.getString();
				Wear wear = getWear(temp);
				short tempType = statement.getShort();
				WeaponType[] type = getWeaponTypes(tempType);
				int stamina = statement.getInt();
				int mana = statement.getInt();
				int strength = statement.getInt();
				int armor = statement.getInt();
				int defaultArmor = statement.getInt();
				int critical = statement.getInt();
				int maxStamina = statement.getInt();
				int maxMana = statement.getInt();
				int expGained = statement.getInt();
				int goldGained = statement.getInt();
				Shortcut[] spells = new Shortcut[49];
				Shortcut[] spellss = new Shortcut[49];
				Spell[] spellUnlocked = new Spell[49];
				Spell[] spellUnlockeds = new Spell[49];
				Stuff[] stuff = new Stuff[19];
				playerList.put(id, new Joueur(Joueur.convStringToClassType(id), tempId, wear, type, stamina, mana, strength, armor, defaultArmor, critical, maxStamina, maxMana, expGained, goldGained, spells, spellUnlocked, stuff));
				iaList.put(id, new Joueur(Joueur.convStringToClassType(id), tempId, wear, type, stamina, mana, strength, armor, defaultArmor, critical, maxStamina, maxMana, expGained, goldGained, spellss, spellUnlockeds, stuff));
				initTable(id);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static WeaponType[] getWeaponTypes(short type) {
		int i = 0;
		int count = 0;
		while(i < 15) {
			if((type & (1 << i)) != 0) {
				count++;
			}
			i++;
		}
		WeaponType[] tempWeaponsType = new WeaponType[count];
		count = 0;
		if((type & (1 << 0)) != 0) {
			tempWeaponsType[count] = WeaponType.DAGGER;
			count++;
		}
		if((type & (1 << 1)) != 0) {
			tempWeaponsType[count] = WeaponType.FISTWEAPON;
			count++;
		}
		if((type & (1 << 2)) != 0) {
			tempWeaponsType[count] = WeaponType.ONEHANDEDAXE;
			count++;
		}
		if((type & (1 << 3)) != 0) {
			tempWeaponsType[count] = WeaponType.TWOHANDEDAXE;
			count++;
		}
		if((type & (1 << 4)) != 0) {
			tempWeaponsType[count] = WeaponType.ONEHANDEDMACE;
			count++;
		}
		if((type & (1 << 5)) != 0) {
			tempWeaponsType[count] = WeaponType.TWOHANDEDMACE;
			count++;
		}
		if((type & (1 << 6)) != 0) {
			tempWeaponsType[count] = WeaponType.ONEHANDEDSWORD;
			count++;
		}
		if((type & (1 << 7)) != 0) {
			tempWeaponsType[count] = WeaponType.TWOHANDEDSWORD;
			count++;
		}
		if((type & (1 << 8)) != 0) {
			tempWeaponsType[count] = WeaponType.POLEARM;
			count++;
		}
		if((type & (1 << 9)) != 0) {
			tempWeaponsType[count] = WeaponType.STAFF;
			count++;
		}
		if((type & (1 << 10)) != 0) {
			tempWeaponsType[count] = WeaponType.BOW;
			count++;
		}
		if((type & (1 << 11)) != 0) {
			tempWeaponsType[count] = WeaponType.CROSSBOW;
			count++;
		}
		if((type & (1 << 12)) != 0) {
			tempWeaponsType[count] = WeaponType.GUN;
			count++;
		}
		if((type & (1 << 13)) != 0) {
			tempWeaponsType[count] = WeaponType.WAND;
			count++;
		}
		if((type & (1 << 14)) != 0) {
			tempWeaponsType[count] = WeaponType.THROWN;
			count++;
		}
		i = 0;
		while(i < count) {
			i++;
		}
		return tempWeaponsType;
	}
	
	public static Joueur getClone(String id) {
		Joueur temp = iaList.get(id);
		if(temp != null) {
			return new Joueur(temp);
		}
		return null;
	}
	
	public static Joueur getPlayerClone(String id) {
		Joueur temp = playerList.get(id);
		if(temp != null) {
			return new Joueur(temp);
		}
		return null;
	}
	
	public static Joueur getPlayer(String id) {
		return iaList.get(id);
	}
	
	public static boolean exists(String id) {
		return iaList.containsKey(id);
	}
	
	public static Joueur getIA(String id) {
		return iaList.get(id);
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
	
	private static void initTable(String id) {
		if(id.equals("Guerrier")) {
			playerList.get("Guerrier").setSpellUnlocked(0, SpellManager.getBookSpell(102));
			//joueurList.get("Guerrier").setSpells(0, SpellManager.getShortcutSpell(102));
			iaList.get("Guerrier").setSpellUnlocked(0, SpellManager.getBookSpell(102));
			iaList.get("Guerrier").setSpells(0, SpellManager.getShortcutSpell(102));
		}
	}
}
