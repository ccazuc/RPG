package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.game.spell.Spell;
import com.mideas.rpg.v2.game.spell.list.Charge;
import com.mideas.rpg.v2.game.spell.list.HeroicStrike;
import com.mideas.rpg.v2.game.spell.list.MortalStrike;
import com.mideas.rpg.v2.game.spell.list.Rend;
import com.mideas.rpg.v2.game.spell.list.ThunderClap;
import com.mideas.rpg.v2.utils.Draw;

public class DragSpellManager {

	private static Spell draggedBookSpell;
	private static Shortcut draggedShortcut;
	private static boolean[] hover = new boolean[13];
	
	public static void draw() {
		if(draggedBookSpell != null) {
			Draw.drawQuad(draggedBookSpell.getSprite(), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.border, Mideas.mouseX()-5, Mideas.mouseY()-5);
		}
		if(draggedShortcut != null) {
			Draw.drawQuad(draggedShortcut.getSprite(), Mideas.mouseX(), Mideas.mouseY());
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
		if(Mouse.getEventButtonState()) {
		   	if(Mouse.getEventButton() == 1) {
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
		}
		if(!Mouse.getEventButtonState()) {
			if(DragManager.getDraggedItem() != null && Mouse.getEventButton() == 0) {
				i = 0;
				while(i <= 10) {
					dropSpell(i);
					i++;
				}
			}
			else if(Mouse.getEventButton() == 1) {
				i = 0;
				while(i <= 10) {
					dropSpell(i);
					i++;
				}
			}
			if(draggedShortcut != null) {
				deleteSpell(draggedShortcut);
				//draggedShortcutSpell = null;
			}
			if(draggedBookSpell != null) {
				//deleteSpell(draggedSpellBook);
				draggedBookSpell = null;
			}
		}
		return false;
	}
	
	private static void isHoverSpellBar(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+66 && Mideas.mouseY() >= Display.getHeight()+y && Mideas.mouseY() <= Display.getHeight()+y+56) {
			hover[i] = true;
		}
	}
	
	private static void deleteSpell(Shortcut draggedSpell) {
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
			if(Mideas.joueur1().getSpells(i) instanceof SpellShortcut) {
				if(Mideas.joueur1().getSpells(i) == null) {
					if(draggedShortcut != null) {
						Mideas.joueur1().setSpells(i, draggedShortcut);
						draggedShortcut = null;
					}
				}
				else if(Mideas.joueur1().getSpells(i) != null) {
					if(draggedShortcut != null) {
						Shortcut tempShortcutSpell = Mideas.joueur1().getSpells(i);
						Mideas.joueur1().setSpells(i, draggedShortcut);
						draggedShortcut = tempShortcutSpell;
					}
					else {
						draggedShortcut = Mideas.joueur1().getSpells(i);
						Mideas.joueur1().setSpells(i, null);
					}
				}
			}
			else if(Mideas.joueur1().getSpells(i) instanceof StuffShortcut) {
				
			}
		}
	}
	
	private static boolean setNullSpell(Shortcut spell) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(spell == Mideas.joueur1().getSpells(i)) {
				Mideas.joueur1().setSpells(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static void dropSpell(int i) {
		if(hover[i] && draggedShortcut != null) {
			if(Mideas.joueur1().getSpells(i) == null) {
				setNullSpell(draggedShortcut);
				Mideas.joueur1().setSpells(i, draggedShortcut);
				draggedShortcut = null;
			}
			else {
				Shortcut tempSpellShortcut = Mideas.joueur1().getSpells(i);
				Mideas.joueur1().setSpells(i, tempSpellShortcut);
				setNullSpell(draggedShortcut);
				draggedShortcut = tempSpellShortcut;
			}
		}
		else if(hover[i] && draggedBookSpell != null) {
			if(Mideas.joueur1().getSpells(i) == null) {
				Mideas.joueur1().setSpells(i, new SpellShortcut(draggedBookSpell));
				draggedShortcut = null;
			}
			else {
				Shortcut tempSpell = Mideas.joueur1().getSpells(i);
				Mideas.joueur1().setSpells(i, new SpellShortcut(draggedBookSpell));
				draggedShortcut = tempSpell;
			}
		}
		else if(hover[i] && DragManager.getDraggedItem() != null) {
			if(Mideas.joueur1().getSpells(i) == null) {
				Mideas.joueur1().setSpells(i, new StuffShortcut((Stuff)DragManager.getDraggedItem()));
				DragManager.setDraggedItem(null);
			}
		}
	}
	
	public static Shortcut getDraggedSpell() {
		return draggedShortcut;
	}
}
