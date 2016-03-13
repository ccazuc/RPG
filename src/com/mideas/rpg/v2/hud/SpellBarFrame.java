package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.shortcut.PotionShortcut;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.utils.Draw;

public class SpellBarFrame {
	
	private static Shortcut hoveredSpell;
	private static boolean hoverAttack;
	private static boolean bagChange = true;
	private static boolean[] hoverSpellBar = new boolean[35];
	
	public static boolean draw() throws FileNotFoundException, SQLException {
		Arrays.fill(hoverSpellBar, false);
		hoverAttack = false;
		hoveredSpell = null;
		if(Mideas.getLevel() >= 70) {
			Draw.drawColorQuad(Display.getWidth()/2-678, Display.getHeight()-75, 1339, 13,  Color.decode("#680764"));
		}
		else if(Mideas.getLevel() > 1) {
			float e = ((float)Mideas.getCurrentExp()-(float)Mideas.getExpNeeded(Mideas.getLevel()-1))/((float)Mideas.getExpNeeded(Mideas.getLevel())-Mideas.getExpNeeded(Mideas.getLevel()-1));
			Draw.drawColorQuad(Display.getWidth()/2-678, Display.getHeight()-75, 1339*e, 13,  Color.decode("#680764"));
		}
		else {
			float e = ((float)Mideas.getCurrentExp()/Mideas.getExpNeeded(Mideas.getLevel()));
			Draw.drawColorQuad(Display.getWidth()/2-678, Display.getHeight()-85, 1339*e, 13,  Color.decode("#680764"));
		}
        Draw.drawQuad(Sprites.final_spellbar, Display.getWidth()/2-809, Display.getHeight()-100);
       /* float xShift = 56.3f;
        int i = 0;
        while(i < 24) {
        	if(i%2 == 0) {
        		Draw.drawQuad(Sprites.spellbar_case, Display.getWidth()/2-678+i*xShift, Display.getHeight()-130);
        	}
        	else {
        		Draw.drawQuad(Sprites.spellbar_case2, Display.getWidth()/2-678+i*xShift, Display.getHeight()-130);
        	}
            i++;
        }*/
		Draw.drawQuad(Sprites.spell_attack, Display.getWidth()/2-678, Display.getHeight()-49);
		Draw.drawQuad(Sprites.spell_border, Display.getWidth()/2-679, Display.getHeight()-51);  
		TTF2.statsName.drawStringShadow(Display.getWidth()/2+5-TTF2.statsName.getWidth(Mideas.getFps()), Display.getHeight()-180, Mideas.getFps(), Color.yellow, Color.black, 1, 1, 1);
		TTF2.font5.drawString(Display.getWidth()/2-648, Display.getHeight()-49, "1", Color.white);
		if(Mideas.mouseX() >= Display.getWidth()/2-678 && Mideas.mouseX() <= Display.getWidth()/2-678+47 && Mideas.mouseY() >= Display.getHeight()-49 && Mideas.mouseY() <= Display.getHeight()-2) {
			Draw.drawQuad(Sprites.spell_hover, Display.getWidth()/2-678, Display.getHeight()-49);	
			Draw.drawQuad(Sprites.tooltip, Display.getWidth()/2-401, Display.getHeight()-280);
			TTF2.spellName.drawStringShadow(Display.getWidth()/2-393, Display.getHeight()-270, "Attack", Color.white, Color.black, 1, 1, 1);
			TTF2.font4.drawStringShadow(Display.getWidth()/2-393, Display.getHeight()-248, "No mana cost", Color.white, Color.black, 1, 1, 1);
			hoverAttack = true;
		}
		int numberFreeSlotBag = 0;
		if(bagChange) {
			numberFreeSlotBag = checkNumberFreeSlotBag();
			bagChange = false;
		}
		TTF2.statsName.drawStringShadow(Display.getWidth()/2+650-TTF2.statsName.getWidth("("+Integer.toString(numberFreeSlotBag)+")")/2, Display.getHeight()-23, "("+Integer.toString(checkNumberFreeSlotBag())+")", Color.white, Color.black, 1, 1, 1);
		float x = -678+56.3f;
		int spellCount = 0;
		int yShift = 0;
		while(spellCount < hoverSpellBar.length) {
		//for(Shortcut spell : Mideas.joueur1().getSpells()) {
			if(Mideas.joueur1().getSpells(spellCount) != null) {
				if(spellCount >= 11) {
		        	if(spellCount%2 == 0) {
		        		Draw.drawQuad(Sprites.spellbar_case, Display.getWidth()/2+x, Display.getHeight()-49+yShift);
		        	}
		        	else {
		        		Draw.drawQuad(Sprites.spellbar_case2, Display.getWidth()/2+x, Display.getHeight()-49+yShift);
		        	}
				}
				if(Mideas.joueur1().getSpells(spellCount).getShortcutType() == ShortcutType.SPELL) {
					SpellShortcut spell = (SpellShortcut)Mideas.joueur1().getSpells(spellCount);
					if(!(DragSpellManager.getDraggedSpell() == spell)) {
						Draw.drawQuad(spell.getSprite(), Display.getWidth()/2+x, Display.getHeight()-49+yShift);
						Draw.drawQuad(Sprites.spell_border, Display.getWidth()/2-1+x, Display.getHeight()-51+yShift);
					}
					if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+47+x && Mideas.mouseY() >= Display.getHeight()-49+yShift  && Mideas.mouseY() <= Display.getHeight()-2) {
						Draw.drawQuad(Sprites.spell_hover, Display.getWidth()/2+x, Display.getHeight()-49+yShift);
						Draw.drawQuad(Sprites.tooltip, Display.getWidth()/2-74+x, Display.getHeight()-220);
						TTF2.font4.drawString(Display.getWidth()/2-66+x, Display.getHeight()-210, ((SpellShortcut)spell).getSpell().getName(), Color.white);
						TTF2.font4.drawString(Display.getWidth()/2-66+x, Display.getHeight()-188, ((SpellShortcut)spell).getSpell().getManaCost()+" Mana", Color.white);
						if(((SpellShortcut)spell).getSpell().getType() == SpellType.HEAL) {
							TTF2.font4.drawStringShadow(Display.getWidth()/2-66+x, Display.getHeight()-158, "Heals you for "+((SpellShortcut)spell).getSpell().getHeal(), Color.yellow, Color.black, 1, 1, 1);
						}
						else {
							TTF2.font4.drawStringShadow(Display.getWidth()/2-66+x, Display.getHeight()-158, "Deals "+((SpellShortcut)spell).getSpell().getBaseDamage()+" damage to the enemy", Color.yellow, Color.black, 1, 1, 1);
						}
						hoveredSpell = spell;
					}
					if(SpellManager.getCd(((SpellShortcut)spell).getSpell().getSpellId()) > 0) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2+30+x, Display.getHeight()-27+yShift, String.valueOf(SpellManager.getCd(((SpellShortcut)spell).getSpell().getSpellId())), Color.white, Color.black, 1, 1, 1);
					}
				}
				else if(Mideas.joueur1().getSpells(spellCount).getShortcutType() == ShortcutType.STUFF) {
					Draw.drawQuad(Mideas.joueur1().getSpells(spellCount).getSprite(), Display.getWidth()/2+x, Display.getHeight()-49+yShift);
					Draw.drawQuad(Sprites.spell_border, Display.getWidth()/2-1+x, Display.getHeight()-51+yShift);
					StuffShortcut spell = (StuffShortcut)Mideas.joueur1().getSpells(spellCount);
					hoveredSpell = spell;
				}
				else if(Mideas.joueur1().getSpells(spellCount).getShortcutType() == ShortcutType.POTION) {
					Draw.drawQuad(Mideas.joueur1().getSpells(spellCount).getSprite(), Display.getWidth()/2+x, Display.getHeight()-49+yShift);
					TTF2.font5.drawStringShadow(Display.getWidth()/2+x+36-TTF2.font5.getWidth(String.valueOf(Mideas.joueur1().getNumberItem(((PotionShortcut)Mideas.joueur1().getSpells(spellCount)).getPotion())))/2, Display.getHeight()-30+yShift, String.valueOf(Mideas.joueur1().getNumberItem(((PotionShortcut)Mideas.joueur1().getSpells(spellCount)).getPotion())), Color.white, Color.black, 1, 1, 1);
					Draw.drawQuad(Sprites.spell_border, Display.getWidth()/2-1+x, Display.getHeight()-51+yShift);
					PotionShortcut spell = (PotionShortcut)Mideas.joueur1().getSpells(spellCount);
					hoveredSpell = spell;
				}
				if(DragSpellManager.getDraggedSpell() != Mideas.joueur1().getSpells(spellCount)) {
					if(spellCount+2 <= 9) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2+30+x, Display.getHeight()-49, String.valueOf(spellCount+2), Color.white, Color.black, 1, 1, 1);
					}
					else if(spellCount+2 == 10) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2+30+x, Display.getHeight()-49, "0", Color.white, Color.black, 1, 1, 1);
					}
					else if(spellCount+2 == 11) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2+30+x, Display.getHeight()-49, ")", Color.white, Color.black, 1, 1, 1);
					}
					else if(spellCount+2 == 12) {
						TTF2.font5.drawStringShadow(Display.getWidth()/2+30+x, Display.getHeight()-49, "=", Color.white, Color.black, 1, 1, 1);
					}
				}
			}
			if(DragSpellManager.getDraggedSpell() != null || DragManager.getDraggedItem() != null) {
				if(spellCount >= 11) {
		        	if(spellCount%2 == 0) {
		        		Draw.drawQuad(Sprites.spellbar_case, Display.getWidth()/2+x, Display.getHeight()-49+yShift);
		        	}
		        	else {
		        		Draw.drawQuad(Sprites.spellbar_case2, Display.getWidth()/2+x, Display.getHeight()-49+yShift);
		        	}
				}
			}
			if(Mideas.mouseX() >= Display.getWidth()/2-72+x && Mideas.mouseY() >= Display.getHeight()-61 && Mideas.mouseX() <= Display.getWidth()/2-16+x && Mideas.mouseY() <= Display.getHeight()-5) {
				hoverSpellBar[spellCount] = true;
			}
			x+= 56.3;
			spellCount++;
			if(spellCount == 11) {
				x = -678;
				yShift = -80;
			}
		}
		return false;
	}
	
	public static boolean mouseEvent() throws SQLException, FileNotFoundException {
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(hoverAttack) {
					Mideas.joueur1().tick();
					Mideas.setCurrentPlayer(false);
				}
				else if(hoveredSpell != null) {
					if(hoveredSpell.getShortcutType() == ShortcutType.SPELL) {
						if(SpellManager.getCd(((SpellShortcut)hoveredSpell).getSpell().getSpellId()) <= 0 && hoveredSpell.use(hoveredSpell)) {
							checkClickCd();
						}
						else if(SpellManager.getCd(((SpellShortcut)hoveredSpell).getSpell().getSpellId()) <= 0){
							hoveredSpell.use(hoveredSpell);
						}
						else {
							Mideas.joueur1().tick();
						}
						Mideas.setCurrentPlayer(false);
					}
					else if(hoveredSpell.getShortcutType() == ShortcutType.STUFF) {
					}
					else if(hoveredSpell.getShortcutType() == ShortcutType.POTION) {
						((PotionShortcut)hoveredSpell).use(hoveredSpell);
					}
				}
			}
		}
		return false;
	}
	
	public static boolean keyboardEvent() throws SQLException, FileNotFoundException {
		if(Keyboard.getEventKey() == Keyboard.KEY_1) {
			Mideas.joueur1().tick();
			Mideas.setCurrentPlayer(false);
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_2) {
			keyboardAttack(Mideas.joueur1().getSpells(0));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_3) {
			keyboardAttack(Mideas.joueur1().getSpells(1));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_4) {
			keyboardAttack(Mideas.joueur1().getSpells(2));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_5) {
			keyboardAttack(Mideas.joueur1().getSpells(3));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_6) {
			keyboardAttack(Mideas.joueur1().getSpells(4));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_7) {
			keyboardAttack(Mideas.joueur1().getSpells(5));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_8) {
			keyboardAttack(Mideas.joueur1().getSpells(6));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_9) {
			keyboardAttack(Mideas.joueur1().getSpells(7));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_0) {
			keyboardAttack(Mideas.joueur1().getSpells(8));
			return true;
		}
		else if(Keyboard.getEventKey() == 26) {
			keyboardAttack(Mideas.joueur1().getSpells(9));
			return true;
		}
		else if(Keyboard.getEventKey() == Keyboard.KEY_EQUALS) {
			keyboardAttack(Mideas.joueur1().getSpells(10));
			return true;
		}
		return false;
	}
	
	public static void keyboardAttack(Shortcut spell) throws SQLException, FileNotFoundException {
		if(spell != null && spell.getShortcutType() == ShortcutType.SPELL) {
			if((SpellManager.getCd(((SpellShortcut)spell).getSpell().getSpellId()) <= 0 && spell.use(spell))) {
				checkKeyboardCd(spell);
			}
			else if(SpellManager.getCd(((SpellShortcut)spell).getSpell().getSpellId()) <= 0){
				spell.use(spell);
			}
			else {
				Mideas.joueur1().tick();
			}
			Mideas.setCurrentPlayer(false);
		}
		else if(spell != null && spell.getShortcutType() == ShortcutType.POTION) {
			spell.use(spell);
		}
		else if(spell != null && spell.getShortcutType() == ShortcutType.STUFF) {
			spell.use(spell);
		}
	}
	
	private static void checkClickCd() {
		for(Shortcut spell : Mideas.joueur1().getSpells()) {
			if(spell != null && spell.getShortcutType() == ShortcutType.SPELL) {
				if(spell != null && ((SpellShortcut)hoveredSpell).getSpell().getSpellId() == ((SpellShortcut)spell).getSpell().getSpellId()) {
					spell.setCd(((SpellShortcut)spell).getSpell().getSpellId(), ((SpellShortcut)spell).getSpell().getSpellBaseCd());
				}
			}
		}
	}

	public static boolean doHealingPotion(Potion item) throws FileNotFoundException, SQLException {
		if(item != null && item.getItemType() == ItemType.POTION) {
			if(Mideas.joueur1().getStamina()+item.getPotionHeal() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous êtes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
				Mideas.joueur1().setNumberItem(item, Mideas.bag().getNumberBagItem(item)-1);
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+item.getPotionHeal());
				LogChat.setStatusText3("Vous vous êtes rendu "+item.getPotionHeal()+" hp");
				Mideas.joueur1().setNumberItem(item, Mideas.bag().getNumberBagItem(item)-1);
			}
			else {
				LogChat.setStatusText3("Vos HP étaient déjà au maximum");
			}
			CharacterStuff.setBagItems();
			if(Mideas.joueur1().getNumberItem(item) <= 0) {
				CharacterStuff.setBagItems();
				return true;
			}
		}
		return false;
	}
	
	private static void checkKeyboardCd(Shortcut spell) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(spell.getShortcutType() == ShortcutType.SPELL) {
				if(Mideas.joueur1().getSpells(i) != null && Mideas.joueur1().getSpells(i).getShortcutType() == ShortcutType.SPELL && ((SpellShortcut)Mideas.joueur1().getSpells(i)).getSpell().getSpellId() == ((SpellShortcut)spell).getSpell().getSpellId()) {
					spell.setCd(((SpellShortcut)spell).getSpell().getSpellId(), ((SpellShortcut)spell).getSpell().getSpellBaseCd());
				}
			}
			i++;
		}
	}
	
	private static int checkNumberFreeSlotBag() {
		int i = 0;
		int number = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				number++;
			}
			i++;
		}
		return number;
	}
	
	public static void setBagChange(boolean we) {
		bagChange = we;
	}
	
	public static boolean getHoverSpellBar(int i) {
		return hoverSpellBar[i];
	}
	
	public static boolean[] getHoverSpellBar() {
		return hoverSpellBar;
	}
}
