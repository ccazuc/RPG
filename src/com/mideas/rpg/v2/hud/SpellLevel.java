/*package com.mideas.rpg.v2.hud;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.spell.SpellManager;

public class SpellLevel {

	private static boolean spell1;
	private static boolean spell3;
	private static boolean spell7;
	private static boolean spell10;
	private static boolean spell15;

	public static void addSpell() {
		if(Mideas.joueur1().getClasseString().equals("Guerrier")) {
			if(!spell1 && Mideas.joueur1().getLevel() >= 1) {
				Mideas.joueur1().setSpellUnlocked(0, SpellManager.getSpell(102));
				spell1 = true;
			}
			if(!spell3 && Mideas.joueur1().getLevel() >= 3) {
				Mideas.joueur1().setSpellUnlocked(1, SpellManager.getSpell(101));
				spell3 = true;
			}
			if(!spell7 && Mideas.joueur1().getLevel() >= 7) {
				Mideas.joueur1().setSpellUnlocked(2, SpellManager.getSpell(105));
				spell7 = true;
			}	
			if(!spell10 && Mideas.joueur1().getLevel() >= 10) {
				Mideas.joueur1().setSpellUnlocked(3, SpellManager.getSpell(104));
				spell10 = true;
			}
			if(!spell15 && Mideas.joueur1().getLevel() >= 15) {
				Mideas.joueur1().setSpellUnlocked(4, SpellManager.getSpell(103));
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
}*/
