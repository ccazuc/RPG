package com.mideas.rpg.v2.game.spell;

import java.sql.SQLException;
import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class SpellManager {

	private final static HashMap<Integer, Spell> spellMap = new HashMap<Integer, Spell>();
	private static int numberSpellLoaded;
	
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
						spellMap.put(id, new Spell(id, sprite_id, name, spellType, damage, manaCost, stunRate, stunDuration, cd, castTime));
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
						spellMap.put(id, new Spell(id, sprite_id, name, spellType, manaCost, heal, cd, castTime));
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
	
	public static Spell getSpell(int id) {
		return spellMap.get(id);
	}
	
	public static boolean exists(int id) {
		return spellMap.containsKey(id);
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
	
	public static int getCDLength(int id) {
		if(spellMap.containsKey(id)) {
			return spellMap.get(id).getSpellCDLength();
		}
		return -1;
	}
	
	public static void setCd(int id, long cdStart, int cd) {
		if(spellMap.containsKey(id)) {
			spellMap.get(id).setSpellCd(cdStart, cd);
		}
	}
	
	public static int getNumberSpellLoaded() {
		return numberSpellLoaded;
	}
}
