package com.mideas.rpg.v2.game.spell;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class SpellManager {

	private static HashMap<Integer, Integer> spellCdList = new HashMap<Integer, Integer>();
	private static ArrayList<Spell> spellList = new ArrayList<Spell>();
	private static ArrayList<SpellShortcut> spellShortcutList = new ArrayList<SpellShortcut>();
	private static int numberSpellLoaded;
	
	public static void loadSpells() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, type, basedamage, manacost, baseheal, basecd, cd FROM spell");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			String sprite_id = statement.getString();
			String name = statement.getString();
			String tempType = statement.getString();
			SpellType type = getSpellType(tempType);
			int baseDamage = statement.getInt();
			int manaCost = statement.getInt();
			int baseHeal = statement.getInt();
			int baseCd = statement.getInt();
			int cd = statement.getInt();
			Spell newPiece = new Spell(id, sprite_id, name, type, baseDamage, manaCost, baseHeal, baseCd, cd);
			SpellShortcut newShortcutPiece = new SpellShortcut(id, sprite_id, name, type, baseDamage, manaCost, baseHeal, baseCd, cd);
			spellList.add(newPiece);
			spellShortcutList.add(newShortcutPiece);
			spellCdList.put(id, 0);
			numberSpellLoaded++;
		}
	}
	
	public static SpellType getSpellType(String type) {
		if(type.equals("DAMAGE")) {
			return SpellType.DAMAGE;
		}
		if(type.equals("HEAL")) {
			return SpellType.HEAL;
		}
		return null;
	}
	
	public static Spell getBookSpell(int id) {
		int i = 0;
		while(i < spellList.size()) {
			if(spellList.get(i).getSpellId() == id) {
				return spellList.get(i);
			}
			i++;
		}
		return null;
	}

	public static SpellShortcut getShortcutSpell(int id) {
		int i = 0;
		while(i < spellShortcutList.size()) {
			if(spellShortcutList.get(i).getSpellId() == id) {
				return spellShortcutList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public static int getCd(int id) {
		return spellCdList.get(id);
	}
	
	public static void setCd(int id, int cd) {
		spellCdList.put(id, cd);
	}
	
	public static ArrayList<SpellShortcut> getSpellShortcutList() {
		return spellShortcutList;
	}
	
	public static int getNumberSpellLoaded() {
		return numberSpellLoaded;
	}
}
