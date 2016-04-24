package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.spell.SpellManager;

public class SpellLevel {

	private static boolean spell1;
	private static boolean spell3;
	private static boolean spell7;
	private static boolean spell10;
	private static boolean spell15;

	public static void addSpell() throws FileNotFoundException, SQLException {
		if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			if(!spell1 && Mideas.getLevel() >= 1) {
				Mideas.joueur1().setSpellUnlocked(0, SpellManager.getBookSpell(102));
				spell1 = true;
			}
			if(!spell3 && Mideas.getLevel() >= 3) {
				Mideas.joueur1().setSpellUnlocked(1, SpellManager.getBookSpell(101));
				spell3 = true;
			}
			if(!spell7 && Mideas.getLevel() >= 7) {
				Mideas.joueur1().setSpellUnlocked(2, SpellManager.getBookSpell(105));
				spell7 = true;
			}	
			if(!spell10 && Mideas.getLevel() >= 10) {
				Mideas.joueur1().setSpellUnlocked(3, SpellManager.getBookSpell(104));
				spell10 = true;
			}
			if(!spell15 && Mideas.getLevel() >= 15) {
				Mideas.joueur1().setSpellUnlocked(4, SpellManager.getBookSpell(103));
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
