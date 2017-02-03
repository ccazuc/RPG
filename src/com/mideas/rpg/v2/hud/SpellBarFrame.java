package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.shortcut.Shortcut;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.utils.ButtonSpellbar;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;
import com.mideas.rpg.v2.utils.Tooltip;

public class SpellBarFrame {

	private static boolean isCastingSpell;
	private static int hoveredSlot = -1;
	private static Tooltip tooltip = new Tooltip(0, 0, 0, 0, 0.6f);
	private final static ButtonSpellbar[] buttonList = new ButtonSpellbar[36];
	
	private static String numberFreeSlotBag = "";
	private static boolean init;
	private static boolean shouldUpdateSize;
	
	public static boolean draw() {
		if(!init) {
			createSpellbarButton();
			init = true;
		}
		updateSize();
		if(Mideas.joueur1().getLevel() >= 70) {
			Draw.drawQuad(Sprites.exp_bar, Display.getWidth()/2-Sprites.final_spellbar.getImageWidth()*Mideas.getDisplayXFactor()/2+110*Mideas.getDisplayXFactor(), Display.getHeight()-Sprites.final_spellbar.getImageHeight()*Mideas.getDisplayYFactor()+24*Mideas.getDisplayXFactor(), 1165*Mideas.getDisplayXFactor(), 8);
		}
		else if(Mideas.joueur1().getLevel() > 1) {
			float e = (Mideas.joueur1().getExp()-(float)Mideas.getExpNeeded(Mideas.joueur1().getLevel()-1))/((float)Mideas.getExpNeeded(Mideas.joueur1().getLevel())-Mideas.getExpNeeded(Mideas.joueur1().getLevel()-1));
			Draw.drawQuad(Sprites.exp_bar, Display.getWidth()/2-Sprites.final_spellbar.getImageWidth()*Mideas.getDisplayXFactor()/2+110*Mideas.getDisplayXFactor(), Display.getHeight()-Sprites.final_spellbar.getImageHeight()*Mideas.getDisplayYFactor()+24*Mideas.getDisplayXFactor(), 1165*e*Mideas.getDisplayXFactor(), 9);
		}
		else {
			float e = ((float)Mideas.joueur1().getExp()/Mideas.getExpNeeded(Mideas.joueur1().getLevel()));
			Draw.drawQuad(Sprites.exp_bar, Display.getWidth()/2-Sprites.final_spellbar.getImageWidth()*Mideas.getDisplayXFactor()/2+110*Mideas.getDisplayXFactor(), Display.getHeight()-Sprites.final_spellbar.getImageHeight()*Mideas.getDisplayYFactor()+24*Mideas.getDisplayXFactor(), 1165*e*Mideas.getDisplayXFactor(), 8);
		}
		//TTF2.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2+5-TTF2.get("FRIZQT", 15).getWidth(Mideas.getFps()), Display.getHeight()-180, Mideas.getFps(), Colors.yellow, Colors.BLACK, 1);
		Draw.drawQuad(Sprites.final_spellbar, Display.getWidth()/2-Sprites.final_spellbar.getImageWidth()/2*Mideas.getDisplayXFactor(), Display.getHeight()-Sprites.final_spellbar.getImageHeight()*Mideas.getDisplayYFactor(), Sprites.final_spellbar.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.final_spellbar.getImageHeight()*Mideas.getDisplayYFactor());
		FontManager.get("FRIZQT", 15).drawStringShadow(Display.getWidth()/2+557*Mideas.getDisplayXFactor()-FontManager.get("FRIZQT", 15).getWidth(numberFreeSlotBag)/2, Display.getHeight()-22, numberFreeSlotBag, Color.WHITE, Color.BLACK, 1, 1, 1);
		int i = 0;
		while(i < buttonList.length) {
			buttonList[i].draw();
			i++;
		}
		i = 0;
		float xBag = 489*Mideas.getDisplayXFactor();
		float xBagShift = -48.2f*Mideas.getDisplayXFactor();
		float yBag = -40*Mideas.getDisplayYFactor();
		while(i < 4) {
			if(Mideas.joueur1().bag().getEquippedBag(i) != null) {
				Draw.drawQuad(IconsManager.getSprite37(Mideas.joueur1().bag().getSpriteId(i)), Display.getWidth()/2+xBag+xBagShift*i, Display.getHeight()+yBag);
				if(DragBagManager.getHoverBag(i)) {
					Draw.drawQuad(Sprites.bag_hover, Display.getWidth()/2+xBag+xBagShift*i, Display.getHeight()+yBag);
					if(tooltip.getHashcode() != Mideas.joueur1().bag().getEquippedBag(i).hashCode()) {
						tooltip.update(Display.getWidth()/2+xBag+i*xBagShift-Mideas.joueur1().bag().getEquippedBag(i).getMaximumTooltipSize()-25*Mideas.getDisplayXFactor(),  Display.getHeight()-100*Mideas.getDisplayYFactor(), Mideas.joueur1().bag().getEquippedBag(i).getMaximumTooltipSize()+25*Mideas.getDisplayXFactor(), 60*Mideas.getDisplayYFactor(), Mideas.joueur1().bag().getEquippedBag(i).hashCode());
					}
					tooltip.draw();
					FontManager.get("FRIZQT", 14).drawStringShadow(Display.getWidth()/2+xBag+xBagShift*i-Mideas.joueur1().bag().getEquippedBag(i).getMaximumTooltipSize()-15*Mideas.getDisplayXFactor(), Display.getHeight()-90*Mideas.getDisplayYFactor(), Mideas.joueur1().bag().getEquippedBag(i).getStuffName(), Color.WHITE, Color.BLACK, 1, 1, 1);
					FontManager.get("FRIZQT", 14).drawStringShadow(Display.getWidth()/2+xBag+xBagShift*i-Mideas.joueur1().bag().getEquippedBag(i).getMaximumTooltipSize()-15*Mideas.getDisplayXFactor(), Display.getHeight()-70*Mideas.getDisplayYFactor(), Mideas.joueur1().bag().getEquippedBag(i).getSlotTooltipString(), Color.WHITE, Color.BLACK, 1, 1, 1);
				}
				if(DragBagManager.getClickBag(i)) {
					Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()/2+xBag+xBagShift*i, Display.getHeight()+yBag);
				}
				if(ContainerFrame.getBagOpen(i+1)) {
					Draw.drawQuadBlend(Sprites.bag_open_border, Display.getWidth()/2+xBag+xBagShift*i, Display.getHeight()+yBag, 40*Mideas.getDisplayXFactor(), 37*Mideas.getDisplayYFactor());
					i++;
					continue;
				}
			}
			i++;
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+539.2f*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+(539.2+48.2f)*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()+yBag && Mideas.mouseY() <= Display.getHeight()-3*Mideas.getDisplayYFactor()) {
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()/2+537*Mideas.getDisplayXFactor(), Display.getHeight()+yBag);
		}
		/*if(DragBagManager.getClickBag(0)) {
			Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()/2+539.2f, Display.getHeight()-41);
		}*/
		if(ContainerFrame.getBagOpen(0)) {
			Draw.drawQuadBlend(Sprites.bag_open_border, Display.getWidth()/2+536.2f*Mideas.getDisplayXFactor(), Display.getHeight()+yBag, 40*Mideas.getDisplayXFactor(), 37*Mideas.getDisplayYFactor());
		}
		return false;
	}
	
	public static boolean mouseEvent() {
		int i = 0;
		while(i < buttonList.length) {
			if(buttonList[i].mouseEvent()) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean keyboardEvent() {
		int i = 0;
		if(!init) {
			createSpellbarButton();
			init = true;
		}
		while(i < buttonList.length) {
			buttonList[i].keyEvent();
			i++;
		}
		return false;
	}
	
	public static void setIsCastingSpell(boolean we) {
		isCastingSpell = we;
	}
	
	public static boolean getIsCastingSpell() {
		return isCastingSpell;
	}
	

	public static void createSpellbarButton() {
		float x = Display.getWidth()/2+(-Sprites.final_spellbar.getImageWidth()/2+115)*Mideas.getDisplayXFactor();
		float y = Display.getHeight()-40f*Mideas.getDisplayYFactor();
		float yShift = 48*Mideas.getDisplayXFactor();
		int i = 0;
		int j = 0;
		while(i < 36) {
			if(i < 12) {
				buttonList[i] = new ButtonSpellbar(x+j*yShift, y, i+1, true);
			}
			else {
				buttonList[i] = new ButtonSpellbar(x+j*yShift, y, -1, false);
			}
			i++;
			j++;
			if(i == 12) {
				j = 0;
				y-= 61*Mideas.getDisplayYFactor();
			}
		}
		init = true;
	}
	
	public static void updateSize() {
		if(!shouldUpdateSize) {
			return;
		}
		if(!init) {
			createSpellbarButton();
			init = true;
		}
		float x = Display.getWidth()/2+(-Sprites.final_spellbar.getImageWidth()/2+115)*Mideas.getDisplayXFactor();
		float y = Display.getHeight()-40f*Mideas.getDisplayYFactor();
		float yShift = 48*Mideas.getDisplayXFactor();
		int i = 0;
		int j = 0;
		while(i < 36) {
			buttonList[i].update(x+j*yShift, y);
			i++;
			j++;
			if(i == 12) {
				j = 0;
				y-= 61*Mideas.getDisplayYFactor();
			}
		}
		shouldUpdateSize = false;
	}
	
	public static void shouldUpdate() {
		shouldUpdateSize = true;
	}
	
	public static ButtonSpellbar[] getButtonList() {
		return buttonList;
	}
	
	public static ButtonSpellbar getButton(int slot) {
		return buttonList[slot];
	}
	
	public static void setShortcut(int slot, Shortcut shortcut) {
		buttonList[slot].setShortcut(shortcut);
	}
	
	public static boolean doHealingPotion(Potion item) {
		if(item != null && item.getItemType() == ItemType.POTION) {
			if(Mideas.joueur1().getStamina()+item.getPotionHeal() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous �tes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
				item.setAmount(item.getAmount()-1);
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+item.getPotionHeal());
				LogChat.setStatusText3("Vous vous �tes rendu "+item.getPotionHeal()+" hp");
				item.setAmount(item.getAmount()-1);
			}
			else {
				LogChat.setStatusText3("Vos HP �taient d�j� au maximum");
			}
			CharacterStuff.setBagItems();
			if(item.getAmount() <= 0) {
				CharacterStuff.setBagItems();
				return true;
			}
		}
		return false;
	}
	
	public static void loadEquippedItems() {
		int i = 0;
		while(i < buttonList.length) {
			if(buttonList[i] == null) {
				i++;
				continue;
			}
			if(buttonList[i].getShortcut() == null) {
				i++;
				continue;
			}
			if(buttonList[i].getShortcut().getShortcutType() == ShortcutType.STUFF || buttonList[i].getShortcut().getShortcutType() == ShortcutType.POTION) {
				buttonList[i].setItemIsEquipped(isEquippedItem(buttonList[i].getShortcut().getItem().getId()));
				buttonList[i].setIsInBag(isInBag(buttonList[i].getShortcut().getItem().getId()));
				if(buttonList[i].getShortcut().getShortcutType() == ShortcutType.POTION) {
					buttonList[i].setNumberItem(Mideas.joueur1().bag().getNumberItemInBags(buttonList[i].getShortcut().getId()));
				}
			}
			i++;
		}
	}
	
	public static boolean getHoverSpellBar(int i) {
		return hoveredSlot == i;
	}
	
	public static boolean isInBag(int id) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).getId() == id) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean isEquippedItem(int id) {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) != null && Mideas.joueur1().getStuff(i).getId() == id) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static void updateEquippedItem(int id, boolean we) {
		int i = 0;
		if(!init) {
			createSpellbarButton();
			init = true;
		}
		while(i < buttonList.length) {
			if(buttonList[i].getShortcut() != null) {
				if(buttonList[i].getShortcut().getShortcutType() == ShortcutType.STUFF) {
					if(buttonList[i].getShortcut().getId() == id)  {
						buttonList[i].setItemIsEquipped(we);
					}
				}
			}
			i++;
		}
	}
	
	public static void setNumberFreeSlotBag(int number) {
		numberFreeSlotBag = "("+Integer.toString(number)+")";
	}
}
