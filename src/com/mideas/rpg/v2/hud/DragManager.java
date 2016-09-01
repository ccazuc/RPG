package com.mideas.rpg.v2.hud;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.enumlist.ItemType;
import com.mideas.rpg.v2.enumlist.StuffType;
import com.mideas.rpg.v2.enumlist.WeaponSlot;
import com.mideas.rpg.v2.enumlist.Wear;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Draw;

public class DragManager {
	
	private static StuffType[] type = new StuffType[]{StuffType.HEAD, StuffType.NECKLACE, StuffType.SHOULDERS, StuffType.BACK, StuffType.CHEST, StuffType.RAN, StuffType.RANDOM, StuffType.WRISTS, StuffType.GLOVES, StuffType.BELT, StuffType.LEGGINGS, StuffType.BOOTS, StuffType.RING, StuffType.RING, StuffType.TRINKET, StuffType.TRINKET, StuffType.MAINHAND, StuffType.OFFHAND, StuffType.RANGED, StuffType.RAN};
	private static WeaponSlot[] weaponSlot = new WeaponSlot[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, WeaponSlot.MAINHAND, WeaponSlot.OFFHAND, WeaponSlot.RANGED};
	static boolean deleteItem;
	private static boolean init;
	private static boolean[] clickInventory = new boolean[20];
	private static boolean[] clickBag = new boolean[97];
	private static boolean leftClickInventoryDown;
	private static boolean leftClickBagDown;
	private static boolean rightClickInventoryDown;
	private static boolean rightClickBagDown;
	static Item draggedItem;
	private static int bagClickedSlot;
	private static int inventoryClickedSlot;
	private static int mouseX;
	private static int mouseY;
	private static boolean draggedItemSplit;
	private static Button hoverDeleteYes = new Button(Display.getWidth()/2-130*Mideas.getDisplayXFactor(), Display.getHeight()/2-43*Mideas.getDisplayYFactor(), Sprites.button_hover.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.button_hover.getImageHeight()*Mideas.getDisplayYFactor(), "Yes", 14) {
		@Override
		public void eventButtonClick() throws SQLException {
			if(draggedItem.isStackable()) {
				Mideas.joueur1().setNumberItem(draggedItem, -1);
				SpellBarFrame.setItemChange(true);
			}
			if(checkCharacterItems(draggedItem)) {
				calcStatsLess(draggedItem);
			}
			deleteItem(draggedItem);
			draggedItem = null;
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
			Mideas.bag().setBagChange(true);
			deleteItem = false;
		}
	};

	private static Button hoverDeleteNo = new Button(Display.getWidth()/2+7*Mideas.getDisplayXFactor(), Display.getHeight()/2-43*Mideas.getDisplayYFactor(), Sprites.button_hover.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.button_hover.getImageHeight()*Mideas.getDisplayYFactor(), "No", 14) {
		@Override
		public void eventButtonClick() throws SQLException {
			if(!checkBagItems(draggedItem) && !checkCharacterItems(draggedItem)) {
				checkFreeSlotBag(draggedItem);
				Mideas.bag().setBagChange(true);
			}
			draggedItem = null;
			deleteItem = false;
		}
	};
	
	public static void draw() {
		if(!init) {
			updateSize();
			init = true;
		}
		if(draggedItem != null) {
			Draw.drawQuad(IconsManager.getSprite42((draggedItem.getSpriteId())), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.stuff_border, Mideas.mouseX()-5, Mideas.mouseY()-5);
			if(draggedItem.isStackable()) {
				TTF2.itemNumber.drawStringShadow(Mideas.mouseX()+35-TTF2.itemNumber.getWidth(String.valueOf(Mideas.joueur1().getNumberItem(draggedItem))), Mideas.mouseY()+20, String.valueOf(Mideas.joueur1().getNumberItem(draggedItem)), Color.white, Color.black, 1, 1, 1);
			}
		}
		if(deleteItem && draggedItem != null) {
			Draw.drawQuad(Sprites.alert, Display.getWidth()/2-172*Mideas.getDisplayXFactor(), Display.getHeight()/2-80*Mideas.getDisplayYFactor(), Sprites.alert.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.alert.getImageHeight()*Mideas.getDisplayYFactor());
			TTF2.font4.drawStringShadow(Display.getWidth()/2-(TTF2.font4.getWidth("Voulez vous supprimer "+draggedItem.getStuffName())*Mideas.getDisplayXFactor())/2, Display.getHeight()/2-85*Mideas.getDisplayYFactor(), "Voulez vous supprimer "+draggedItem.getStuffName(), Color.white, Color.black, 1, Mideas.getDisplayXFactor(), Mideas.getDisplayXFactor());
			hoverDeleteYes.draw();
			hoverDeleteNo.draw();
		}
	}
	
