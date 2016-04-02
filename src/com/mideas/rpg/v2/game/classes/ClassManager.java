package com.mideas.rpg.v2.game.classes;

import java.sql.SQLException;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.Wear;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class ClassManager {

	private static HashMap<String, Joueur> joueurList = new HashMap<String, Joueur>();
	private static HashMap<String, Joueur> iaList = new HashMap<String, Joueur>();
	
	public static void loadClasses() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, wear, stamina, mana, strength, armor, default_armor, critical, max_stamina, max_mana, exp_gained, gold_gained FROM player");
		statement.execute();
		while(statement.fetch()) {
			String id = statement.getString();
			String temp = statement.getString();
			Wear wear = getWear(temp);
			int stamina = statement.getInt();
			int mana = statement.getInt();
			int strength = statement.getInt();
			int armor = statement.getInt();
			int defaultArmor = statement.getInt();
			int critical = statement.getInt();
			int maxStamina = statement.getInt();
			int maxMana = statement.getInt();
			int expGained = statement.getInt();
			int goldGained =statement.getInt();
			Shortcut[] spells = new Shortcut[49];
			Spell[] spellUnlocked = new Spell[49];
			Stuff[] stuff = new Stuff[20];
			Joueur newClass = new Joueur(id, wear, stamina, mana, strength, armor, defaultArmor, critical, maxStamina, maxMana, expGained, goldGained, spells, spellUnlocked, stuff);
			Joueur iaClass = new Joueur(id, wear, stamina, mana, strength, armor, defaultArmor, critical, maxStamina, maxMana, expGained, goldGained, spells, spellUnlocked, stuff);
			joueurList.put(id, newClass);
			iaList.put(id, iaClass);
			initTable(id);
		}
	}
	
	public static Joueur getClone(String id) {
		Joueur temp = iaList.get(id);
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
			joueurList.get("Guerrier").setSpellUnlocked(0, SpellManager.getBookSpell(102));
			joueurList.get("Guerrier").setSpells(0, SpellManager.getShortcutSpell(102));
			iaList.get("Guerrier").setSpellUnlocked(0, SpellManager.getBookSpell(102));
			iaList.get("Guerrier").setSpells(0, SpellManager.getShortcutSpell(102));
		}
	}
}
