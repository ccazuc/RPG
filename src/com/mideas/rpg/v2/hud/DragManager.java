package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.stuff.Stuff;
import com.mideas.rpg.v2.game.stuff.item.Item;
import com.mideas.rpg.v2.game.stuff.item.potion.healingPotion.HealingPotion;
import com.mideas.rpg.v2.utils.Draw;

public class DragManager {
	
	private static boolean deleteItem;
	private static boolean hoverDelete;
	private static boolean hoverSave;
	private static Stuff draggedItem;
	
	public static void draw() {
		hoverDelete = false;
		hoverSave = false;
		if(draggedItem != null) {
			Draw.drawQuad(CharacterStuff.getSprite(draggedItem.getId()), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.stuff_border, Mideas.mouseX()-5, Mideas.mouseY()-5);
			if(draggedItem instanceof HealingPotion) {
				TTF2.itemNumber.drawStringShadow(Mideas.mouseX()+35-TTF2.itemNumber.getWidth(String.valueOf(Mideas.joueur1().getNumberPotion(draggedItem))), Mideas.mouseY()+20, String.valueOf(Mideas.joueur1().getNumberPotion(draggedItem)), Color.white, Color.black, 1, 1, 1);
			}
		}
		if(deleteItem && draggedItem != null) {
			Draw.drawQuad(Sprites.alert, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-105, Display.getHeight()/2-80);
			if(Mideas.mouseX() >= Display.getWidth()/2-130 && Mideas.mouseX() <= Display.getWidth()/2-6 && Mideas.mouseY() <= Display.getHeight()/2-18 && Mideas.mouseY() >= Display.getHeight()/2-37) {
				Draw.drawQuad(Sprites.button_hover, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
				hoverDelete = true;
			}
			else {
				Draw.drawQuad(Sprites.button, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2-70, Display.getHeight()/2-43);
			}
			if(Mideas.mouseX() >= Display.getWidth()/2+7 && Mideas.mouseX() <= Display.getWidth()/2+134 && Mideas.mouseY() <= Display.getHeight()/2-15 && Mideas.mouseY() >= Display.getHeight()/2-38) {
				Draw.drawQuad(Sprites.button_hover2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
				hoverSave = true;
			}
			else {
				Draw.drawQuad(Sprites.button2, Display.getWidth()/2-Sprites.button_hover.getImageWidth()/2+70, Display.getHeight()/2-43);
			}
			if(Interface.getEscapeFrameStatus()) {
				draggedItem = null;
				deleteItem = false;
			}
			TTF2.font4.drawStringShadow(Display.getWidth()/2-115, Display.getHeight()/2-65, "Voulez vous supprimer cet item ?", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Oui")/2-69, Display.getHeight()/2-41, "Oui", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Non")/2+70, Display.getHeight()/2-40, "Non", Color.white, Color.black, 1, 1, 1);
		}
	}
	
	public static boolean mouseEvent() throws FileNotFoundException {
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				/*if(CharacterFrame.getHoverCharacterFrame(0)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(0);
					if(tempItem != null && tempItem instanceof Head) {
						Mideas.joueur1().setStuff(0, (Head)tempItem);
						setNullCharacter(tempItem);
						calcStats(Mideas.joueur1().getStuff(0));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(1)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(1);
					if(tempItem != null && tempItem instanceof Necklace) {
						Mideas.joueur1().setStuff(1, (Necklace)tempItem);
						calcStats(Mideas.joueur1().getStuff(0));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(2)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(2);
					if(tempItem != null && tempItem instanceof Shoulders) {
						Mideas.joueur1().setStuff(2, (Shoulders)tempItem);
						calcStats(Mideas.joueur1().getStuff(2));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(3)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(3);
					if(tempItem != null && tempItem instanceof Back) {
						Mideas.joueur1().setStuff(3, (Back)tempItem);
						calcStats(Mideas.joueur1().getStuff(3));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(4)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(4);
					if(tempItem != null && tempItem instanceof Chest) {
						Mideas.joueur1().setStuff(4, (Chest)tempItem);
						calcStats(Mideas.joueur1().getStuff(4));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.hoverCharacterFrame6) {
					Stuff tempItem = draggedItem;
					draggedItem = slotEquipped6;
					slotEquipped6 = tempItem;
					return true;
				}
				else if(CharacterFrame.hoverCharacterFrame7) {
					Stuff tempItem = draggedItem;
					draggedItem = slotEquipped7;
					slotEquipped7 = tempItem;
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(7)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(7);
					if(tempItem != null && tempItem instanceof Wrists) {
						Mideas.joueur1().setStuff(7, (Wrists)tempItem);
						calcStats(Mideas.joueur1().getStuff(7));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(8)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(8);
					if(tempItem != null && tempItem instanceof Gloves) {
						Mideas.joueur1().setStuff(8, (Gloves)tempItem);
						calcStats(Mideas.joueur1().getStuff(8));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(9)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(9);
					if(tempItem != null && tempItem instanceof Belt) {
						Mideas.joueur1().setStuff(9, (Belt)tempItem);
						calcStats(Mideas.joueur1().getStuff(9));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(10)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(10);
					if(tempItem != null && tempItem instanceof Leggings) {
						Mideas.joueur1().setStuff(10, (Leggings)tempItem);
						calcStats(Mideas.joueur1().getStuff(10));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(11)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(11);
					if(tempItem != null && tempItem instanceof Boots) {
						Mideas.joueur1().setStuff(11, (Boots)tempItem);
						calcStats(Mideas.joueur1().getStuff(11));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(12)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(12);
					if(tempItem != null && tempItem instanceof Ring) {
						Mideas.joueur1().setStuff(12, (Ring)tempItem);
						calcStats(Mideas.joueur1().getStuff(12));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(13)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(13);
					if(tempItem != null && tempItem instanceof Ring2) {
						Mideas.joueur1().setStuff(13, (Ring2)tempItem);
						calcStats(Mideas.joueur1().getStuff(13));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(14)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(14);
					if(tempItem != null && tempItem instanceof Trinket) {
						Mideas.joueur1().setStuff(14, (Trinket)tempItem);
						calcStats(Mideas.joueur1().getStuff(14));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(15)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(15);
					if(tempItem != null && tempItem instanceof Trinket2) {
						Mideas.joueur1().setStuff(15, (Trinket2)tempItem);
						calcStats(Mideas.joueur1().getStuff(15));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(16)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(16);
					if(tempItem != null && tempItem instanceof MainHand) {
						Mideas.joueur1().setStuff(16, (MainHand)tempItem);
						calcStats(Mideas.joueur1().getStuff(16));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(17)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(17);
					if(tempItem != null && tempItem instanceof OffHand) {
						Mideas.joueur1().setStuff(17, (OffHand)tempItem);
						calcStats(Mideas.joueur1().getStuff(17));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}
				else if(CharacterFrame.getHoverCharacterFrame(18)) {
					Stuff tempItem = draggedItem;
					draggedItem = Mideas.joueur1().getStuff(18);
					if(tempItem != null && tempItem instanceof Ranged) {
						Mideas.joueur1().setStuff(18, (Ranged)tempItem);
						calcStats(Mideas.joueur1().getStuff(18));
						CharacterStuff.setEquippedItems();
					}
					return true;
				}*/
				int i = 0;
				while(i < CharacterFrame.getHoverCharacterFrame().length) {
					clickInventoryItem(i);
					i++;
				}
				i = 0;
				while(i < Mideas.bag().getBag().length) {
					clickBagItem(i);
					i++;
				}
			}
			else {
				/*if(CharacterFrame.getHoverCharacterFrame(0) && draggedItem != null) {
					if(draggedItem instanceof Head && !draggedItem.equals(Mideas.joueur1().getStuff(0)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(0);
						Mideas.joueur1().setStuff(0, (Head)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(0));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(1) && draggedItem != null) {
					if(draggedItem instanceof Necklace && !draggedItem.equals(Mideas.joueur1().getStuff(1))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(1);
						Mideas.joueur1().setStuff(1, (Necklace)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(1));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(2) && draggedItem != null) {
					if(draggedItem instanceof Shoulders && !draggedItem.equals(Mideas.joueur1().getStuff(2)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(2);
						Mideas.joueur1().setStuff(2, (Shoulders)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(2));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(3) && draggedItem != null) {
					if(draggedItem instanceof Back && !draggedItem.equals(Mideas.joueur1().getStuff(3))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(3);
						Mideas.joueur1().setStuff(3, (Back)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(3));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(4) && draggedItem != null) {
					if(draggedItem instanceof Chest && !draggedItem.equals(Mideas.joueur1().getStuff(4)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(4);
						Mideas.joueur1().setStuff(4, (Chest)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(4));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.hoverCharacterFrame6 && draggedItem != null) {
					Stuff tempItem = Mideas.joueur1().;
					slotEquipped6 = draggedItem;
					draggedItem = tempItem;
				}
				else if(CharacterFrame.hoverCharacterFrame7 && draggedItem != null) {
					Stuff tempItem = slotEquipped7;
					slotEquipped7 = draggedItem;
					draggedItem = tempItem;
				}
				else if(CharacterFrame.getHoverCharacterFrame(7) && draggedItem != null) {
					if(draggedItem instanceof Wrists && !draggedItem.equals(Mideas.joueur1().getStuff(7)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(7);
						Mideas.joueur1().setStuff(7, (Wrists)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(7));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(8) && draggedItem != null) {
					if(draggedItem instanceof Gloves && !draggedItem.equals(Mideas.joueur1().getStuff(8)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(8);
						Mideas.joueur1().setStuff(8, (Gloves)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(8));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(9) && draggedItem != null) {
					if(draggedItem instanceof Belt && !draggedItem.equals(Mideas.joueur1().getStuff(9)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(9);
						Mideas.joueur1().setStuff(9, (Belt)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(9));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(10) && draggedItem != null) {
					if(draggedItem instanceof Leggings && !draggedItem.equals(Mideas.joueur1().getStuff(10)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(10);
						Mideas.joueur1().setStuff(10, (Leggings)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(10));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(11) && draggedItem != null) {
					if(draggedItem instanceof Boots && !draggedItem.equals(Mideas.joueur1().getStuff(11)) && draggedItem.getClasse().equals(Mideas.joueur1().getClasse())) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(11);
						Mideas.joueur1().setStuff(11, (Boots)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(11));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(12) && draggedItem != null) {
					if(draggedItem instanceof Ring && !draggedItem.equals(Mideas.joueur1().getStuff(12))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(12);
						Mideas.joueur1().setStuff(12, (Ring)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(12));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(13) && draggedItem != null) {
					if(draggedItem instanceof Ring2 && !draggedItem.equals(Mideas.joueur1().getStuff(13))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(13);
						Mideas.joueur1().setStuff(13, (Ring2)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(13));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(14) && draggedItem != null) {
					if(draggedItem instanceof Trinket && !draggedItem.equals(Mideas.joueur1().getStuff(14))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(14);
						Mideas.joueur1().setStuff(14, (Trinket)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(14));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(15) && draggedItem != null) {
					if(draggedItem instanceof Trinket2 && !draggedItem.equals(Mideas.joueur1().getStuff(15))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(15);
						Mideas.joueur1().setStuff(15, (Trinket2)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(15));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(16) && draggedItem != null) {
					if(draggedItem instanceof MainHand && !draggedItem.equals(Mideas.joueur1().getStuff(16)) && (draggedItem.getClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getSecondClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getThirdClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getFourthClasse().equals(Mideas.joueur1().getClasse()))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(16);
						Mideas.joueur1().setStuff(16, (MainHand)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(16));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(17) && draggedItem != null) {
					if(draggedItem instanceof OffHand && !draggedItem.equals(Mideas.joueur1().getStuff(17)) && (draggedItem.getClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getSecondClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getThirdClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getFourthClasse().equals(Mideas.joueur1().getClasse()))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(17);
						Mideas.joueur1().setStuff(17, (OffHand)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(17));
					}
					else {
						draggedItem = null;
					}
				}
				else if(CharacterFrame.getHoverCharacterFrame(18) && draggedItem != null) {
					if(draggedItem instanceof Ranged && !draggedItem.equals(Mideas.joueur1().getStuff(18)) && (draggedItem.getClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getSecondClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getThirdClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getFourthClasse().equals(Mideas.joueur1().getClasse()))) {
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(18);
						Mideas.joueur1().setStuff(18, (Ranged)draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(18));
					}
					else {
						draggedItem = null;
					}
				}*/
				int i = 0;
				while(i < CharacterFrame.getHoverCharacterFrame().length) {
					dropInventoryItem(i);
					i++;
				}
				i = 0;
				while(i < Mideas.bag().getBag().length) {
					dropBagItem(i);
					i++;
				}
				if(draggedItem != null) {
					deleteItem = true;
				}
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(hoverDelete && deleteItem) {
					if(draggedItem instanceof Item) {
						Mideas.joueur1().setNumberPotion(draggedItem, 0);
					}
					deleteItem(draggedItem);
					deleteItem = false;
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
				}
				if(hoverSave && deleteItem) {
					draggedItem = null;
					deleteItem = false;
				}
				deleteItem = false;
			}
		}
		if(Mouse.getEventButton() == 1) {
			if(Mouse.getEventButtonState()) {
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(doHealingPotion(Mideas.bag().getBag(i), ContainerFrame.getContainerFrameSlotHover(i), Mideas.joueur1().getNumberPotion(Mideas.bag().getBag(i)))) {
						Mideas.bag().setBag(i, null);
						return true;
					}
					i++;
				}
			}
		}
		return false;
	}
	
	private static boolean setNullCharacter(Stuff draggedItem) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(draggedItem == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				return true;
			}
			i++;
 		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(draggedItem == Mideas.joueur1().getStuff(i)) {
				Mideas.joueur1().setStuff(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean deleteItem(Stuff draggedItem) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(draggedItem == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(draggedItem == Mideas.joueur1().getStuff(i)) {
				Mideas.joueur1().setStuff(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean setNullContainer(Stuff draggedItem, Stuff bag) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(draggedItem == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, bag);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(draggedItem == Mideas.joueur1().getStuff(i)) {
				calcStatsLess(Mideas.joueur1().getStuff(i));
				Mideas.joueur1().setStuff(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static void calcStats(Stuff stuff) throws FileNotFoundException {
		if(stuff != null) {
			CharacterStuff.setEquippedItems();
			Mideas.joueur1().setStuffArmor(stuff.getArmor());
			Mideas.joueur1().setStuffStamina(stuff.getStamina());
			Mideas.joueur1().setStuffStrength(stuff.getStrength());
			Mideas.joueur1().setStuffMana(stuff.getMana());
			Mideas.joueur1().setStuffCritical(stuff.getCritical());
		}
	}
	
	private static void calcStatsLess(Stuff stuff) {
		if(stuff != null) {
			Mideas.joueur1().setStuffArmor(-stuff.getArmor());
			Mideas.joueur1().setStuffStamina(-stuff.getStamina());
			Mideas.joueur1().setStuffStrength(-stuff.getStrength());
			Mideas.joueur1().setStuffMana(-stuff.getMana());
			Mideas.joueur1().setStuffCritical(-stuff.getCritical());
		}
	}
	
	private static boolean doHealingPotion(Stuff stuff, boolean hover, int number) throws FileNotFoundException {
		if(hover && stuff != null && stuff instanceof HealingPotion) {
			if(Mideas.joueur1().getStamina()+stuff.getStamina() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous êtes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+stuff.getStamina());
				LogChat.setStatusText3("Vous vous êtes rendu "+stuff.getStamina()+" hp");
			}
			else {
				LogChat.setStatusText3("Vos HP étaient déjà au maximum");
				number++;
			}
			number--;
			Mideas.joueur1().setNumberPotion(stuff, number);
			CharacterStuff.setBagItems();
			if(number <= 0) {
				return true;
			}
		}
		return false;
	}
	
	private static void dropBagItem(int i) throws FileNotFoundException {
		if(ContainerFrame.getContainerFrameSlotHover(i) && draggedItem != null) {
			if(!draggedItem.equals(Mideas.bag().getBag(i))) {
				Stuff tempItem = Mideas.bag().getBag(i);
				if(setNullContainer(draggedItem, Mideas.bag().getBag(i))) {
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
				}
				else {
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = tempItem;
				}
				CharacterStuff.setEquippedItems();
				CharacterStuff.setBagItems();
			}
			else {
				draggedItem = null;
			}
		}
	}
	
	private static void clickBagItem(int i) throws FileNotFoundException {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			Stuff tempItem = draggedItem;
			draggedItem = Mideas.bag().getBag(i);
			if(tempItem != null) {
				setNullContainer(tempItem, Mideas.bag().getBag(i));
				Mideas.bag().setBag(i, tempItem);;
				CharacterStuff.setBagItems();
			}
		}
	}
	
	private static void clickInventoryItem(int i) throws FileNotFoundException {
		if(CharacterFrame.getHoverCharacterFrame(i)) {
			Stuff tempItem = draggedItem;
			draggedItem = Mideas.joueur1().getStuff(i);
			if(tempItem != null && tempItem.getSlot() == Stuff.getSlot(i)) {
				Mideas.joueur1().setStuff(i, tempItem);
				setNullCharacter(tempItem);
				calcStats(Mideas.joueur1().getStuff(i));
				CharacterStuff.setEquippedItems();
			}
		}
	}
	
	private static void dropInventoryItem(int i) throws FileNotFoundException {
		if(CharacterFrame.getHoverCharacterFrame(i) && draggedItem != null) {
			if(draggedItem.getSlot() == Stuff.getSlot(i) && !draggedItem.equals(Mideas.joueur1().getStuff(i))/* && (draggedItem.getClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getSecondClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getThirdClasse().equals(Mideas.joueur1().getClasse()))*/) {
				if(draggedItem.getClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getSecondClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getThirdClasse().equals(Mideas.joueur1().getClasse())) {
					setNullCharacter(draggedItem);
					Stuff tempItem = Mideas.joueur1().getStuff(i);
					Mideas.joueur1().setStuff(i, draggedItem);
					draggedItem = tempItem;
					CharacterStuff.setBagItems();
					calcStats(Mideas.joueur1().getStuff(i));
				}
			}
			else {
				draggedItem = null;
			}
		}
	}
	
	public static Stuff getDraggedItem() {
		return draggedItem;
	}
}
