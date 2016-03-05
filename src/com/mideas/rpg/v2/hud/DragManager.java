package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffType;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.utils.Draw;

public class DragManager {
	
	private static StuffType[] type = new StuffType[]{StuffType.HEAD, StuffType.NECKLACE, StuffType.SHOULDERS, StuffType.BACK, StuffType.CHEST, StuffType.RAN, StuffType.RANDOM, StuffType.WRISTS, StuffType.GLOVES, StuffType.BELT, StuffType.LEGGINGS, StuffType.BOOTS, StuffType.RING, StuffType.RING, StuffType.TRINKET, StuffType.TRINKET, StuffType.MAINHAND, StuffType.OFFHAND, StuffType.RANGED, StuffType.RAN};
	private static boolean deleteItem;
	private static boolean hoverDelete;
	private static boolean hoverSave;
	private static Item draggedItem;
	
	public static void draw() {
		hoverDelete = false;
		hoverSave = false;
		if(draggedItem != null) {
			Draw.drawQuad(IconsManager.getSprite42((draggedItem.getSpriteId())), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.stuff_border, Mideas.mouseX()-5, Mideas.mouseY()-5);
			if(draggedItem.getItemType() == ItemType.ITEM || draggedItem.getItemType() == ItemType.POTION) {
				TTF2.itemNumber.drawStringShadow(Mideas.mouseX()+35-TTF2.itemNumber.getWidth(String.valueOf(Mideas.joueur1().getNumberItem(draggedItem, ContainerFrame.getSlotItem(draggedItem)))), Mideas.mouseY()+20, String.valueOf(Mideas.joueur1().getNumberItem(draggedItem, ContainerFrame.getSlotItem(draggedItem))), Color.white, Color.black, 1, 1, 1);
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
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
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
				if(draggedItem != null && !isCharacterHover() && !isSpellBarHover()) {
					deleteItem = true;
				}
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(hoverDelete && deleteItem) {
					if(draggedItem.getItemType() == ItemType.ITEM || draggedItem.getItemType() == ItemType.POTION) {
						Mideas.joueur1().setNumberItem(draggedItem, -1);
					}
					deleteItem(draggedItem);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
				}
				if(hoverSave && deleteItem) {
					if(!checkBagItems(draggedItem) && !checkCharacterItems(draggedItem)) {
						checkFreeSlotBag(draggedItem);
					}
					draggedItem = null;
				}
				if(deleteItem) {
					draggedItem = null;
				}
				deleteItem = false;
			}
		}
		if(Mouse.getEventButton() == 1) {
			if(Mouse.getEventButtonState()) {
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getItemType() == ItemType.POTION && doHealingPotion((Potion)Mideas.bag().getBag(i), ContainerFrame.getContainerFrameSlotHover(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i), ContainerFrame.getSlotItem(Mideas.bag().getBag(i))))) {
						Mideas.bag().setBag(i, null);
						return true;
					}
					i++;
				}
			}
		}
		return false;
	}
	
	private static boolean setNullCharacter(Item tempItem) {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(tempItem == Mideas.joueur1().getStuff(i)) {
				Mideas.joueur1().setStuff(i, null);
				return true;
			}
			i++;
		}
		return false;
	}

	private static boolean deleteItem(Item draggedItem2) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(Mideas.joueur1().getSpells(i) instanceof StuffShortcut && ((StuffShortcut)Mideas.joueur1().getSpells(i)).getStuff() == draggedItem2) {
				Mideas.joueur1().setSpells(i, null);
			}
			i++;
		}
		i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(draggedItem2 == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(draggedItem2 == Mideas.joueur1().getStuff(i)) {
				Mideas.joueur1().setStuff(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean checkCharacterItems(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) == item) {
				return true;
			}
			i++;
		}
		return false;
		
	}
	
	private static boolean checkBagItems(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == item) {
				return true;
			}
			i++;
		}
		return false;
	}
	private static boolean setNullContainer(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(item == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static void calcStats(Item stuff) throws FileNotFoundException, SQLException {
		if(stuff != null && stuff.getItemType() == ItemType.STUFF) {
			CharacterStuff.setEquippedItems();
			Mideas.joueur1().setStuffArmor(((Stuff)stuff).getArmor());
			Mideas.joueur1().setStuffStamina(((Stuff)stuff).getStamina());
			Mideas.joueur1().setStuffStrength(((Stuff)stuff).getStrength());
			Mideas.joueur1().setStuffMana(((Stuff)stuff).getMana());
			Mideas.joueur1().setStuffCritical(((Stuff)stuff).getCritical());
		}
	}
	
	private static void calcStatsLess(Item stuff) {
		if(stuff != null && stuff.getItemType() == ItemType.STUFF) {
			Mideas.joueur1().setStuffArmor(-((Stuff)stuff).getArmor());
			Mideas.joueur1().setStuffStamina(-((Stuff)stuff).getStamina());
			Mideas.joueur1().setStuffStrength(-((Stuff)stuff).getStrength());
			Mideas.joueur1().setStuffMana(-((Stuff)stuff).getMana());
			Mideas.joueur1().setStuffCritical(-((Stuff)stuff).getCritical());
		}
	}
	
	private static boolean doHealingPotion(Potion item, boolean hover, int number) throws FileNotFoundException, SQLException {
		if(hover && item != null && item.getItemType() == ItemType.POTION) {
			if(Mideas.joueur1().getStamina()+item.getPotionHeal() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous �tes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+item.getPotionHeal());
				LogChat.setStatusText3("Vous vous �tes rendu "+item.getPotionHeal()+" hp");
			}
			else {
				LogChat.setStatusText3("Vos HP �taient d�j� au maximum");
				number++;
			}
			number--;
			Mideas.joueur1().setNumberItem(item, Mideas.bag().getNumberBagItem(item.getId())-1);
			CharacterStuff.setBagItems();
			if(number <= 1) {
				return true;
			}
		}
		return false;
	}
	
	/*private static void dropBagItem(int i) throws FileNotFoundException, SQLException {
		//System.out.println(Mideas.joueur1().getStuff(15));
		//System.out.println(Mideas.joueur1().getStuff(14));
		if(ContainerFrame.getContainerFrameSlotHover(i) && draggedItem != null) {
			if(!draggedItem.equals(Mideas.bag().getBag(i))) {
				Item tempItem = Mideas.bag().getBag(i);
				if(setNullContainer(draggedItem, Mideas.bag().getBag(i))) {
					Mideas.bag().setBag(i, draggedItem);
					
						System.out.println("b");
				}
					else {
						draggedItem = null;
					}
			}
				else {
					Mideas.bag().setBag(i, draggedItem);
					System.out.println("a");
					setNullCharacter(draggedItem);
					draggedItem = tempItem;
				}
				CharacterStuff.setEquippedItems();
				CharacterStuff.setBagItems();
			}
			else {
				draggedItem = null;
			}
		}
	}*/
	
	private static boolean checkFreeSlotBag(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				Mideas.bag().setBag(i, item);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean exchangeBagItems(Item draggedItem, Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(draggedItem == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, item);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static void dropBagItem(int i) throws FileNotFoundException, SQLException {
	//System.out.println(Mideas.joueur1().getStuff(15));
	//System.out.println(Mideas.joueur1().getStuff(14));
	if(ContainerFrame.getContainerFrameSlotHover(i) && draggedItem != null) {
		//if(!draggedItem.equals(Mideas.bag().getBag(i))) {
			if(checkCharacterItems(draggedItem)) {                                          //if draggedItem comes from inventory       
				if(Mideas.bag().getBag(i) == null) { 
					Mideas.bag().setBag(i, draggedItem);
					calcStatsLess(draggedItem);
					setNullCharacter(draggedItem);
					draggedItem = null;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					if(checkFreeSlotBag(tempItem)) {
						calcStatsLess(draggedItem);
						setNullCharacter(draggedItem);
						Mideas.bag().setBag(i, draggedItem);
						draggedItem = null;
					}
					else {
						tempItem = Mideas.bag().getBag(i);
						Mideas.bag().setBag(i, draggedItem);
						draggedItem = tempItem;
					}
				}
			}
			else if(checkBagItems(draggedItem)) {                             //if draggedItem comes from bags
				if(Mideas.bag().getBag(i) == null) {
					setNullContainer(draggedItem);
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
				}
				else {
					exchangeBagItems(draggedItem, Mideas.bag().getBag(i));
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
				}
			}
			/*Item tempItem = Mideas.bag().getBag(i);
			Mideas.bag().setBag(i, draggedItem);
			System.out.println("a");
			setNullCharacter(draggedItem);
			draggedItem = tempItem;*/
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
		//}
	}
		//else {
			//draggedItem = null;
		//}
	}
	
	private static void clickBagItem(int i) throws FileNotFoundException, SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			if(draggedItem == null) {
				if(Mideas.bag().getBag(i) == null) {
				}
				else {
					draggedItem = Mideas.bag().getBag(i);
				}
			}
			else if(checkCharacterItems(draggedItem)) {        
				if(Mideas.bag().getBag(i) == null) {
					calcStatsLess(draggedItem);
					setNullCharacter(draggedItem);
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					Mideas.bag().setBag(i, draggedItem);
					setNullCharacter(draggedItem);
					draggedItem = tempItem;
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(Mideas.bag().getBag(i) == null) {
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					exchangeBagItems(draggedItem, Mideas.bag().getBag(i));
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = tempItem;
				}
			}
			else {
				if(Mideas.bag().getBag(i) == null) {
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = tempItem;
				}
			}
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
			/*	Item tempItem = draggedItem;
			draggedItem = Mideas.bag().getBag(i);
			if(tempItem != null) {
				setNullContainer(tempItem, Mideas.bag().getBag(i));
				Mideas.bag().setBag(i, tempItem);;
				CharacterStuff.setBagItems();
			}*/
		}
	}
	
	private static void clickInventoryItem(int i) throws FileNotFoundException, SQLException {
		if(CharacterFrame.getHoverCharacterFrame(i)) {
			if(draggedItem == null) {
				if(Mideas.joueur1().getStuff(i) == null) {
					
				}
				else {
					draggedItem = Mideas.joueur1().getStuff(i);
				}
			}
			else if(checkCharacterItems(draggedItem)) {
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {        //draggeditem same stuff type of slot hover
					if(Mideas.joueur1().getStuff(i) == null) {
						setNullCharacter(draggedItem);
						calcStatsLess(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
					}
					else {
						Item tempItem = Mideas.joueur1().getStuff(i);
						setNullCharacter(draggedItem);
						calcStatsLess(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = tempItem;
					}
				}
				else {
					
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
					if(Mideas.joueur1().getStuff(i) == null) {
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
					}
					else {
						Item tempItem = Mideas.joueur1().getStuff(i);
						calcStatsLess(Mideas.joueur1().getStuff(i));
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						setNullContainer(draggedItem);
						draggedItem = tempItem;
					}
				}
			}
			else {
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
					if(Mideas.joueur1().getStuff(i) == null) {
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
					}
					else {
						Item tempItem = Mideas.joueur1().getStuff(i);
						calcStatsLess(Mideas.joueur1().getStuff(i));
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = tempItem;
					}
				}
			}
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
			/*Item tempItem = draggedItem;
			draggedItem = Mideas.joueur1().getStuff(i);
			if(tempItem != null && tempItem.getItemType() == ItemType.STUFF && ((Stuff)tempItem).getType() == type[i]) {
				Mideas.joueur1().setStuff(i, tempItem);
				setNullCharacter(tempItem);
				calcStats(Mideas.joueur1().getStuff(i));
				CharacterStuff.setEquippedItems();
			}*/
		}
	}
	
	private static void dropInventoryItem(int i) throws FileNotFoundException, SQLException {
		if(CharacterFrame.getHoverCharacterFrame(i) && draggedItem != null) {
			if(checkCharacterItems(draggedItem)) {
				if(Mideas.joueur1().getStuff(i) == null) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
						setNullCharacter(draggedItem);
						calcStatsLess(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
					}
				}
				else if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
					Item tempItem = Mideas.joueur1().getStuff(i);
					calcStatsLess(Mideas.joueur1().getStuff(i));
					setNullCharacter(draggedItem);
					Mideas.joueur1().setStuff(i, draggedItem);
					calcStats(draggedItem);
					draggedItem = tempItem;
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(Mideas.joueur1().getStuff(i) == null) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
						setNullContainer(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
					}
				}
				else {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
						Item tempItem = Mideas.joueur1().getStuff(i);
						calcStatsLess(Mideas.joueur1().getStuff(i));
						setNullContainer(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = tempItem;
					}
				}
			}
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
			/*if(draggedItem.getItemType() == ItemType.STUFF && ((Stuff)draggedItem).getType() == type[i] && !draggedItem.equals(Mideas.joueur1().getStuff(i))) {
				if(Mideas.joueur1().getStuff(i) == null) {
					if(((Stuff)draggedItem).canEquipTo(convClassType())) {
						//setNullContainer(draggedItem, null);
						setNullCharacter(draggedItem);
						Stuff tempItem = Mideas.joueur1().getStuff(i);
						Mideas.joueur1().setStuff(i, draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						calcStats(Mideas.joueur1().getStuff(i));
					}
					else {
						draggedItem  = null;
					}
				}
				else {
					if(((Stuff)draggedItem).canEquipTo(convClassType())) {
						Stuff tempItem = Mideas.joueur1().getStuff(i);
						Mideas.joueur1().setStuff(i, draggedItem);
						//setNullContainer(draggedItem, null);
						draggedItem = tempItem;
					}
				}
			}
			else {
				draggedItem = null;
			}*/
		}
	}
	
	private static boolean isCharacterHover() {
		int i = 0;
		while(i < CharacterFrame.getHoverCharacterFrame().length) {
			if(CharacterFrame.getHoverCharacterFrame(i)) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean isSpellBarHover() {
		int i = 0;
		while(i < SpellBarFrame.getHoverSpellBar().length) {
			if(SpellBarFrame.getHoverSpellBar(i)) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static ClassType convClassType() {
		if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			return ClassType.GUERRIER;
		}
		if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
			return ClassType.DEATHKNIGHT;
		}
		if(Mideas.joueur1().getClasse().equals("Hunter")) {
			return ClassType.HUNTER;
		}
		if(Mideas.joueur1().getClasse().equals("Mage")) {
			return ClassType.MAGE;
		}
		if(Mideas.joueur1().getClasse().equals("MONK")) {
			return ClassType.MONK;
		}
		if(Mideas.joueur1().getClasse().equals("Paladin")) {
			return ClassType.PALADIN;
		}
		if(Mideas.joueur1().getClasse().equals("Priest")) {
			return ClassType.PRIEST;
		}
		if(Mideas.joueur1().getClasse().equals("Rogue")) {
			return ClassType.ROGUE;
		}
		if(Mideas.joueur1().getClasse().equals("Shaman")) {
			return ClassType.SHAMAN;
		}
		if(Mideas.joueur1().getClasse().equals("Warlock")) {
			return ClassType.WARLOCK;
		}
		return null;
	}
	
	public static StuffType getStuffType(int i) {
		return type[i];
	}
	
	public static void setDraggedItem(Item item) {
		draggedItem = item;
	}
	
	public static Item getDraggedItem() {
		return draggedItem;
	}
}
