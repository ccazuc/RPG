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
import com.mideas.rpg.v2.game.stuff.StuffType;
import com.mideas.rpg.v2.game.stuff.item.Item;
import com.mideas.rpg.v2.game.stuff.item.potion.healingPotion.HealingPotion;
import com.mideas.rpg.v2.utils.Draw;

public class DragManager {
	
	private static StuffType[] type = new StuffType[]{StuffType.HEAD, StuffType.NECKLACE, StuffType.SHOULDERS, StuffType.CHEST, StuffType.BACK, StuffType.WRISTS, StuffType.GLOVES, StuffType.BELT, StuffType.LEGGINGS, StuffType.BOOTS, StuffType.RING, StuffType.TRINKET, StuffType.MAINHAND, StuffType.OFFHAND, StuffType.RANGED};
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
				TTF2.itemNumber.drawStringShadow(Mideas.mouseX()+35-TTF2.itemNumber.getWidth(String.valueOf(Mideas.joueur1().getNumberItem(draggedItem))), Mideas.mouseY()+20, String.valueOf(Mideas.joueur1().getNumberItem(draggedItem)), Color.white, Color.black, 1, 1, 1);
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
					if(draggedItem instanceof Item) {
						Mideas.joueur1().setNumberItem(draggedItem, 0);
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
					if(doHealingPotion(Mideas.bag().getBag(i), ContainerFrame.getContainerFrameSlotHover(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i)))) {
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
			Mideas.joueur1().setNumberItem(stuff, number);
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
			if(tempItem != null && tempItem.getType() == type[i]) {
				Mideas.joueur1().setStuff(i, tempItem);
				setNullCharacter(tempItem);
				calcStats(Mideas.joueur1().getStuff(i));
				CharacterStuff.setEquippedItems();
			}
		}
	}
	
	private static void dropInventoryItem(int i) throws FileNotFoundException {
		if(CharacterFrame.getHoverCharacterFrame(i) && draggedItem != null) {
			if(draggedItem.getType() == type[i] && !draggedItem.equals(Mideas.joueur1().getStuff(i))/* && (draggedItem.getClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getSecondClasse().equals(Mideas.joueur1().getClasse()) || draggedItem.getThirdClasse().equals(Mideas.joueur1().getClasse()))*/) {
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
