package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.SpellHeal;
import com.mideas.rpg.v2.utils.Draw;

public class SpellBarFrame {
	
	private static Spell hoveredSpell;
	private static boolean hoverAttack;
	
	public static boolean draw() throws FileNotFoundException {
		hoverAttack = false;
		hoveredSpell = null;
		if(Mideas.getLevel() == 70) {
			Draw.drawColorQuad(Display.getWidth()/2-140, Display.getHeight()-80, 790, 11,  Color.decode("#680764"));
		}
		else if(Mideas.getLevel() > 1) {
			float e = ((float)Mideas.getExp()-(float)Mideas.getExpNeeded(Mideas.getLevel()-1))/((float)Mideas.getExpNeeded(Mideas.getLevel())-Mideas.getExpNeeded(Mideas.getLevel()-1));
			Draw.drawColorQuad(Display.getWidth()/2-393, Display.getHeight()-80, 790*e, 12,  Color.decode("#680764"));
		}
		else {
			float e = ((float)Mideas.getExp()/Mideas.getExpNeeded(Mideas.getLevel()));
			Draw.drawColorQuad(Display.getWidth()/2-393, Display.getHeight()-80, 790*e, 12,  Color.decode("#680764"));
		}
        //Draw.drawQuad(Sprites.spellbar, Display.getWidth()/2-300, Display.getHeight()-120);  
        Draw.drawQuad(Sprites.large_spellbar, Display.getWidth()/2-570, Display.getHeight()-140);                                                         //spellBar
		Draw.drawQuad(Sprites.spell_attack, Display.getWidth()/2-395, Display.getHeight()-58);                                              //attack
		Draw.drawQuad(Sprites.border, Display.getWidth()/2-399, Display.getHeight()-66);  
		TTF2.statsName.drawStringShadow(Display.getWidth()/2+5-TTF2.statsName.getWidth(Mideas.getFps()), Display.getHeight()-180, Mideas.getFps(), Color.yellow, Color.black, 1, 1, 1);
		TTF2.font5.drawString(Display.getWidth()/2-363, Display.getHeight()-61, "1", Color.white);
		if(Mideas.mouseX() >= Display.getWidth()/2-395 && Mideas.mouseX() <= Display.getWidth()/2-343 && Mideas.mouseY() >= Display.getHeight()-61 && Mideas.mouseY() <= Display.getHeight()-5) {
			Draw.drawQuad(Sprites.hover, Display.getWidth()/2-402, Display.getHeight()-63);	
			Draw.drawQuad(Sprites.tooltip, Display.getWidth()/2-401, Display.getHeight()-220);
			TTF2.spellName.drawStringShadow(Display.getWidth()/2-393, Display.getHeight()-210, "Attack", Color.white, Color.black, 1, 1, 1);
			TTF2.font4.drawStringShadow(Display.getWidth()/2-393, Display.getHeight()-188, "No mana cost", Color.white, Color.black, 1, 1, 1);
			hoverAttack = true;
		}
		else {
			hoverAttack = false;
		}
		int x = -260;
		int spellCount = 0;
		for(Spell spell : Mideas.joueur1().getSpells()) {
			if(spell != null && spellCount < 11) {
				if(DragSpellManager.getDraggedSpell() != spell) {
					Draw.drawQuad(spell.getSprite(), Display.getWidth()/2-68+x, Display.getHeight()-58);
					Draw.drawQuad(Sprites.border, Display.getWidth()/2-76+x, Display.getHeight()-66);
				}
				if(Mideas.mouseX() >= Display.getWidth()/2-72+x && Mideas.mouseY() >= Display.getHeight()-61 && Mideas.mouseX() <= Display.getWidth()/2-16+x && Mideas.mouseY() <= Display.getHeight()-5) {
					Draw.drawQuad(Sprites.hover, Display.getWidth()/2-75+x, Display.getHeight()-63);
					Draw.drawQuad(Sprites.tooltip, Display.getWidth()/2-74+x, Display.getHeight()-220);
					TTF2.font4.drawString(Display.getWidth()/2-66+x, Display.getHeight()-210, spell.getName()+"", Color.white);
					TTF2.font4.drawString(Display.getWidth()/2-66+x, Display.getHeight()-188, spell.getManaCost()+" Mana", Color.white);
					if(spell instanceof SpellHeal) {
						TTF2.font4.drawStringShadow(Display.getWidth()/2-66+x, Display.getHeight()-158, "Heals you for "+spell.getHeal(), Color.yellow, Color.black, 1, 1, 1);
					}
					else {
						TTF2.font4.drawStringShadow(Display.getWidth()/2-66+x, Display.getHeight()-158, "Deals "+spell.getBaseDamage()+" damage to the enemy", Color.yellow, Color.black, 1, 1, 1);
					}
					hoveredSpell = spell;
				}
				if(DragSpellManager.getDraggedSpell() != spell) {
					if(spellCount+2 <= 9) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2-35+x, Display.getHeight()-61, String.valueOf(spellCount+2), Color.white, Color.black, 1, 1, 1);
					}
					else if(spellCount+2 == 10) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2-35+x, Display.getHeight()-61, "0", Color.white, Color.black, 1, 1, 1);
					}
					else if(spellCount+2 == 11) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2-35+x, Display.getHeight()-61, ")", Color.white, Color.black, 1, 1, 1);
					}
					else if(spellCount+2 == 12) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2-35+x, Display.getHeight()-61, "=", Color.white, Color.black, 1, 1, 1);
					}
					if(Spell.getSpellCd(spell) > 0) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2-35+x, Display.getHeight()-34, String.valueOf(Spell.getSpellCd(spell)), Color.white, Color.black, 1, 1, 1);
					}
				}
			}
			x+= 67;
			spellCount++;
		}
		return false;
	}
	
	public static boolean mouseEvent() {
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(hoverAttack) {
					Mideas.joueur1().tick();
					Mideas.setCurrentPlayer(false);
				}
				else if(hoveredSpell != null) {
					if(Spell.getSpellCd(hoveredSpell) <= 0 && Mideas.joueur1().cast(hoveredSpell)) {
						//hoveredSpell.setSpellCd(hoveredSpell,hoveredSpell.getSpellBaseCd());
						checkClickCd();
					}
					else if (Spell.getSpellCd(hoveredSpell) <= 0){
						Mideas.joueur1().cast(hoveredSpell);
					}
					else {
						Mideas.joueur1().tick();
					}
					Mideas.setCurrentPlayer(false);
				}
			}
		}
		return false;
	}
	
	public static boolean keyboardEvent() {
		if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == Keyboard.KEY_1) {
			Mideas.joueur1().tick();
			Mideas.setCurrentPlayer(false);
			return true;
		}
		if(Keyboard.getEventKey() == Keyboard.KEY_2) {
			/*if(Spell.getSpellCd(Mideas.joueur1.getSpells()[0]) <= 0 && Mideas.joueur1().cast(Mideas.joueur1().getSpells()[0])) {
				//Mideas.joueur1().cast(Mideas.joueur1().getSpells()[2]);
				Mideas.joueur1.getSpells()[0].setSpellCd(Mideas.joueur1.getSpells()[0], Mideas.joueur1.getSpells()[0].getSpellBaseCd());
			}
			else if (Spell.getSpellCd(Mideas.joueur1.getSpells()[0]) <= 0){
				Mideas.joueur1.cast(Mideas.joueur1.getSpells()[0]);
			}
			else {
				Mideas.joueur1.tick();
			}*/
			keyboardAttack(0);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_3) {
			keyboardAttack(1);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_4) {
			keyboardAttack(2);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_5) {
			keyboardAttack(3);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_6) {
			keyboardAttack(4);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_7) {
			keyboardAttack(5);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_8) {
			keyboardAttack(6);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_9) {
			keyboardAttack(7);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_0) {
			keyboardAttack(8);
			return true;
		}
		else if(Keyboard.getEventKey() == 26) {
			keyboardAttack(9);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_EQUALS) {
			keyboardAttack(10);
			return true;
		}
		return false;
	}
	
	private static void keyboardAttack(int i) {
		if(Mideas.joueur1().getSpells()[i] != null) {
			if(Spell.getSpellCd(Mideas.joueur1().getSpells()[i]) <= 0 && Mideas.joueur1().cast(Mideas.joueur1().getSpells()[i])) {
				/*Mideas.joueur1.getSpells()[i].setSpellCd(Mideas.joueur1.getSpells()[i], Mideas.joueur1.getSpells()[i].getSpellBaseCd());*/
				checkKeyboardCd(i);
			}
			else if (Spell.getSpellCd(Mideas.joueur1().getSpells()[i]) <= 0){
				Mideas.joueur1().cast(Mideas.joueur1().getSpells()[i]);
			}
			else {
				Mideas.joueur1().tick();
			}
			Mideas.setCurrentPlayer(false);
		}
	}
	
	private static void checkClickCd() {
		for(Spell spell : Mideas.joueur1().getSpells()) {
			if(spell != null && hoveredSpell.getId() == spell.getId()) {
				spell.setSpellCd(spell, spell.getSpellBaseCd());
			}
		}
	}
	
	private static void checkKeyboardCd(int i) {
		for(Spell spell : Mideas.joueur1().getSpells()) {
			if(spell != null && Mideas.joueur1().getSpells()[i].getId() == spell.getId()) {
				spell.setSpellCd(spell, spell.getSpellBaseCd());
			}
		}
	}
}
