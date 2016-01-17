package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.spell.Charge;
import com.mideas.rpg.v2.game.spell.HeroicStrike;
import com.mideas.rpg.v2.game.spell.MortalStrike;
import com.mideas.rpg.v2.game.spell.Rend;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.ThunderClap;
import com.mideas.rpg.v2.utils.Draw;

public class DragSpellManager {

	private static Spell draggedSpell;
	private static boolean[] hover = new boolean[12];
	
	public static void draw() {
		if(draggedSpell != null) {
			Draw.drawQuad(draggedSpell.getSprite(), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.border, Mideas.mouseX()-5, Mideas.mouseY()-5);
		}
	}
	
	private static void isHover(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+66 && Mideas.mouseY() >= Display.getHeight()+y && Mideas.mouseY() <= Display.getHeight()+y+56) {
			hover[i] = true;
		}
	}
	
	public static boolean mouseEvent() {
		int x = -332;
		int xShift = 68;
		int y = -61;
		Arrays.fill(hover, false);
		isHover(x, y, 0);
		isHover(x+xShift, y, 1);
		isHover(x+2*xShift, y, 2);
		isHover(x+3*xShift, y, 3);
		isHover(x+4*xShift, y, 4);
		isHover(x+5*xShift, y, 5);
		isHover(x+6*xShift, y, 6);
		isHover(x+7*xShift, y, 7);
		isHover(x+8*xShift, y, 8);
		isHover(x+9*xShift, y, 9);
		isHover(x+10*xShift, y, 10);
		isHover(x+11*xShift, y, 11);
		if(Mouse.getEventButton() == 1) {
			if(Mouse.getEventButtonState()) {
				clickSpell(0);
				clickSpell(1);
				clickSpell(2);
				clickSpell(3);
				clickSpell(4);
				clickSpell(5);
				clickSpell(6);
				clickSpell(7);
				clickSpell(8);
				clickSpell(9);
				clickSpell(10);
				if(SpellBookFrame.getHoverBook(1) && Mideas.joueur1().getSpellUnlocked(0) != null) {
					draggedSpell = new HeroicStrike();
				}
				else if(SpellBookFrame.getHoverBook(2) && Mideas.joueur1().getSpellUnlocked(1) != null) {
					draggedSpell = new Charge();
				}
				else if(SpellBookFrame.getHoverBook(3) && Mideas.joueur1().getSpellUnlocked(2) != null) {
					draggedSpell = new ThunderClap();
				}
				if(SpellBookFrame.getHoverBook(4) && Mideas.joueur1().getSpellUnlocked(3) != null) {
					draggedSpell = new Rend();
				}
				if(SpellBookFrame.getHoverBook(5) && Mideas.joueur1().getSpellUnlocked(4) != null) {
					draggedSpell = new MortalStrike();
				}
			}
			else {
				dropSpell(0);
				dropSpell(1);
				dropSpell(2);
				dropSpell(3);
				dropSpell(4);
				dropSpell(5);
				dropSpell(6);
				dropSpell(7);
				dropSpell(8);
				dropSpell(9);
				dropSpell(10);
				deleteSpell(draggedSpell);
				draggedSpell = null;
			}
		}
		return false;
	}
	
	private static void deleteSpell(Spell draggedSpell) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(draggedSpell == Mideas.joueur1().getSpells(i)) {
				Mideas.joueur1().setSpells(i, null);
			}
			i++;
		}
	}
	
	private static boolean setNullSpell(Spell draggedSpell, Spell slot) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(draggedSpell == Mideas.joueur1().getSpells(i)) {
				Mideas.joueur1().setSpells(i, slot);
				return true;
			}
			i++;
		}
		return false;
	}
	
	/*private static boolean checkSpell() {
		if(Mideas.joueur1().getSpellUnlocked(0) != null && draggedSpell == Mideas.joueur1().getSpellUnlocked(0)) {
			return true;
		}
		else if(Mideas.joueur1().getSpellUnlocked(1) != null && draggedSpell == Mideas.joueur1().getSpellUnlocked(1)) {
			return true;
		}
		else if(Mideas.joueur1().getSpellUnlocked(2) != null && draggedSpell == Mideas.joueur1().getSpellUnlocked(2)) {
			return true;
		}
		else if(Mideas.joueur1().getSpellUnlocked(3) != null && draggedSpell == Mideas.joueur1().getSpellUnlocked(3)) {
			return true;
		}
		else if(Mideas.joueur1().getSpellUnlocked(4) != null && draggedSpell == Mideas.joueur1().getSpellUnlocked(4)) {
			return true;
		}
		else if(Mideas.joueur1().getSpellUnlocked(5) != null && draggedSpell == Mideas.joueur1().getSpellUnlocked(5)) {
			return true;
		}
		return false;
	}*/
	
	private static void clickSpell(int i) {
		if(hover[i]) {
			Spell tempSpell = draggedSpell;
			draggedSpell = Mideas.joueur1().getSpells(i);
			if(tempSpell != null) {
				Mideas.joueur1().setSpells(i, tempSpell);
			}
		}
	}
	
	private static void dropSpell(int i) {
		if(hover[i] && draggedSpell != null) {
			Spell tempSpell = Mideas.joueur1().getSpells(i);
			if(setNullSpell(draggedSpell, Mideas.joueur1().getSpells(i))) {
				Mideas.joueur1().setSpells(i, draggedSpell);
				draggedSpell = null;
			}
			else {
				Mideas.joueur1().setSpells(i, draggedSpell);
				draggedSpell = tempSpell;
			}
		}
	}
	
	public static Spell getDraggedSpell() {
		return draggedSpell;
	}
}
