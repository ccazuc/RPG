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
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.SpellShortcut;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.game.spell.SpellManager;
import com.mideas.rpg.v2.game.spell.SpellType;
import com.mideas.rpg.v2.utils.Draw;

public class SpellBarFrame {
	
	private static Shortcut hoveredSpell;
	private static boolean hoverAttack;
	private static boolean[] hoverSpellBar = new boolean[11];
	
	public static boolean draw() throws FileNotFoundException, SQLException {
		Arrays.fill(hoverSpellBar, false);
		hoverAttack = false;
		hoveredSpell = null;
		if(Mideas.getLevel() == 70) {
			Draw.drawColorQuad(Display.getWidth()/2-140, Display.getHeight()-80, 790, 11,  Color.decode("#680764"));
		}
		else if(Mideas.getLevel() > 1) {
			float e = ((float)Mideas.getCurrentExp()-(float)Mideas.getExpNeeded(Mideas.getLevel()-1))/((float)Mideas.getExpNeeded(Mideas.getLevel())-Mideas.getExpNeeded(Mideas.getLevel()-1));
			Draw.drawColorQuad(Display.getWidth()/2-393, Display.getHeight()-80, 790*e, 12,  Color.decode("#680764"));
		}
		else {
			float e = ((float)Mideas.getCurrentExp()/Mideas.getExpNeeded(Mideas.getLevel()));
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
		while(spellCount < hoverSpellBar.length) {
		//for(Shortcut spell : Mideas.joueur1().getSpells()) {
			if(Mideas.joueur1().getSpells(spellCount) != null && spellCount < 11) {
				if(Mideas.joueur1().getSpells(spellCount) instanceof SpellShortcut) {
					SpellShortcut spell = (SpellShortcut)Mideas.joueur1().getSpells(spellCount);
					if(!(DragSpellManager.getDraggedSpell() == spell)) {
						Draw.drawQuad(spell.getSprite(), Display.getWidth()/2-68+x, Display.getHeight()-58);
						Draw.drawQuad(Sprites.border, Display.getWidth()/2-76+x, Display.getHeight()-66);
					}   
					if(Mideas.mouseX() >= Display.getWidth()/2-72+x && Mideas.mouseY() >= Display.getHeight()-61 && Mideas.mouseX() <= Display.getWidth()/2-16+x && Mideas.mouseY() <= Display.getHeight()-5) {
						Draw.drawQuad(Sprites.hover, Display.getWidth()/2-75+x, Display.getHeight()-63);
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
						if(SpellManager.getCd(((SpellShortcut)spell).getSpell().getSpellId()) > 0) {
							TTF2.font5.drawStringShadow(Display.getWidth()/2-35+x, Display.getHeight()-34, String.valueOf(SpellManager.getCd(((SpellShortcut)spell).getSpell().getSpellId())), Color.white, Color.black, 1, 1, 1);
						}
					}
				}
				else if(Mideas.joueur1().getSpells(spellCount) instanceof StuffShortcut) {
					Draw.drawQuad(Mideas.joueur1().getSpells(spellCount).getSprite(), Display.getWidth()/2-68+x, Display.getHeight()-58);
					Draw.drawQuad(Sprites.border, Display.getWidth()/2-76+x, Display.getHeight()-66);
				}
			}
			if(Mideas.mouseX() >= Display.getWidth()/2-72+x && Mideas.mouseY() >= Display.getHeight()-61 && Mideas.mouseX() <= Display.getWidth()/2-16+x && Mideas.mouseY() <= Display.getHeight()-5) {
				hoverSpellBar[spellCount] = true;
			}
			x+= 67;
			spellCount++;
		}
		return false;
	}
	
	public static boolean mouseEvent() throws SQLException {
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(hoverAttack) {
					Mideas.joueur1().tick();
					Mideas.setCurrentPlayer(false);
				}
				else if(hoveredSpell != null) {
					if(hoveredSpell instanceof SpellShortcut) {
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
					else if(hoveredSpell instanceof StuffShortcut) {
						
					}
				}
			}
		}
		return false;
	}
	
	public static boolean keyboardEvent() throws SQLException {
		if(Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == Keyboard.KEY_1) {
			Mideas.joueur1().tick();
			Mideas.setCurrentPlayer(false);
			return true;
		}
		if(Keyboard.getEventKey() == Keyboard.KEY_2) {
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
	
	public static void keyboardAttack(Shortcut spell) throws SQLException {
		if(spell instanceof SpellShortcut) {
			if(spell != null) {
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
		}
	}
	
	private static void checkClickCd() {
		for(Shortcut spell : Mideas.joueur1().getSpells()) {
			if(spell instanceof SpellShortcut) {
				if(spell != null && ((SpellShortcut)hoveredSpell).getSpell().getSpellId() == ((SpellShortcut)spell).getSpell().getSpellId()) {
					spell.setCd(((SpellShortcut)spell).getSpell().getSpellId(), ((SpellShortcut)spell).getSpell().getSpellBaseCd());
				}
			}
		}
	}

	public static boolean doHealingPotion(Potion item, int number) throws FileNotFoundException, SQLException {
		if(item != null && item.getItemType() == ItemType.POTION) {
			if(Mideas.joueur1().getStamina()+item.getPotionHeal() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous êtes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+item.getPotionHeal());
				LogChat.setStatusText3("Vous vous êtes rendu "+item.getPotionHeal()+" hp");
			}
			else {
				LogChat.setStatusText3("Vos HP étaient déjà au maximum");
				number++;
			}
			number--;
			Mideas.joueur1().setNumberItem(item, Mideas.bag().getNumberBagItem(ContainerFrame.getSlotItem(item))-1);
			CharacterStuff.setBagItems();
			if(number <= 1) {
				return true;
			}
		}
		return false;
	}
	
	private static void checkKeyboardCd(Shortcut spell) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(spell instanceof SpellShortcut) {
				if(Mideas.joueur1().getSpells(i) != null && ((SpellShortcut)Mideas.joueur1().getSpells(i)).getSpell().getSpellId() == ((SpellShortcut)spell).getSpell().getSpellId()) {
					spell.setCd(((SpellShortcut)spell).getSpell().getSpellId(), ((SpellShortcut)spell).getSpell().getSpellBaseCd());
				}
			}
			i++;
		}
	}
	
	public static boolean getHoverSpellBar(int i) {
		return hoverSpellBar[i];
	}
	
	public static boolean[] getHoverSpellBar() {
		return hoverSpellBar;
	}
}