	public static boolean mouseEvent() throws SQLException, NoSuchAlgorithmException {
		if(!ContainerFrame.isHoverItemNumberFrame()) {
			if(Keyboard.isKeyDown(42) && !Mouse.getEventButtonState() && (Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)) { //split item
				int i = 0;
				while(i < Mideas.bag().getBag().length) {
					if(ContainerFrame.getContainerFrameSlotHover(i)) {
						if(Mideas.bag().getBag(i) != null) {
							if(Mideas.bag().getBag(i).isStackable()) {
								if(Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i)) > 1) {
									ContainerFrame.setItemNumberOpen(i, true);
									leftClickBagDown = false;
									Arrays.fill(clickBag, false);
									return true;
								}
							}
						}
					}
					i++;
				}
			}
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
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
				}
				else if(Mouse.getEventButton() == 1) {
					if(checkInventoryClick() && draggedItem == null) {
						rightClickInventoryDown = true;
						mouseX = Mideas.mouseX();
						mouseY = Mideas.mouseY();
					}
					if(checkBagClick() && draggedItem == null) {
						rightClickBagDown = true;
						mouseX = Mideas.mouseX();
						mouseY = Mideas.mouseY();
					}
				}
			}
			else {
				if(Mouse.getEventButton() == 0) {
					int i = 0;
					if(draggedItem != null) {
						if(draggedItem != null && !deleteItem && !isSpellBarHover() && !isHoverCharacterFrame() && !isHoverBagFrame()) {
							deleteItem = true;
							return true;
						}
						if(Interface.getCharacterFrameStatus()) {
							if(isHoverCharacterFrame()) {				
								if(!checkCharacterHover() && !deleteItem) {
									if(checkCharacterItems(draggedItem)) {
										draggedItem = null;
										leftClickInventoryDown = false;
										clickInventory[inventoryClickedSlot] = false;
										return true;
									}
								}
							}
						}
						if(isHoverBagFrame()) {
							if(!checkBagHover()) {
								while(i < Mideas.bag().getBag().length) {
									if(clickBagItem(i)) {
										draggedItem = null;
										leftClickBagDown = false;
										clickInventory[inventoryClickedSlot] = false;
										return true;
									}
									i++;
								}
							}
						}
					}
					if(leftClickInventoryDown && draggedItem == null) {
						while(i < CharacterFrame.getHoverCharacterFrame().length) {
							if(clickInventoryItem(i)) {
								leftClickInventoryDown = false;
								clickInventory[inventoryClickedSlot] = false;
								return true;
							}
							i++;
						}
					}
					if(leftClickBagDown && draggedItem == null && !checkBagHover()) {
						while(i < Mideas.bag().getBag().length) {
							if(clickBagItem(i)) {
								leftClickBagDown = false;
								clickBag[bagClickedSlot] = false;
								return true;
							}
							i++;
						}
					}
					if(Interface.getContainerFrameStatus()) {
						i = 0;
						if(isHoverBagFrame()) {
							while(i < Mideas.bag().getBag().length) {
								if(clickBagItem(i)) {
									leftClickBagDown = false;
									clickBag[bagClickedSlot] = false;
									return true;
								}
								i++;
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
					leftClickInventoryDown = false;
					leftClickBagDown = false;
					clickBag[bagClickedSlot] = false;
					clickInventory[inventoryClickedSlot] = false;
				}
			}
			if(draggedItem == null && Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseX)) >= 15 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseY)) >= 15) {
				if(leftClickBagDown) {
					setDraggedItemForBag();
					clickBag[bagClickedSlot] = false;
				}
				if(leftClickInventoryDown) {
					setDraggedItemForCharacter();
					clickInventory[inventoryClickedSlot] = false;
				}
				if(rightClickBagDown || rightClickInventoryDown) {
					clickInventory[inventoryClickedSlot] = false;
					clickBag[bagClickedSlot] = false;
				}
			}
			if(Mouse.getEventButton() == 0) {
				if(!Mouse.getEventButtonState()) {
					if(!hoverDeleteYes.getButtonDown() && !hoverDeleteNo.getButtonDown()) {
						deleteItem = false;
					}
				}
			}
			if(Mouse.getEventButton() == 1 && !Keyboard.isKeyDown(42)) {
				if(!Mouse.getEventButtonState()) {
					int i = 0;
					while(i < Mideas.bag().getBag().length) {
						if(Mideas.bag().getBag(i) != null) {
							if(ContainerFrame.getContainerFrameSlotHover(i)) {
								if(Mideas.bag().getBag(i).getItemType() == ItemType.POTION) {
									doHealingPotion((Potion)Mideas.bag().getBag(i), ContainerFrame.getContainerFrameSlotHover(i), i);
									SpellBarFrame.setItemChange(true);
									clickBag[bagClickedSlot] = false;
									return true;
								}
								else if(Mideas.bag().getBag(i).getItemType() == ItemType.STUFF || Mideas.bag().getBag(i).getItemType() == ItemType.WEAPON) {
									equipBagItem(i);
									clickBag[bagClickedSlot] = false;
									return true;
								}
							}
						}
						i++;
					}
					Arrays.fill(clickBag, false);
					clickBag[bagClickedSlot] = false;
				}
				else {
					if(checkBagClick() && draggedItem == null) {
						return true;
					}
					if(draggedItem != null) {
						draggedItem = null;
					}
				}
			}
			if(deleteItem && draggedItem != null) {
				hoverDeleteYes.event();
				hoverDeleteNo.event();
			}
		}
		return false;
	}
	
	public static void calcStats(Item stuff) throws SQLException {
		if(stuff != null && (stuff.getItemType() == ItemType.STUFF || stuff.getItemType() == ItemType.WEAPON)) {
			CharacterStuff.setEquippedItems();
			Mideas.joueur1().setStuffArmor(((Stuff)stuff).getArmor());
			Mideas.joueur1().setStuffStamina(((Stuff)stuff).getStamina());
			Mideas.joueur1().setStuffStrength(((Stuff)stuff).getStrength());
			Mideas.joueur1().setStuffMana(((Stuff)stuff).getMana());
			Mideas.joueur1().setStuffCritical(((Stuff)stuff).getCritical());
		}
	}
	
	public static void calcStatsLess(Item stuff) {
		if(stuff != null && (stuff.getItemType() == ItemType.STUFF || stuff.getItemType() == ItemType.WEAPON)) {
			Mideas.joueur1().setStuffArmor(-((Stuff)stuff).getArmor());
			Mideas.joueur1().setStuffStamina(-((Stuff)stuff).getStamina());
			Mideas.joueur1().setStuffStrength(-((Stuff)stuff).getStrength());
			Mideas.joueur1().setStuffMana(-((Stuff)stuff).getMana());
			Mideas.joueur1().setStuffCritical(-((Stuff)stuff).getCritical());
		}
	}
	
	private static boolean equipBagItem(int i) throws SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			if(Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
				int j = 0;
				while(j < type.length) {
					if(((Stuff)Mideas.bag().getBag(i)).getType() == type[j] && ((Stuff)Mideas.bag().getBag(i)).canEquipTo(convClassType()) && canWear((Stuff)Mideas.bag().getBag(i))) {
						if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel()) {
							if(Mideas.joueur1().getStuff(j) == null) {
								Mideas.joueur1().setStuff(j, Mideas.bag().getBag(i));
								calcStats(Mideas.joueur1().getStuff(j));
								setNullContainer(Mideas.bag().getBag(i));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								Mideas.bag().setBagChange(true);
								return true;
							}
							else {
								Item tempItem = Mideas.bag().getBag(i);
								Mideas.bag().setBag(i, Mideas.joueur1().getStuff(j));
								calcStatsLess(Mideas.joueur1().getStuff(j));
								Mideas.joueur1().setStuff(j, tempItem);
								calcStats(Mideas.joueur1().getStuff(j));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								return true;
							}
						}
					}
					j++;
				}
			}
			else if(Mideas.bag().getBag(i).getItemType() == ItemType.WEAPON) {
				int j = 0;
				while(j < type.length) {
					if(((Stuff)Mideas.bag().getBag(i)) != null && ((Stuff)Mideas.bag().getBag(i)).getWeaponSlot() == weaponSlot[j] && ((Stuff)Mideas.bag().getBag(i)).canEquipTo(convClassType())) {
						if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel()) {
							if(Mideas.joueur1().getStuff(j) == null) {
								Mideas.joueur1().setStuff(j, Mideas.bag().getBag(i));
								calcStats(Mideas.joueur1().getStuff(j));
								setNullContainer(Mideas.bag().getBag(i));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								Mideas.bag().setBagChange(true);
								return true;
							}
							else {
								Item tempItem = Mideas.bag().getBag(i);
								Mideas.bag().setBag(i, Mideas.joueur1().getStuff(j));
								calcStatsLess(Mideas.joueur1().getStuff(j));
								Mideas.joueur1().setStuff(j, tempItem);
								calcStats(Mideas.joueur1().getStuff(j));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								return true;
							}
						}
					}
					j++;
				}
			}
		}
		return false;
	}
	
	private static void doHealingPotion(Potion item, boolean hover, int i) throws SQLException {
		if(hover && item != null && item.getItemType() == ItemType.POTION && Mideas.getLevel() >= item.getLevel()) {
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
			if(Mideas.joueur1().getNumberItem(item) <= 0) {
				Mideas.bag().setBag(i, null);
				Mideas.bag().getNumberStack().remove(item);
				Mideas.bag().setBagChange(true);
			}
			CharacterStuff.setBagItems();
		}
	}
	
	
	private static void equipItem(int i) throws SQLException {
		if(Mideas.bag().getBag(i).getItemType() == ItemType.STUFF && draggedItem.getItemType() == ItemType.STUFF) {
			if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel() && ((Stuff)Mideas.bag().getBag(i)).canEquipTo(convClassType()) && ((Stuff)Mideas.bag().getBag(i)).getType() == ((Stuff)draggedItem).getType()) {
				Stuff temp = (Stuff)Mideas.bag().getBag(i);
				Mideas.bag().setBag(i, draggedItem);
				calcStatsLess(draggedItem);
				Mideas.joueur1().setStuff(i, temp);
				calcStats(temp);
			}
		}
	}
	
	private static boolean clickBagItem(int i) throws SQLException {
		if(ContainerFrame.getContainerFrameSlotHover(i) && !DragSpellManager.isHoverSpellBarFrame()) {
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
					Mideas.bag().setBagChange(true);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				else {
					equipItem(i);
					return true;
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(Mideas.bag().getBag(i) == null) {
					setNullContainer(draggedItem);
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
				else if(draggedItem.getItemType() == ItemType.POTION && draggedItem.getId() == Mideas.bag().getBag(i).getId()) {
					if(draggedItem != Mideas.bag().getBag(i)) {
						int number = Mideas.joueur1().getNumberItem(draggedItem)+Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i));
						System.out.println(number);
						setNullContainer(draggedItem);
						Mideas.bag().getNumberStack().put(Mideas.bag().getBag(i), number);
						draggedItem = null;
						Mideas.bag().getNumberStack().remove(draggedItem);
						CharacterStuff.setBagItems();
						return true;
					}
					else {
						draggedItem = null;
						return true;
					}
				}
				else {
					Item tempItem = Mideas.bag().getBag(i);
					Mideas.bag().setBag(checkItemSlot(draggedItem), tempItem);
					Mideas.bag().setBag(i, draggedItem);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
			}
			else {
				if(Mideas.bag().getBag(i) == null) {
					Mideas.bag().setBag(i, draggedItem);
					Mideas.bag().setBagChange(true);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
				else {
					if(draggedItem.getItemType() == ItemType.POTION && draggedItem.getId() == Mideas.bag().getBag(i).getId()) {
						if(draggedItem != Mideas.bag().getBag(i)) {
							int number = Mideas.joueur1().getNumberItem(draggedItem)+Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i));
							setNullContainer(draggedItem);
							Mideas.bag().getNumberStack().put(Mideas.bag().getBag(i), number);
							draggedItem = null;
							Mideas.bag().getNumberStack().remove(draggedItem);
							CharacterStuff.setBagItems();
							return true;
						}
						else {
							draggedItem = null;
							return true;
						}
					}
					if(draggedItemSplit) {
						if(draggedItem.getId() != Mideas.bag().getBag(i).getId()) {
							Mideas.joueur1().setNumberItem(Mideas.bag().getBag(ContainerFrame.getLastSplit()), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(ContainerFrame.getLastSplit()))+Mideas.joueur1().getNumberItem(draggedItem));
							draggedItem = null;
							CharacterStuff.setBagItems();
							draggedItemSplit = false;
							return true;
						}
					}
					else {
						Item tempItem = Mideas.bag().getBag(i);
						Mideas.bag().setBag(i, draggedItem);
						draggedItem = tempItem;
						CharacterStuff.setBagItems();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static boolean clickInventoryItem(int i) throws SQLException {
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
			else if(checkCharacterItems(draggedItem)) {
				if(draggedItem.getItemType() == ItemType.STUFF) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType()) && Mideas.getLevel() >= ((Stuff)draggedItem).getLevel() && canWear((Stuff)draggedItem)) {        //draggeditem same stuff type of slot hover
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
						draggedItem = null;
						return true;
					}
				}
				else if(draggedItem.getItemType() == ItemType.WEAPON) {
					if((((Stuff)draggedItem).getWeaponSlot() == weaponSlot[i]) && ((Stuff)draggedItem).canEquipTo(convClassType()) && Mideas.getLevel() >= ((Stuff)draggedItem).getLevel() && ((Stuff)draggedItem).canWearWeapon()) {
						if(Mideas.joueur1().getStuff(i) == null) {
							setNullCharacter(draggedItem);
							calcStatsLess(draggedItem);
							Mideas.joueur1().setStuff(i, draggedItem);
							calcStats(draggedItem);
							CharacterStuff.setEquippedItems();
							CharacterStuff.setBagItems();
						}
					}
				}
			}
			else if(checkBagItems(draggedItem)) {
				if(draggedItem.getItemType() == ItemType.STUFF) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType()) && Mideas.getLevel() >= ((Stuff)draggedItem).getLevel() && canWear((Stuff)draggedItem)) {
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
							Stuff temp = Mideas.joueur1().getStuff(i);
							calcStatsLess(Mideas.joueur1().getStuff(i));
							Mideas.joueur1().setStuff(i, draggedItem);
							calcStats(draggedItem);
							setNullContainer(draggedItem);
							draggedItem = temp;
							CharacterStuff.setEquippedItems();
							CharacterStuff.setBagItems();
							return true;
						}
					}
					else {
						draggedItem = null;
					}
				}
				else if(draggedItem.getItemType() == ItemType.WEAPON) {
					if((((Stuff)draggedItem).getWeaponSlot() == weaponSlot[i]) && ((Stuff)draggedItem).canEquipTo(convClassType()) && Mideas.getLevel() >= ((Stuff)draggedItem).getLevel() && ((Stuff)draggedItem).canWearWeapon()) {
						if(Mideas.joueur1().getStuff(i) == null) {
							Mideas.joueur1().setStuff(i, draggedItem);
							calcStats(draggedItem);
							setNullContainer(draggedItem);
							draggedItem = null;
							CharacterStuff.setEquippedItems();
							CharacterStuff.setBagItems();
						}
					}
				}
				else {
					draggedItem = null;
				}
			}
			else if(draggedItem.getItemType() == ItemType.STUFF){
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(convClassType()) && Mideas.getLevel() >= ((Stuff)draggedItem).getLevel() && canWear((Stuff)draggedItem)) {
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
				else {
					draggedItem = null;
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
				bagClickedSlot = i;
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
				inventoryClickedSlot = i;
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

	static boolean deleteItem(Item draggedItem2) {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(Mideas.joueur1().getSpells(i) != null && Mideas.joueur1().getSpells(i).getShortcutType() == ShortcutType.STUFF && ((StuffShortcut)Mideas.joueur1().getSpells(i)).getStuff() == draggedItem2) {
				Mideas.joueur1().setSpells(i, null);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && draggedItem2 == Mideas.bag().getBag(i)) {
				Mideas.bag().setBag(i, null);
				Mideas.bag().setBagChange(true);
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
	
	static boolean checkCharacterItems(Item item) {
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
				if(Mideas.bag().getBag(i).isStackable()) {
					Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), 0);
				}
				Mideas.bag().setBag(i, null);
				Mideas.bag().setBagChange(true);
				return true;
			}
			i++;
		}
		return false;
	}
	
	static boolean checkBagItems(Item item) {
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
				Mideas.bag().setBagChange(true);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean checkFreeSlotBag(Potion potion) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getId() == potion.getId()) {
				Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i))+1);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				Mideas.bag().setBag(i, potion);
				Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), 1);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean isSpellBarHover() {
		int i = 0;
		while(i < SpellBarFrame.getHoverSpellBar().length) {
			if(SpellBarFrame.getHoverSpellBar(i)) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static ClassType convClassType() {
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
		if(Mideas.joueur1().getClasse().equals("MonK")) {
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
	
	public static boolean isHoverCharacterFrame() {
		if(Interface.getCharacterFrameStatus()) {
			if(Mideas.mouseX() >= Display.getWidth()/2-300+CharacterFrame.getMouseX() && Mideas.mouseX() <= Display.getWidth()/2-300+Sprites.character_frame.getImageWidth()+CharacterFrame.getMouseX() && Mideas.mouseY() >= Display.getHeight()/2-380+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2-380+Sprites.character_frame.getImageHeight()+CharacterFrame.getMouseY()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoverBagFrame() {
		if(Interface.getContainerFrameStatus()) {
			if(Mideas.mouseX() >= Display.getWidth()-520 && Mideas.mouseX() <= Display.getWidth()-320+Sprites.back_bag.getImageWidth()) {
				return true;
			}
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
	
	public static int checkItemSlot(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == item) {
				return i;
			}
			i++;
		}
		return 0;
	}
	
	public static boolean getClickBag(int i) {
		return clickBag[i];
	}
	
	public static boolean getClickInventory(int i) {
		return clickInventory[i];
	}
	
	public static boolean canWear(Stuff stuff) {
		if(stuff != null) {
			if(Mideas.joueur1().getWear() == Wear.PLATE) {
				return true;
			}
			if(Mideas.joueur1().getWear() == Wear.MAIL) {
				if(stuff.getWear() == Wear.PLATE) {
					return false;
				}
				return true;
			}
			if(Mideas.joueur1().getWear() == Wear.LEATHER) {
				if(stuff.getWear() == Wear.PLATE || stuff.getWear() == Wear.MAIL) {
					return false;
				}
				return true;
			}
			if(Mideas.joueur1().getWear() == Wear.CLOTH) {
				if(stuff.getWear() == Wear.CLOTH || stuff.getWear() == Wear.NONE) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public static void setDraggedItemSplit(boolean we) {
		draggedItemSplit = we;
	}
	
	public static WeaponSlot getWeaponSlot(int i) {
		return weaponSlot[i];
	}
	
	public static void updateSize() {
		hoverDeleteYes.setX(Display.getWidth()/2-130*Mideas.getDisplayXFactor());
		hoverDeleteYes.setY(Display.getHeight()/2-43*Mideas.getDisplayYFactor());
		hoverDeleteYes.setButtonWidth(Sprites.button_hover.getImageWidth()*Mideas.getDisplayXFactor());
		hoverDeleteYes.setButtonHeight(Sprites.button_hover.getImageHeight()*Mideas.getDisplayYFactor());
		hoverDeleteNo.setX(Display.getWidth()/2+7*Mideas.getDisplayXFactor());
		hoverDeleteNo.setY(Display.getHeight()/2-43*Mideas.getDisplayYFactor());
		hoverDeleteNo.setButtonWidth(Sprites.button_hover.getImageWidth()*Mideas.getDisplayXFactor());
		hoverDeleteNo.setButtonHeight(Sprites.button_hover.getImageHeight()*Mideas.getDisplayYFactor());
	}
}
