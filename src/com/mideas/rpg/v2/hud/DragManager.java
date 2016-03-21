package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

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
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.utils.Draw;

public class DragManager {
	
	private static StuffType[] type = new StuffType[]{StuffType.HEAD, StuffType.NECKLACE, StuffType.SHOULDERS, StuffType.BACK, StuffType.CHEST, StuffType.RAN, StuffType.RANDOM, StuffType.WRISTS, StuffType.GLOVES, StuffType.BELT, StuffType.LEGGINGS, StuffType.BOOTS, StuffType.RING, StuffType.RING, StuffType.TRINKET, StuffType.TRINKET, StuffType.MAINHAND, StuffType.OFFHAND, StuffType.RANGED, StuffType.RAN};
	private static boolean deleteItem;
	private static boolean[] clickInventory = new boolean[20];
	private static boolean[] clickBag = new boolean[32];
	private static boolean hoverDelete;
	private static boolean hoverSave;
	private static boolean leftClickInventoryDown;
	private static boolean leftClickBagDown;
	private static Item draggedItem;
	private static int mouseX;
	private static int mouseY;
	
	public static void draw() {
		hoverDelete = false;
		hoverSave = false;
		if(draggedItem != null) {
			Draw.drawQuad(IconsManager.getSprite42((draggedItem.getSpriteId())), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.stuff_border, Mideas.mouseX()-5, Mideas.mouseY()-5);
			if(draggedItem.getItemType() == ItemType.ITEM || draggedItem.getItemType() == ItemType.POTION) {
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
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(draggedItem != null && !isSpellBarHover() && !isHoverCharacterFrame() && !isHoverBagFrame()) {
					deleteItem = true;
					return true;
				}
				if(checkInventoryClick() && draggedItem == null) {
					leftClickInventoryDown = true;
					mouseX = Mideas.mouseX();
					mouseY = Mideas.mouseY();
				}
				if(checkBagClick() && draggedItem == null) {
					leftClickBagDown = true;
					mouseX = Mideas.mouseX();
					mouseY = Mideas.mouseY();
				}
				int i = 0;
				/*i = 0;
				if(isHoverBagFrame()) {
					while(i < Mideas.bag().getBag().length) {
						if(clickBagItem(i)) {
							break;
						}
						i++;
					}
				}
				i = 0;*/
				/*if(isHoverCharacterFrame()) {
					while(i < Mideas.joueur1().getStuff().length) {
						if(clickInventoryItem(i)) {
							break;
						}
						i++;
					}
				}*/
			}
			else {
				int i = 0;
				if(draggedItem != null) {
					if(Interface.getCharacterFrameStatus()) {
						if(Mideas.mouseX() >= Display.getWidth()/2-300 && Mideas.mouseX() <= Display.getWidth()/2+135 && Mideas.mouseY() >= Display.getHeight()/2-380 && Mideas.mouseY() <= Display.getHeight()/2+146) {
							if(!checkCharacterHover()) {
								if(checkCharacterItems(draggedItem)) {
									draggedItem = null;
									leftClickInventoryDown = false;
									Arrays.fill(clickInventory, false);
									return true;
								}
							}
						}
					}
					if(Interface.getContainerFrameStatus()) {
						if(Mideas.mouseX() >= Display.getWidth()-200 && Mideas.mouseX() <= Display.getWidth()-9 && Mideas.mouseY() >= Display.getHeight()/2-100 && Mideas.mouseY() <= Display.getHeight()+387) {
							if(!checkBagHover()) {
								while(i < Mideas.bag().getBag().length) {
									if(clickBagItem(i)) {
										return true;
									}
									i++;
								}
								draggedItem = null;
								leftClickBagDown = false;
								Arrays.fill(clickBag, false);
							}
						}
					}
				}
				if(leftClickInventoryDown && draggedItem == null) {
					while(i < CharacterFrame.getHoverCharacterFrame().length) {
						if(clickInventoryItem(i)) {
							leftClickInventoryDown = false;
							Arrays.fill(clickInventory, false);
							return true;
						}
						i++;
					}
				}
				if(leftClickBagDown && draggedItem == null && !checkBagHover()) {
					while(i < ContainerFrame.getContainerFrameSlotHover().length) {
						if(clickBagItem(i)) {
							leftClickBagDown = false;
							Arrays.fill(clickBag, false);
							return true;
						}
						i++;
					}
				}
				if(Interface.getContainerFrameStatus()) {
					if(Mideas.mouseX() >= Display.getWidth()-200 && Mideas.mouseX() <= Display.getWidth()-9 && Mideas.mouseY() >= Display.getHeight()/2-100 && Mideas.mouseY() <= Display.getHeight()+387) {
						i = 0;
						if(isHoverBagFrame()) {
							while(i < Mideas.bag().getBag().length) {
								if(clickBagItem(i)) {
									return true;
								}
								i++;
							}
						}
					}
				}
				if(Interface.getCharacterFrameStatus()) {
					if(Mideas.mouseX() >= Display.getWidth()/2-300 && Mideas.mouseX() <= Display.getWidth()/2+135 && Mideas.mouseY() >= Display.getHeight()/2-380 && Mideas.mouseY() <= Display.getHeight()/2+146) {
						i = 0;
						while(i < Mideas.joueur1().getStuff().length) {
							if(clickInventoryItem(i)) {
								return true;
							}
							i++;
						}
					}
				}
				/*while(i < CharacterFrame.getHoverCharacterFrame().length) {
					if(dropInventoryItem(i)) {
						SpellBarFrame.setBagChange(true);
						break;
					}
					i++;
				}*/
				i = 0;
				/*while(i < Mideas.bag().getBag().length) {
					if(dropBagItem(i)) {
						SpellBarFrame.setBagChange(true);
						break;
					}
					i++;
				}*/
				/*if(draggedItem != null && !isCharacterHover() && !isSpellBarHover()) {
					deleteItem = true;
				}*/
				leftClickInventoryDown = false;
				leftClickBagDown = false;
			}
		}
		if(leftClickInventoryDown && draggedItem == null) {
			if(Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseX)) >= 20 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseY)) >= 20) {
				setDraggedItemForCharacter();
				Arrays.fill(clickInventory, false);
			}
		}
		if(leftClickBagDown && draggedItem == null) {
			if(Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseX)) >= 10 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseY)) >= 10) {
				setDraggedItemForBag();
				Arrays.fill(clickBag, false);
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(hoverDelete && deleteItem) {
					if(draggedItem.getItemType() == ItemType.ITEM || draggedItem.getItemType() == ItemType.POTION) {
						Mideas.joueur1().setNumberItem(draggedItem, -1);
					}
					if(checkCharacterItems(draggedItem)) {
						calcStatsLess(draggedItem);
					}
					deleteItem(draggedItem);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					SpellBarFrame.setBagChange(true);
				}
				if(hoverSave && deleteItem) {
					if(!checkBagItems(draggedItem) && !checkCharacterItems(draggedItem)) {
						checkFreeSlotBag(draggedItem);
						SpellBarFrame.setBagChange(true);
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
					if(Mideas.bag().getBag(i) != null) {
						if(Mideas.bag().getBag(i).getItemType() == ItemType.POTION && doHealingPotion((Potion)Mideas.bag().getBag(i), ContainerFrame.getContainerFrameSlotHover(i))) {
							Mideas.bag().setBag(i, null);
							return true;
						}
						else if(Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
							equipBagItem(i);
						}
					}
					i++;
				}
			}
			else {
				if(draggedItem != null) {
					draggedItem = null;
				}
			}
		}
		return false;
	}
	
	public static void calcStats(Item stuff) throws FileNotFoundException, SQLException {
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
	
	private static void equipBagItem(int i) throws FileNotFoundException, SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			if(Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
				int j = 0;
				while(j < type.length) {
					if(Mideas.bag().getBag(i) != null && ((Stuff)Mideas.bag().getBag(i)).getType() == type[j]) {
						if(Mideas.joueur1().getStuff(j) == null) {
							Mideas.joueur1().setStuff(j, Mideas.bag().getBag(i));
							calcStats(Mideas.joueur1().getStuff(j));
							setNullContainer(Mideas.bag().getBag(i));
						}
						else {
							Item tempItem = Mideas.bag().getBag(i);
							Mideas.bag().setBag(i, Mideas.joueur1().getStuff(j));
							calcStatsLess(Mideas.joueur1().getStuff(j));
							Mideas.joueur1().setStuff(j, tempItem);
							calcStats(Mideas.joueur1().getStuff(j));
						}
					}
					j++;
				}
			}
		}
	}
	
	private static boolean doHealingPotion(Potion item, boolean hover) throws FileNotFoundException, SQLException {
		if(hover && item != null && item.getItemType() == ItemType.POTION) {
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
	
	
	private static boolean dropBagItem(int i) throws FileNotFoundException, SQLException {
	//System.out.println(Mideas.joueur1().getStuff(15));
	//System.out.println(Mideas.joueur1().getStuff(14));
		if(ContainerFrame.getContainerFrameSlotHover(i) && draggedItem != null) {
			//if(!draggedItem.equals(Mideas.bag().getBag(i))) {
			if(checkCharacterItems(draggedItem)) {                                          //if draggedItem comes from inventory       
				if(Mideas.bag().getBag(i) == null) { 
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					calcStatsLess(draggedItem);
					setNullCharacter(draggedItem);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				else {
					//if(checkFreeSlotBag(tempItem)) {
						//calcStatsLess(draggedItem);
						//setNullCharacter(draggedItem);
						exchangeCharacterItem(i);
						SpellBarFrame.setBagChange(true);
						draggedItem = null;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					//}
					/*else {
						tempItem = Mideas.bag().getBag(i);
						Mideas.bag().setBag(i, draggedItem);
						SpellBarFrame.setBagChange(true);
						draggedItem = tempItem;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}*/
				}
			}
			else if(checkBagItems(draggedItem)) {                             //if draggedItem comes from bags
				if(Mideas.bag().getBag(i) == null) {
					setNullContainer(draggedItem);
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					draggedItem = null;
					return true;
				}
				else {
					exchangeBagItems(draggedItem, Mideas.bag().getBag(i));
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean clickBagItem(int i) throws FileNotFoundException, SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			if(draggedItem == null) {
				if(Mideas.bag().getBag(i) == null) {
					return true;
				}
				else {
					draggedItem = Mideas.bag().getBag(i);
					return true;
				}
			}
			else if(checkCharacterItems(draggedItem)) {        
				if(Mideas.bag().getBag(i) == null) {
					calcStatsLess(draggedItem);
					setNullCharacter(draggedItem);
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					setNullCharacter(draggedItem);
					draggedItem = tempItem;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(Mideas.bag().getBag(i) == null) {
					setNullContainer(draggedItem);
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					Mideas.bag().setBag(checkItemSlot(draggedItem), tempItem);
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
					SpellBarFrame.setBagChange(true);
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
			}
			else {
				if(Mideas.bag().getBag(i) == null) {
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					Mideas.bag().setBag(i, draggedItem);
					SpellBarFrame.setBagChange(true);
					draggedItem = tempItem;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean clickInventoryItem(int i) throws FileNotFoundException, SQLException {
		if(CharacterFrame.getHoverCharacterFrame(i)) {
			if(draggedItem == null) {
				if(Mideas.joueur1().getStuff(i) == null) {
					return true;
				}
				else {
					draggedItem = Mideas.joueur1().getStuff(i);
					return true;
				}
			}
			else if(checkCharacterItems(draggedItem) && draggedItem.getItemType() == ItemType.STUFF) {
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {        //draggeditem same stuff type of slot hover
					if(Mideas.joueur1().getStuff(i) == null) {
						setNullCharacter(draggedItem);
						calcStatsLess(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
					else {
						Item tempItem = Mideas.joueur1().getStuff(i);
						setNullCharacter(draggedItem);
						calcStatsLess(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
				}
				else {
					return true;
				}
			}
			else if(checkBagItems(draggedItem) && draggedItem.getItemType() == ItemType.STUFF) {
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
					if(Mideas.joueur1().getStuff(i) == null) {
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						setNullContainer(draggedItem);
						draggedItem = null;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
					else {
						Item tempItem = Mideas.joueur1().getStuff(i);
						calcStatsLess(Mideas.joueur1().getStuff(i));
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						exchangeBagItem(i);
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
				}
			}
			else if(draggedItem.getItemType() == ItemType.STUFF){
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
					if(Mideas.joueur1().getStuff(i) == null) {
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
					else {
						Item tempItem = Mideas.joueur1().getStuff(i);
						calcStatsLess(Mideas.joueur1().getStuff(i));
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static boolean dropInventoryItem(int i) throws FileNotFoundException, SQLException {
		if(CharacterFrame.getHoverCharacterFrame(i) && draggedItem != null) {
			if(checkCharacterItems(draggedItem)) {
				if(Mideas.joueur1().getStuff(i) == null) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
						setNullCharacter(draggedItem);
						calcStatsLess(draggedItem);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
				}
				else if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
					Item tempItem = Mideas.joueur1().getStuff(i);
					calcStatsLess(Mideas.joueur1().getStuff(i));
					setNullCharacter(draggedItem);
					Mideas.joueur1().setStuff(i, draggedItem);
					calcStats(draggedItem);
					draggedItem = tempItem;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(Mideas.joueur1().getStuff(i) == null) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
						setNullContainer(draggedItem);
						SpellBarFrame.setBagChange(true);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
				}
				else {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType())) {
						Item tempItem = Mideas.joueur1().getStuff(i);
						calcStatsLess(Mideas.joueur1().getStuff(i));
						setNullContainer(draggedItem);
						SpellBarFrame.setBagChange(true);
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static boolean setDraggedItemForCharacter() {
		int i = 0;
		while(i < clickInventory.length) {
			if(clickInventory[i] == true) {
				draggedItem = Mideas.joueur1().getStuff(i);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean setDraggedItemForBag() {
		int i = 0;
		while(i < clickBag.length) {
			if(clickBag[i] == true) {
				draggedItem = Mideas.bag().getBag(i);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean checkBagClick() {
		int i = 0;
		while(i < clickBag.length) {
			if(ContainerFrame.getContainerFrameSlotHover(i) == true && draggedItem == null) {
				clickBag[i] = true;
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean checkInventoryClick() {
		int i = 0 ;
		while(i < clickInventory.length) {
			if(CharacterFrame.getHoverCharacterFrame(i) == true && draggedItem == null) {
				clickInventory[i] = true;
				return true;
			}
			i++;
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
			if(Mideas.joueur1().getSpells(i) != null && Mideas.joueur1().getSpells(i).getShortcutType() == ShortcutType.STUFF && ((StuffShortcut)Mideas.joueur1().getSpells(i)).getStuff() == draggedItem2) {
				Mideas.joueur1().setSpells(i, null);
			}
			i++;
		}
		i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && draggedItem2 == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				SpellBarFrame.setBagChange(true);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) != null && draggedItem2 == Mideas.joueur1().getStuff(i)) {
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
	
	public static boolean deleteItem(int id) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getId() == id) {
				if(Mideas.bag().getBag(i).getItemType() == ItemType.POTION || (Mideas.bag().getBag(i).getItemType() == ItemType.ITEM)) {
					Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), 0);
				}
				Mideas.bag().setBag(i, null);
				SpellBarFrame.setBagChange(true);
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
	public static boolean setNullContainer(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(item == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				SpellBarFrame.setBagChange(true);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean checkFreeSlotBag(Item item) {
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
				SpellBarFrame.setBagChange(true);
				return true;
			}
			i++;
		}
		return false;
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
		if(i < type.length) {
			return type[i];
		}
		return null;
	}
	
	public static void setDraggedItem(Item item) {
		draggedItem = item;
	}
	
	public static Item getDraggedItem() {
		return draggedItem;
	}
	
	private static boolean isHoverCharacterFrame() {
		if(Mideas.mouseX() >= Display.getWidth()/2-300 && Mideas.mouseX() <= Display.getWidth()/2+135 && Mideas.mouseY() >= Display.getHeight()/2-380 && Mideas.mouseY() <= Display.getHeight()/2+146) {
			return true;
		}
		return false;
	}
	
	private static boolean isHoverBagFrame() {
		if(Mideas.mouseX() >= Display.getWidth()-200 && Mideas.mouseX() <= Display.getWidth()-9 && Mideas.mouseY() >= Display.getHeight()/2-100 && Mideas.mouseY() <= Display.getHeight()/2+387) {
			return true;
		}
		return false;
	}
	
	private static boolean checkBagHover() {
		int i = 0;
		while(i < ContainerFrame.getContainerFrameSlotHover().length) {
			if(clickBag[i] == true && clickBag[i] == ContainerFrame.getContainerFrameSlotHover(i)) {
				return true;
			}
			i++;
		}
		return false;
	}
	private static boolean checkCharacterHover() {
		int i = 0;
		while(i < CharacterFrame.getHoverCharacterFrame().length) {
			if(clickInventory[i] == true && clickInventory[i] == CharacterFrame.getHoverCharacterFrame(i)) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static void exchangeBagItem(int i) {
		Item tempItem = Mideas.bag().getBag(i);
		
	}
	
	private static void exchangeCharacterItem(int i) {
		Item tempItem = Mideas.bag().getBag(i);
		if(tempItem.getItemType() == ItemType.STUFF) {
			int j = 0;
			while(j < Mideas.joueur1().getStuff().length) {
				if(Mideas.joueur1().getStuff(j) == draggedItem) {
					if(((Stuff)tempItem).getType() == ((Stuff)draggedItem).getType()) {
						Mideas.joueur1().setStuff(j, tempItem);
						Mideas.bag().setBag(i, draggedItem);
					}
					break;
				}
				j++;
			}
		}
	}
	
	private static int checkItemSlot(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == item) {
				return i;
			}
			i++;
		}
		return 0;
	}
}
