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
	
	/*public static void loadSpells() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, sprite_id, name, type, basedamage, manacost, baseheal, basecd, cd, cast_time FROM spell");
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
			int time = statement.getInt();
			Spell newSpell = new Spell(id, sprite_id, name, type, baseDamage, manaCost, baseHeal, baseCd, cd, time);
			SpellShortcut newShortcutSpell = new SpellShortcut(newSpell);
			spellList.add(newSpell);
			spellShortcutList.add(newShortcutSpell);
			spellCdList.put(id, 0);
			numberSpellLoaded++;
		}
	}*/
	
	public static void loadSpells() {
		try {
			JDOStatement statement = Mideas.getJDO().prepare("SELECT type, id FROM spell");
			statement.execute();
			while(statement.fetch()) {
				String type = statement.getString();
				int id = statement.getInt();
				if(type.equals("DAMAGE")) {
					JDOStatement statements = Mideas.getJDO().prepare("SELECT sprite_id, name, damage, manaCost, cd, cast_time, stun_duration, stun_rate FROM SPELL WHERE id = ?");
					statements.putInt(id);
					statements.execute();
					if(statements.fetch()) {
						String sprite_id = statements.getString();
						String name = statements.getString();
						SpellType spellType = getSpellType(type);
						int damage = statements.getInt();
						int manaCost = statements.getInt();
						int cd = statements.getInt();
						int castTime = statements.getInt();
						int stunDuration = statements.getInt();
						float stunRate = statements.getFloat();
						spellCdList.put(id, 0);
						spellList.add(new Spell(id, sprite_id, name, spellType, damage, manaCost, stunRate, stunDuration, cd, castTime));
						/*spellList.add(new Spell(id, sprite_id, name, spellType, damage, manaCost, stunRate, stunDuration, cd, castTime) {
							@Override
							public void action(Joueur caster, Joueur target) {
								if(caster.getMana() >= this.getManaCost()) {
									this.doDamage(caster, target);
									caster.setMana(Math.max(0, caster.getMana()-manaCost));
								}
								else {
									RedAlertFrame.addNewAlert("Not enough mana.");
								}
							}
						});*/
					}
				}
				else if(type.equals("HEAL")) {
					JDOStatement statements = Mideas.getJDO().prepare("SELECT sprite_id, name, heal, manaCost, cd, cast_time FROM SPELL WHERE id = ?");
					statements.putInt(id);
					statements.execute();
					if(statements.fetch()) {
						String sprite_id = statements.getString();
						String name = statements.getString();
						int heal = statements.getInt();
						SpellType spellType = getSpellType(type);
						int manaCost = statements.getInt();
						int cd = statements.getInt();
						int castTime = statements.getInt();
						spellCdList.put(id, 0);
						spellList.add(new Spell(id, sprite_id, name, spellType, manaCost, heal, cd, castTime));
						/*spellList.add(new Spell(id, sprite_id, name, spellType, manaCost, heal, cd, castTime) {
							@Override
							public void action(Joueur target, Joueur caster) {
								this.doHeal(target, caster);
							}
						});*/
					}
				}
				else if(type.equals("HEALANDDAMAGE")) {
					
				}
				else if(type.equals("OTHER")) {
					
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean exists(int id) {
		return spellList.contains(getBookSpell(id));
	}
	
	public static SpellType getSpellType(String type) {
		if(type.equals("DAMAGE")) {
			return SpellType.DAMAGE;
		}
		if(type.equals("HEAL")) {
			return SpellType.HEAL;
		}
		if(type.equals("HEALANDDAMAGE")) {
			return SpellType.HEALANDDAMAGE;
		}
		if(type.equals("OTHER")) {
			return SpellType.OTHER;
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
			if(spellShortcutList.get(i).getSpell().getSpellId() == id) {
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
