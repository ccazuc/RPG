package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.spell.Charge;
import com.mideas.rpg.v2.game.spell.HeroicStrike;
import com.mideas.rpg.v2.game.spell.MortalStrike;
import com.mideas.rpg.v2.game.spell.Rend;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.ThunderClap;

public class SpellLevel {

	private static boolean spell1;
	private static boolean spell3;
	private static boolean spell7;
	private static boolean spell10;
	private static boolean spell15;

	public static void addSpell() throws FileNotFoundException, SQLException {
		if(Mideas.joueur1().getClasse() == "Guerrier") {
			if(!spell1 && Mideas.getLevel() >= 1) {
				Mideas.joueur1().setSpellUnlocked(0, new HeroicStrike());
				spell1 = true;
			}
			if(!spell3 && Mideas.getLevel() >= 3) {
				checkSpellSlot(new Charge());
				Mideas.joueur1().setSpellUnlocked(1, new Charge());
				spell3 = true;
			}
			if(!spell7 && Mideas.getLevel() >= 7) {
				checkSpellSlot(new ThunderClap());
				Mideas.joueur1().setSpellUnlocked(2, new ThunderClap());
				spell7 = true;
			}	
			if(!spell10 && Mideas.getLevel() >= 10) {
				checkSpellSlot(new Rend());
				Mideas.joueur1().setSpellUnlocked(3, new Rend());
				spell10 = true;
			}
			if(!spell15 && Mideas.getLevel() >= 15) {
				checkSpellSlot(new MortalStrike());
				Mideas.joueur1().setSpellUnlocked(4, new MortalStrike());
				spell15 = true;
			}	
		}
	}
	
	public static void setSpellLevelFalse() {
		spell1 = false;
		spell3 = false;
		spell7 = false;
		spell10 = false;
		spell15 = false;
	}
	
	private static boolean checkSpellSlot(Spell spell) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(Mideas.joueur1().getSpells(i) == null) {
				Mideas.joueur1().setSpells(i, spell);
				return true;
			}
			i++;
		}
		return false;
	}
}
