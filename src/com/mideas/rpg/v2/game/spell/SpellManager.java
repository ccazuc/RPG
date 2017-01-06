package com.mideas.rpg.v2.game.spell;

import java.util.HashMap;

public class SpellManager {

	//private static JDOStatement loadSpells;
	private final static HashMap<Integer, Spell> spellMap = new HashMap<Integer, Spell>();
	private static int numberSpellLoaded;
	
	public static void loadSpells() {
		/*try {
			if(loadSpells == null) {
				loadSpells = Mideas.getJDO().prepare("SELECT id, sprite_id, name, effectValue, stun_duration, stun_rate, manaCost, trigger_gcd, cd, cast_time FROM spell");
			}
			loadSpells.clear();
			loadSpells.execute();
			while(loadSpells.fetch()) {
				int id = loadSpells.getInt();
				String sprite_id = loadSpells.getString();
				String name = loadSpells.getString();
				int effectValue = loadSpells.getInt();
				int stunDuration = loadSpells.getInt();
				float stunRate = loadSpells.getFloat();
				int manaCost = loadSpells.getInt();
				boolean triggerGCD = loadSpells.getBoolean();
				int cd = loadSpells.getInt();
				int castTime = loadSpells.getInt();
				spellMap.put(id, new Spell(id, sprite_id, name, effectValue, manaCost, stunRate, stunDuration, cd, castTime, triggerGCD));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}*/
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
	
	public static void storeSpell(Spell spell) {
		spellMap.put(spell.getSpellId(), spell);
	}
}
