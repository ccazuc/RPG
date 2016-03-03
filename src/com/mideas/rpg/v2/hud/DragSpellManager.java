package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.list.Charge;
import com.mideas.rpg.v2.game.spell.list.HeroicStrike;
import com.mideas.rpg.v2.game.spell.list.MortalStrike;
import com.mideas.rpg.v2.game.spell.list.Rend;
import com.mideas.rpg.v2.game.spell.list.ThunderClap;
import com.mideas.rpg.v2.utils.Draw;

public class DragSpellManager {

	private static Spell draggedBookSpell;
	private static SpellShortcut draggedShortcutSpell;
	private static boolean[] hover = new boolean[13];
	
	public static void draw() {
		if(draggedBookSpell != null) {
			Draw.drawQuad(draggedBookSpell.getSprite(), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.border, Mideas.mouseX()-5, Mideas.mouseY()-5);
		}
		if(draggedShortcutSpell != null) {
			Draw.drawQuad(draggedShortcutSpell.getSprite(), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.border, Mideas.mouseX()-5, Mideas.mouseY()-5);
		}
	}
	
	public static boolean mouseEvent() {
		Arrays.fill(hover, false);
		int x = -332;
		int xShift = 68;
		int y = -61;
		int i = 0;
		while(i <= 12) {
			isHoverSpellBar(x+i*xShift, y, i);
			i++;
		}
		if(Mouse.getEventButton() == 1) {
			if(Mouse.getEventButtonState()) {
				i = 0;
				while(i <= 10) {
					clickSpell(i);
					i++;
				}
				if(SpellBookFrame.getHoverBook(1) && Mideas.joueur1().getSpellUnlocked(0) != null) {
					draggedBookSpell = new HeroicStrike();
				}
				else if(SpellBookFrame.getHoverBook(2) && Mideas.joueur1().getSpellUnlocked(1) != null) {
					draggedBookSpell = new Charge();
				}
				else if(SpellBookFrame.getHoverBook(3) && Mideas.joueur1().getSpellUnlocked(2) != null) {
					draggedBookSpell = new ThunderClap();
				}
				else if(SpellBookFrame.getHoverBook(4) && Mideas.joueur1().getSpellUnlocked(3) != null) {
					draggedBookSpell = new Rend();
				}
				else if(SpellBookFrame.getHoverBook(5) && Mideas.joueur1().getSpellUnlocked(4) != null) {
					draggedBookSpell = new MortalStrike();
				}
			}
			else {
				i = 0;
				while(i <= 10) {
					dropSpell(i);
					i++;
				}
				if(draggedShortcutSpell != null) {
					deleteSpell(draggedShortcutSpell);
					draggedShortcutSpell = null;
				}
				if(draggedBookSpell != null) {
					//deleteSpell(draggedSpellBook);
					draggedBookSpell = null;
				}
			}
		}
		return false;
	}
	
	private static void isHoverSpellBar(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+66 && Mideas.mouseY() >= Display.getHeight()+y && Mideas.mouseY() <= Display.getHeight()+y+56) {
			hover[i] = true;
		}
	}
	
	private static void deleteSpell(SpellShortcut draggedSpell) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(draggedSpell == Mideas.joueur1().getSpells(i)) {
				Mideas.joueur1().setSpells(i, null);
			}
			i++;
		}
	}
	
	private static void clickSpell(int i) {
		if(hover[i]) {
			SpellShortcut tempShortcutSpell = Mideas.joueur1().getSpells(i);
			draggedShortcutSpell = Mideas.joueur1().getSpells(i);
			if(tempShortcutSpell != null) {
				Mideas.joueur1().setSpells(i, tempShortcutSpell);
			}
		}
	}
	
	private static boolean setNullSpell(SpellShortcut draggedSpell, SpellShortcut slot) {
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
	
	private static void dropSpell(int i) {
		if(hover[i] && draggedShortcutSpell != null) {
			SpellShortcut tempSpell = Mideas.joueur1().getSpells(i);
			if(setNullSpell(draggedShortcutSpell, Mideas.joueur1().getSpells(i))) {
				Mideas.joueur1().setSpells(i, draggedShortcutSpell);
				draggedShortcutSpell = null;
			}
			else {
				Mideas.joueur1().setSpells(i, draggedShortcutSpell);
				draggedShortcutSpell = tempSpell;
			}
		}
		if(hover[i] && draggedBookSpell != null) {
			SpellShortcut tempSpell = Mideas.joueur1().getSpells(i);
			Mideas.joueur1().setSpells(i, Spell.getSpellToShortcut(draggedBookSpell));
			if(Mideas.joueur1().getSpells(i) != null) {
				draggedShortcutSpell = tempSpell;
			}
		}
	}
	
	public static SpellShortcut getDraggedSpell() {
		return draggedShortcutSpell;
	}
}
