package com.mideas.rpg.v2.game.spell;

import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.jdo.JDOStatement;

public class SpellBarManager {

	public static void loadSpellBar() throws SQLException {
		int i = 0;
		int id;
		JDOStatement statement = Mideas.getJDO().prepare("SELECT slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slot10, slot11, slot12, slot13, slot14, slot15, slot16, slot17, slot18, slot19, slot20, slot21, slot22, slot23, slot24, slot25, slot26, slot27, slot28, slot29, slot30, slot31, slot32, slot33, slot34, slot35, slot36 FROM spellbar WHERE character_id = ?");
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
		if(statement.fetch()) {
			while(i < 36) {
				id = statement.getInt();
				if(StuffManager.exists(id)) {
					Mideas.joueur1().setSpells(i, new StuffShortcut(StuffManager.getStuff(id)));
				}
				else if(PotionManager.exists(id)) {
					Mideas.joueur1().setSpells(i, new PotionShortcut(PotionManager.getPotion(id)));
				}
				else if(SpellManager.exists(id)) {
					Mideas.joueur1().setSpells(i, new SpellShortcut(SpellManager.getBookSpell(id)));
				}
				else if(WeaponManager.exists(id)) {
					Mideas.joueur1().setSpells(i, new StuffShortcut(WeaponManager.getWeapon(id)));
				}
				i++;
			}
		}
	}
	
	public static void setSpellBar() throws SQLException {
		JDOStatement statement = Mideas.getJDO().prepare("UPDATE spellbar SET slot1 = ?, slot2 = ?, slot3 = ?, slot4 = ?, slot5 = ?, slot6 = ?, slot7 = ?, slot8 = ?, slot9 = ?, slot10 = ?, slot11 = ?, slot12 = ?, slot13 = ?, slot14 = ?, slot15 = ?, slot16 = ?, slot17 = ?, slot18 = ?, slot19 = ?, slot20 = ?, slot21 = ?, slot22 = ?, slot23 = ?, slot24 = ?, slot25 = ?, slot26 = ?, slot27 = ?, slot28 = ?, slot29 = ?, slot30 = ?, slot31 = ?, slot32 = ?, slot33 = ?, slot34 = ?, slot35 = ?, slot36 = ? WHERE character_id = ?");
		int i = 0;
		while(i < 36) {
			Shortcut tempSpell = Mideas.joueur1().getSpells(i);
			int id = 0;
			if(tempSpell != null) {
				id = tempSpell.getId();
			}
			statement.putInt(id);
			i++;
		}
		statement.putInt(Mideas.getCharacterId());
		statement.execute();
	}
}
