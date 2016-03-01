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
				if(draggedItem != null) {
					deleteItem = true;
				}
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(hoverDelete && deleteItem) {
					if(draggedItem.getItemType() == ItemType.ITEM) {
						Mideas.joueur1().setNumberItem(draggedItem, ContainerFrame.getSlotItem(draggedItem), 0);
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
		while(i < Mideas.bag().getBag().length) {
			if(tempItem.equals(Mideas.bag().getBag(i))) {
				Mideas.bag().setBag(i, null);
				return true;
			}
			i++;
 		}
		i = 0;
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
	
	private static boolean setNullContainer(Item draggedItem2, Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(draggedItem2 == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, item);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(draggedItem2 != null & Mideas.joueur1().getStuff(i) != null && draggedItem2.equals(Mideas.joueur1().getStuff(i))) {
				System.out.println("a");
				calcStatsLess(Mideas.joueur1().getStuff(i));
				Mideas.joueur1().setStuff(i, null);
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
			Mideas.joueur1().setNumberItem(item, ContainerFrame.getSlotItem(item), -1);
			CharacterStuff.setBagItems();
			if(number <= 1) {
				return true;
			}
		}
		return false;
	}
	
	private static void dropBagItem(int i) throws FileNotFoundException, SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i) && draggedItem != null) {
			if(!draggedItem.equals(Mideas.bag().getBag(i))) {
				System.out.println("a");
				Item tempItem = Mideas.bag().getBag(i);
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
	
	private static void clickBagItem(int i) throws FileNotFoundException, SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			Item tempItem = draggedItem;
			draggedItem = Mideas.bag().getBag(i);
			if(tempItem != null) {
				setNullContainer(tempItem, Mideas.bag().getBag(i));
				Mideas.bag().setBag(i, tempItem);;
				CharacterStuff.setBagItems();
			}
		}
	}
	
	private static void clickInventoryItem(int i) throws FileNotFoundException, SQLException {
		if(CharacterFrame.getHoverCharacterFrame(i)) {
			Item tempItem = draggedItem;
			draggedItem = Mideas.joueur1().getStuff(i);
			if(tempItem != null && tempItem.getItemType() == ItemType.STUFF && ((Stuff)tempItem).getType() == type[i]) {
				Mideas.joueur1().setStuff(i, tempItem);
				setNullCharacter(tempItem);
				calcStats(Mideas.joueur1().getStuff(i));
				CharacterStuff.setEquippedItems();
			}
		}
	}
	
	private static void dropInventoryItem(int i) throws FileNotFoundException, SQLException {
		if(CharacterFrame.getHoverCharacterFrame(i) && draggedItem != null) {
			if(draggedItem.getItemType() == ItemType.STUFF && ((Stuff)draggedItem).getType() == type[i] && !draggedItem.equals(Mideas.joueur1().getStuff(i))) {
				if(((Stuff)draggedItem).canEquipTo(convClassType())) {
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
				draggedItem = null;
			}
		}
	}
	
	public static Item getDraggedItem() {
		return draggedItem;
	}
	
	private static ClassType convClassType() {
		ClassType tempClasse = null;
		if(Mideas.joueur1().getClasse().equals("Guerrier")) {
			tempClasse = ClassType.GUERRIER;
		}
		else if(Mideas.joueur1().getClasse().equals("DeathKnight")) {
			tempClasse = ClassType.DEATHKNIGHT;
		}
		else if(Mideas.joueur1().getClasse().equals("Hunter")) {
			tempClasse = ClassType.HUNTER;
		}
		else if(Mideas.joueur1().getClasse().equals("Mage")) {
			tempClasse = ClassType.MAGE;
		}
		else if(Mideas.joueur1().getClasse().equals("MONK")) {
			tempClasse = ClassType.MONK;
		}
		else if(Mideas.joueur1().getClasse().equals("Paladin")) {
			tempClasse = ClassType.PALADIN;
		}
		else if(Mideas.joueur1().getClasse().equals("Priest")) {
			tempClasse = ClassType.PRIEST;
		}
		else if(Mideas.joueur1().getClasse().equals("Rogue")) {
			tempClasse = ClassType.ROGUE;
		}
		else if(Mideas.joueur1().getClasse().equals("Shaman")) {
			tempClasse = ClassType.SHAMAN;
		}
		else if(Mideas.joueur1().getClasse().equals("Warlock")) {
			tempClasse = ClassType.WARLOCK;
		}
		System.out.println(tempClasse);
		return tempClasse;
	}
}
