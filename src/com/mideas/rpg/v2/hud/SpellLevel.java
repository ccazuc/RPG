package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.spell.list.Charge;
import com.mideas.rpg.v2.game.spell.list.HeroicStrike;
import com.mideas.rpg.v2.game.spell.list.MortalStrike;
import com.mideas.rpg.v2.game.spell.list.Rend;
import com.mideas.rpg.v2.game.spell.list.ThunderClap;

public class SpellLevel {

	private static boolean spell1;
	private static boolean spell3;
	private static boolean spell7;
	private static boolean spell10;
	private static boolean spell15;

	public static void addSpell() throws FileNotFoundException, SQLException {
		if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			if(!spell1 && Mideas.getLevel() >= 1) {
				Mideas.joueur1().setSpellUnlocked(0, new HeroicStrike());
				spell1 = true;
			}
			if(!spell3 && Mideas.getLevel() >= 3) {
				Mideas.joueur1().setSpellUnlocked(1, new Charge());
				spell3 = true;
			}
			if(!spell7 && Mideas.getLevel() >= 7) {
				Mideas.joueur1().setSpellUnlocked(2, new ThunderClap());
				spell7 = true;
			}	
			if(!spell10 && Mideas.getLevel() >= 10) {
				Mideas.joueur1().setSpellUnlocked(3, new Rend());
				spell10 = true;
			}
			if(!spell15 && Mideas.getLevel() >= 15) {
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
}
