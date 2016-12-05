package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.Joueur;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffType;
import com.mideas.rpg.v2.game.item.weapon.WeaponSlot;
import com.mideas.rpg.v2.game.shortcut.ShortcutType;
import com.mideas.rpg.v2.game.shortcut.StuffShortcut;
import com.mideas.rpg.v2.utils.Button;
import com.mideas.rpg.v2.utils.Color;
import com.mideas.rpg.v2.utils.Draw;

public class DragManager {
	
	private static StuffType[] type = new StuffType[]{StuffType.HEAD, StuffType.NECKLACE, StuffType.SHOULDERS, StuffType.BACK, StuffType.CHEST, StuffType.RAN, StuffType.RANDOM, StuffType.WRISTS, StuffType.GLOVES, StuffType.BELT, StuffType.LEGGINGS, StuffType.BOOTS, StuffType.RING, StuffType.RING, StuffType.TRINKET, StuffType.TRINKET, StuffType.MAINHAND, StuffType.OFFHAND, StuffType.RANGED, StuffType.RAN};
	private static WeaponSlot[] weaponSlot = new WeaponSlot[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, WeaponSlot.MAINHAND, WeaponSlot.OFFHAND, WeaponSlot.RANGED};
	static boolean deleteItem;
	private static boolean init;
	private static boolean leftClickInventoryDown;
	private static boolean leftClickBagDown;
	private static boolean rightClickInventoryDown;
	private static boolean rightClickBagDown;
	static Item draggedItem;
	private static int bagClickedSlot = -1;
	private static int inventoryClickedSlot = -1;
	private static int mouseX;
	private static int mouseY;
	private static boolean draggedItemSplit;
	private static Button hoverDeleteYes = new Button(Display.getWidth()/2-130*Mideas.getDisplayXFactor(), Display.getHeight()/2-43*Mideas.getDisplayYFactor(), 127*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Yes", 14, 1) {
		@Override
		public void eventButtonClick() {
			if(draggedItem.isStackable()) {
				Mideas.joueur1().bag().setBagChange(true);
			}
			if(checkCharacterItems(draggedItem)) {
				calcStatsLess(draggedItem);
			}
			deleteItem(draggedItem);
			draggedItem = null;
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
			Mideas.joueur1().bag().setBagChange(true);
			this.setHoverFalse();
			deleteItem = false;
		}
	};

	private static Button hoverDeleteNo = new Button(Display.getWidth()/2+7*Mideas.getDisplayXFactor(), Display.getHeight()/2-43*Mideas.getDisplayYFactor(), 127*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "No", 14, 1) {
		@Override
		public void eventButtonClick() {
			if(!checkBagItems(draggedItem) && !checkCharacterItems(draggedItem)) {
				checkFreeSlotBag(draggedItem);
				Mideas.joueur1().bag().setBagChange(true);
			}
			this.setHoverFalse();
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
				FontManager.get("FRIZQT", 13).drawStringShadow(Mideas.mouseX()+35-FontManager.get("FRIZQT", 13).getWidth(String.valueOf(draggedItem.getAmount())), Mideas.mouseY()+20, String.valueOf(draggedItem.getAmount()), Color.WHITE, Color.BLACK, 1, 1, 1);
			}
		}
		if(deleteItem && draggedItem != null) {
			Draw.drawQuad(Sprites.alert, Display.getWidth()/2-172*Mideas.getDisplayXFactor(), Display.getHeight()/2-80*Mideas.getDisplayYFactor(), Sprites.alert.getImageWidth()*Mideas.getDisplayXFactor(), Sprites.alert.getImageHeight()*Mideas.getDisplayYFactor());
			FontManager.get("FRIZQT", 16).drawStringShadow(Display.getWidth()/2-(FontManager.get("FRIZQT", 16).getWidth(draggedItem.getDeleteConfirm())*Mideas.getDisplayXFactor())/2, Display.getHeight()/2-72*Mideas.getDisplayYFactor(), draggedItem.getDeleteConfirm(), Color.WHITE, Color.BLACK, 1, Mideas.getDisplayXFactor(), Mideas.getDisplayXFactor());
			hoverDeleteYes.draw();
			hoverDeleteNo.draw();
		}
	}
	
	public static boolean mouseEvent() {
		if(!ContainerFrame.isHoverItemNumberFrame()) {
			if(Keyboard.isKeyDown(42) && !Mouse.getEventButtonState() && (Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1)) { //split item
				int i = 0;
				while(i < Mideas.joueur1().bag().getBag().length) {
					if(ContainerFrame.getContainerFrameSlotHover(i)) {
						if(Mideas.joueur1().bag().getBag(i) != null) {
							if(Mideas.joueur1().bag().getBag(i).isStackable()) {
								if(Mideas.joueur1().bag().getBag(i).getAmount() > 1) {
									ContainerFrame.setItemNumberOpen(i);
									leftClickBagDown = false;
									bagClickedSlot = -1;
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
			else { //if(!Mouse.getEventButtonState())
				if(Mouse.getEventButton() == 0) {
					int i = 0;
					if(draggedItem != null) {
						if(draggedItem != null && !deleteItem && !isSpellBarHover() && !isHoverCharacterFrame() && !isHoverBagFrame() && !isHoverSocketingFrame() && !isHoverTradeFrame()) {
							deleteItem = true;
							return true;
						}
						if(Interface.getCharacterFrameStatus()) {
							if(isHoverCharacterFrame()) {				
								if(!checkCharacterHover() && !deleteItem) {
									if(checkCharacterItems(draggedItem)) {
										draggedItem = null;
										leftClickInventoryDown = false;
										inventoryClickedSlot = -1;
										//return true;
									}
								}
							}
						}
						if(isHoverBagFrame()) {
							if(!checkBagHover()) {
								while(i < Mideas.joueur1().bag().getBag().length) {
									if(clickBagItem(i)) {
										draggedItem = null;
										leftClickBagDown = false;
										inventoryClickedSlot = -1;
										return true;
									}
									i++;
								}
							}
						}
					}
					if(leftClickInventoryDown && draggedItem == null) {
						while(i < Mideas.joueur1().getStuff().length) {
							if(clickInventoryItem(i)) {
								leftClickInventoryDown = false;
								inventoryClickedSlot = -1;
								return true;
							}
							i++;
						}
					}
					if(leftClickBagDown && draggedItem == null && !checkBagHover()) {
						while(i < Mideas.joueur1().bag().getBag().length) {
							if(clickBagItem(i)) {
								leftClickBagDown = false;
								bagClickedSlot = -1;
								return true;
							}
							i++;
						}
					}
					if(Interface.getContainerFrameStatus()) {
						i = 0;
						if(isHoverBagFrame()) {
							while(i < Mideas.joueur1().bag().getBag().length) {
								if(clickBagItem(i)) {
									leftClickBagDown = false;
									bagClickedSlot = -1;
									return true;
								}
								i++;
							}
						}
					}
					if(Interface.getCharacterFrameStatus()) {
						//if(Mideas.mouseX() >= Display.getWidth()/2-300 && Mideas.mouseX() <= Display.getWidth()/2+135 && Mideas.mouseY() >= Display.getHeight()/2-380 && Mideas.mouseY() <= Display.getHeight()/2+146) {
							i = 0;
							while(i < Mideas.joueur1().getStuff().length) {
								if(clickInventoryItem(i)) {
									return true;
								}
								i++;
							}
						//}
					}
					leftClickInventoryDown = false;
					leftClickBagDown = false;
					bagClickedSlot = -1;
					inventoryClickedSlot = -1;
				}
			}
			if(draggedItem == null && Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseX)) >= 15 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseY)) >= 15) {
				if(leftClickBagDown) {
					setDraggedItemForBag();
					bagClickedSlot = -1;
				}
				if(leftClickInventoryDown) {
					setDraggedItemForCharacter();
					inventoryClickedSlot = -1;
				}
				if(rightClickBagDown || rightClickInventoryDown) {
					inventoryClickedSlot = -1;
					bagClickedSlot = -1;
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
					while(i < Mideas.joueur1().bag().getBag().length) {
						if(Mideas.joueur1().bag().getBag(i) != null) {
							if(ContainerFrame.getContainerFrameSlotHover(i) && bagClickedSlot == i) {
								if(Mideas.joueur1().bag().getBag(i).isPotion()) {
									doHealingPotion((Potion)Mideas.joueur1().bag().getBag(i), ContainerFrame.getContainerFrameSlotHover(i), i);
									bagClickedSlot = -1;
									return true;
								}
								else if(Mideas.joueur1().bag().getBag(i).isStuff() || Mideas.joueur1().bag().getBag(i).isWeapon()) {
									equipBagItem(i);
									bagClickedSlot = -1;
									return true;
								}
							}
						}
						i++;
					}
					bagClickedSlot = -1;
				}
				else {
					if(checkBagClick() && draggedItem == null) {
						return true;
					}
					if(draggedItem != null) {
						draggedItem.setIsSelectable(true);
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
	
	public static void calcStats(Item stuff) {
		if(stuff != null && (stuff.isStuff() || stuff.isWeapon())) {
			CharacterStuff.setEquippedItems();
			Mideas.joueur1().setStuffArmor(((Stuff)stuff).getArmor());
			Mideas.joueur1().setStuffStamina(((Stuff)stuff).getStamina());
			Mideas.joueur1().setStuffStrength(((Stuff)stuff).getStrength());
			Mideas.joueur1().setStuffMana(((Stuff)stuff).getMana());
			Mideas.joueur1().setStuffCritical(((Stuff)stuff).getCritical());
		}
	}
	
	public static void calcStatsLess(Item stuff) {
		if(stuff != null && (stuff.isStuff() || stuff.isWeapon())) {
			Mideas.joueur1().setStuffArmor(-((Stuff)stuff).getArmor());
			Mideas.joueur1().setStuffStamina(-((Stuff)stuff).getStamina());
			Mideas.joueur1().setStuffStrength(-((Stuff)stuff).getStrength());
			Mideas.joueur1().setStuffMana(-((Stuff)stuff).getMana());
			Mideas.joueur1().setStuffCritical(-((Stuff)stuff).getCritical());
		}
	}
	
	private static boolean equipBagItem(int i) {
		if(ContainerFrame.getContainerFrameSlotHover(i)) {
			if(Mideas.joueur1().bag().getBag(i).isStuff()) {
				int j = 0;
				while(j < type.length) {
					if(((Stuff)Mideas.joueur1().bag().getBag(i)).getType() == type[j] && ((Stuff)Mideas.joueur1().bag().getBag(i)).canEquipTo(Mideas.joueur1().getClassType()) && Mideas.joueur1().canWear((Stuff)Mideas.joueur1().bag().getBag(i))) {
						if(Mideas.joueur1().getLevel() >= ((Stuff)Mideas.joueur1().bag().getBag(i)).getLevel()) {
							if(Mideas.joueur1().getStuff(j) == null) {
								Mideas.joueur1().setStuff(j, Mideas.joueur1().bag().getBag(i));
								calcStats(Mideas.joueur1().getStuff(j));
								setNullContainer(Mideas.joueur1().bag().getBag(i));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								Mideas.joueur1().bag().setBagChange(true);
								return true;
							}
							Item tempItem = Mideas.joueur1().bag().getBag(i);
							Mideas.joueur1().bag().setBag(i, Mideas.joueur1().getStuff(j));
							calcStatsLess(Mideas.joueur1().getStuff(j));
							Mideas.joueur1().setStuff(j, tempItem);
							calcStats(Mideas.joueur1().getStuff(j));
							CharacterStuff.setBagItems();
							CharacterStuff.setEquippedItems();
							return true;
						}
					}
					j++;
				}
			}
			else if(Mideas.joueur1().bag().getBag(i).isWeapon()) {
				int j = 0;
				while(j < weaponSlot.length) {
					if(((Stuff)Mideas.joueur1().bag().getBag(i)) != null && ((Stuff)Mideas.joueur1().bag().getBag(i)).getWeaponSlot() == weaponSlot[j] && ((Stuff)Mideas.joueur1().bag().getBag(i)).canEquipTo(Mideas.joueur1().getClassType())) {
						if(Mideas.joueur1().getLevel() >= ((Stuff)Mideas.joueur1().bag().getBag(i)).getLevel()) {
							if(Mideas.joueur1().getStuff(j) == null) {
								Mideas.joueur1().setStuff(j, Mideas.joueur1().bag().getBag(i));
								calcStats(Mideas.joueur1().getStuff(j));
								setNullContainer(Mideas.joueur1().bag().getBag(i));
								CharacterStuff.setBagItems();
								CharacterStuff.setEquippedItems();
								Mideas.joueur1().bag().setBagChange(true);
								return true;
							}
							Item tempItem = Mideas.joueur1().bag().getBag(i);
							Mideas.joueur1().bag().setBag(i, Mideas.joueur1().getStuff(j));
							calcStatsLess(Mideas.joueur1().getStuff(j));
							Mideas.joueur1().setStuff(j, tempItem);
							calcStats(Mideas.joueur1().getStuff(j));
							CharacterStuff.setBagItems();
							CharacterStuff.setEquippedItems();
							return true;
						}
					}
					j++;
				}
			}
		}
		return false;
	}
	
	private static void doHealingPotion(Potion item, boolean hover, int i) {
		if(hover && item != null && item.isPotion() && Mideas.joueur1().getLevel() >= item.getLevel()) {
			if(Mideas.joueur1().getStamina()+item.getPotionHeal() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous êtes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
				item.setAmount(item.getAmount()-1);
				Mideas.joueur1().bag().setBagChange(true);
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+item.getPotionHeal());
				LogChat.setStatusText3("Vous vous êtes rendu "+item.getPotionHeal()+" hp");
				item.setAmount(item.getAmount()-1);
				Mideas.joueur1().bag().setBagChange(true);
			}
			else {
				LogChat.setStatusText3("Vos HP étaient déjà au maximum");
			}
			if(item.getAmount() <= 0) {
				Mideas.joueur1().bag().setBag(i, null);
				Mideas.joueur1().bag().setBagChange(true);
			}
			CharacterStuff.setBagItems();
		}
	}
	
	
	private static void equipItem(int i) {
		if(Mideas.joueur1().bag().getBag(i).isStuff() && draggedItem.isStuff()) {
			if(Mideas.joueur1().getLevel() >= ((Stuff)Mideas.joueur1().bag().getBag(i)).getLevel() && ((Stuff)Mideas.joueur1().bag().getBag(i)).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && ((Stuff)Mideas.joueur1().bag().getBag(i)).getType() == ((Stuff)draggedItem).getType()) {
				Stuff temp = (Stuff)Mideas.joueur1().bag().getBag(i);
				Mideas.joueur1().bag().setBag(i, draggedItem);
				calcStatsLess(draggedItem);
				Mideas.joueur1().setStuff(i, temp);
				calcStats(temp);
			}
		}
	}
	
	private static boolean clickBagItem(int i) {
		if(ContainerFrame.getContainerFrameSlotHover(i) && !DragSpellManager.isHoverSpellBarFrame()) {
			if(draggedItem == null) {
				if(Mideas.joueur1().bag().getBag(i) == null) {
					return true;
				}
				else if(Mideas.joueur1().bag().getBag(i).isSelectable()) {
					draggedItem = Mideas.joueur1().bag().getBag(i);
					return true;
				}
			}
			else if(checkCharacterItems(draggedItem)) {        
				if(Mideas.joueur1().bag().getBag(i) == null) {
					calcStatsLess(draggedItem);
					setNullCharacter(draggedItem);
					Mideas.joueur1().bag().setBag(i, draggedItem);
					Mideas.joueur1().bag().setBagChange(true);
					draggedItem = null;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				equipItem(i);
				return true;
			}
			else if(checkBagItems(draggedItem)) {
				if(Mideas.joueur1().bag().getBag(i) == null) {
					setNullContainer(draggedItem);
					Mideas.joueur1().bag().setBag(i, draggedItem);
					Mideas.joueur1().bag().getBag(i).setIsSelectable(true);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
				else if(draggedItem.isPotion() && draggedItem.getId() == Mideas.joueur1().bag().getBag(i).getId()) {
					if(draggedItem != Mideas.joueur1().bag().getBag(i)) {
						int number = draggedItem.getAmount()+Mideas.joueur1().bag().getBag(i).getAmount();
						setNullContainer(draggedItem);
						Mideas.joueur1().bag().getBag(i).setAmount(number);
						draggedItem = null;
						CharacterStuff.setBagItems();
						return true;
					}
					draggedItem = null;
					return true;
				}
				else if(draggedItem == Mideas.joueur1().bag().getBag(i)) {
					Mideas.joueur1().bag().getBag(i).setIsSelectable(true);
					draggedItem = null;
					return true;
				}
				else {
					Item tempItem = Mideas.joueur1().bag().getBag(i);
					Mideas.joueur1().bag().setBag(checkItemSlot(draggedItem), tempItem);
					Mideas.joueur1().bag().setBag(i, draggedItem);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
			}
			else {
				if(Mideas.joueur1().bag().getBag(i) == null) {
					Mideas.joueur1().bag().setBag(i, draggedItem);
					Mideas.joueur1().bag().setBagChange(true);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
				if(draggedItem.isPotion() && draggedItem.getId() == Mideas.joueur1().bag().getBag(i).getId()) {
					if(draggedItem != Mideas.joueur1().bag().getBag(i)) {
						int number = draggedItem.getAmount()+Mideas.joueur1().bag().getBag(i).getAmount();
						setNullContainer(draggedItem);
						Mideas.joueur1().bag().getBag(i).setAmount(number);
						draggedItem = null;
						CharacterStuff.setBagItems();
						return true;
					}
					draggedItem = null;
					return true;
				}
				if(draggedItemSplit) {
					if(draggedItem.getId() != Mideas.joueur1().bag().getBag(i).getId()) {
						Mideas.joueur1().bag().getBag(ContainerFrame.getLastSplit()).setAmount(Mideas.joueur1().bag().getBag(ContainerFrame.getLastSplit()).getAmount()+draggedItem.getAmount());
						draggedItem = null;
						CharacterStuff.setBagItems();
						draggedItemSplit = false;
						return true;
					}
				}
				else {
					Item tempItem = Mideas.joueur1().bag().getBag(i);
					Mideas.joueur1().bag().setBag(i, draggedItem);
					draggedItem = tempItem;
					CharacterStuff.setBagItems();
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean clickInventoryItem(int i) {
		if(CharacterFrame.getHoverCharacterFrame(i)) {
			if(draggedItem == null) {
				if(Mideas.joueur1().getStuff(i) == null) {
					return true;
				}
				else if(Mideas.joueur1().getStuff(i).isSelectable()) {
					draggedItem = Mideas.joueur1().getStuff(i);
					return true;
				}
			}
			else if(checkCharacterItems(draggedItem)) {
				if(draggedItem.isStuff()) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && Mideas.joueur1().getLevel() >= ((Stuff)draggedItem).getLevel() && Mideas.joueur1().canWear((Stuff)draggedItem)) {        //draggeditem same stuff type of slot hover
						if(Mideas.joueur1().getStuff(i) == null) {
							setNullCharacter(draggedItem);
							calcStatsLess(draggedItem);
							Mideas.joueur1().setStuff(i, draggedItem);
							calcStats(draggedItem);
							CharacterStuff.setEquippedItems();
							CharacterStuff.setBagItems();
							return true;
						}
						else if(Mideas.joueur1().getStuff(i).isSelectable()) {
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
				else if(draggedItem.isWeapon()) {
					if((((Stuff)draggedItem).getWeaponSlot() == weaponSlot[i]) && ((Stuff)draggedItem).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && Mideas.joueur1().getLevel() >= ((Stuff)draggedItem).getLevel() && ((Stuff)draggedItem).canWearWeapon()) {
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
				if(draggedItem.isStuff()) {
					if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && Mideas.joueur1().getLevel() >= ((Stuff)draggedItem).getLevel() && Mideas.joueur1().canWear((Stuff)draggedItem)) {
						if(Mideas.joueur1().getStuff(i) == null) {
							Mideas.joueur1().setStuff(i, draggedItem);
							calcStats(draggedItem);
							setNullContainer(draggedItem);
							draggedItem = null;
							CharacterStuff.setEquippedItems();
							CharacterStuff.setBagItems();
							return true;
						}
						else if(Mideas.joueur1().getStuff(i).isSelectable()) {
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
				else if(draggedItem.isWeapon()) {
					if((((Stuff)draggedItem).getWeaponSlot() == weaponSlot[i]) && ((Stuff)draggedItem).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && Mideas.joueur1().getLevel() >= ((Stuff)draggedItem).getLevel() && ((Stuff)draggedItem).canWearWeapon()) {
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
			else if(draggedItem.isStuff()){
				if(((Stuff)draggedItem).getType() == type[i] && ((Stuff)draggedItem).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && Mideas.joueur1().getLevel() >= ((Stuff)draggedItem).getLevel() && Mideas.joueur1().canWear((Stuff)draggedItem)) {
					if(Mideas.joueur1().getStuff(i) == null) {
						Mideas.joueur1().setStuff(i, draggedItem);
						calcStats(draggedItem);
						draggedItem = null;
						CharacterStuff.setEquippedItems();
						CharacterStuff.setBagItems();
						return true;
					}
					Item tempItem = Mideas.joueur1().getStuff(i);
					calcStatsLess(Mideas.joueur1().getStuff(i));
					Mideas.joueur1().setStuff(i, draggedItem);
					calcStats(draggedItem);
					draggedItem = tempItem;
					CharacterStuff.setEquippedItems();
					CharacterStuff.setBagItems();
					return true;
				}
				draggedItem = null;
			}
		}
		return false;
	}
	
	private static boolean setDraggedItemForCharacter() {
		if(inventoryClickedSlot >= 0 && inventoryClickedSlot < Mideas.joueur1().getStuff().length) {
			int i = 0;
			while(i < Mideas.joueur1().getStuff().length) {
				if(inventoryClickedSlot == i && Mideas.joueur1().getStuff(i) != null && Mideas.joueur1().getStuff(i).isSelectable()) {
					draggedItem = Mideas.joueur1().getStuff(i);
					return true;
				}
				i++;
			}
		}
		return false;
	}
	
	private static boolean setDraggedItemForBag() {
		if(bagClickedSlot >= 0 && bagClickedSlot < Mideas.joueur1().bag().getBag().length) {
			int i = 0;
			while(i < Mideas.joueur1().bag().getBag().length) {
				if(bagClickedSlot == i && Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).isSelectable()) {
					draggedItem = Mideas.joueur1().bag().getBag(i);
					return true;
				}
				i++;
			}
		}
		return false;
	}
	
	private static boolean checkBagClick() {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(ContainerFrame.getContainerFrameSlotHover(i) == true && draggedItem == null) {
				bagClickedSlot = i;
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean checkInventoryClick() {
		int i = 0 ;
		while(i < Mideas.joueur1().getStuff().length) {
			if(draggedItem == null && CharacterFrame.getHoverCharacterFrame(i) == true) {
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
				break;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) != null && draggedItem2 == Mideas.joueur1().bag().getBag(i)) {
				Mideas.joueur1().bag().setBag(i, null);
				Mideas.joueur1().bag().setBagChange(true);
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
	
	public static boolean deleteBagItem(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == item) {
				Mideas.joueur1().bag().setBag(i, null);
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
	
	public static Item getBagItem(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == item) {
				return Mideas.joueur1().bag().getBag(i);
			}
			i++;
		}
		return null;
	}
	
	public static boolean deleteItem(int id) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).getId() == id) {
				Mideas.joueur1().bag().setBag(i, null);
				Mideas.joueur1().bag().setBagChange(true);
				return true;
			}
			i++;
		}
		return false;
	}
	
	static boolean checkBagItems(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == item) {
				return true;
			}
			i++;
		}
		return false;
	}
	public static boolean setNullContainer(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(item == Mideas.joueur1().bag().getBag(i)) {
				Mideas.joueur1().bag().setBag(i, null);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean checkFreeSlotBag(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == null) {
				Mideas.joueur1().bag().setBag(i, item);
				Mideas.joueur1().bag().setBagChange(true);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean checkFreeSlotBag(Potion potion) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) != null && Mideas.joueur1().bag().getBag(i).getId() == potion.getId()) {
				Mideas.joueur1().bag().getBag(i).setAmount(Mideas.joueur1().bag().getBag(i).getAmount()+1);
				return true;
			}
			i++;
		}
		i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == null) {
				Mideas.joueur1().bag().setBag(i, potion, potion.getAmount());
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static boolean isSpellBarHover() {
		int i = 0;
		while(i < Mideas.joueur1().getSpells().length) {
			if(SpellBarFrame.getHoverSpellBar(i)) {
				return true;
			}
			i++;
		}
		return false;
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
			if(Mideas.mouseX() >= Display.getWidth()/2-300+CharacterFrame.getMouseX()+CharacterFrame.getGemFrameSize() && Mideas.mouseX() <= Display.getWidth()/2-300+CharacterFrame.getGemFrameSize()+Sprites.character_frame.getImageWidth()+CharacterFrame.getMouseX() && Mideas.mouseY() >= Display.getHeight()/2-380+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2-380+Sprites.character_frame.getImageHeight()+CharacterFrame.getMouseY()) {
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
	
	public static boolean isHoverSocketingFrame() {
		if(Interface.isSocketingFrameActive()) {
			if(Mideas.mouseX() >= Display.getWidth()/2-300+CharacterFrame.getMouseX() && Mideas.mouseX() <= Display.getWidth()/2-300+CharacterFrame.getMouseX()+Sprites.socketing_frame.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2-380+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2-380+CharacterFrame.getMouseY()+Sprites.socketing_frame.getImageHeight()*Mideas.getDisplayXFactor()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoverTradeFrame() {
		if(Interface.isTradeFrameActive()) {
			if(Mideas.mouseX() >= TradeFrame.getXFrame() && Mideas.mouseX() <= TradeFrame.getXFrame()+Sprites.trade_frame.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= TradeFrame.getYFrame() && Mideas.mouseY() <= TradeFrame.getYFrame()+Sprites.trade_frame.getImageHeight()*Mideas.getDisplayXFactor()) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkBagHover() {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(bagClickedSlot == i && true == ContainerFrame.getContainerFrameSlotHover(i)) {
				return true;
			}
			i++;
		}
		return false;
	}
	private static boolean checkCharacterHover() {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(inventoryClickedSlot == i && CharacterFrame.getHoverCharacterFrame(i) == true) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public static int checkItemSlot(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == item) {
				return i;
			}
			i++;
		}
		return 0;
	}
	
	public static boolean getClickBag(int i) {
		return bagClickedSlot == i;
	}
	
	public static boolean getClickInventory(int i) {
		return inventoryClickedSlot == i;
	}
	
	public static void setDraggedItemSplit(boolean we) {
		draggedItemSplit = we;
	}
	
	public static WeaponSlot getWeaponSlot(int i) {
		if(i < weaponSlot.length) {
			return weaponSlot[i];
		}
		return null;
	}
	
	public static void resetInventoryClickedSlot() {
		inventoryClickedSlot = -1;
	}
	
	public static void resetBagClickedSlot() {
		bagClickedSlot = -1;
	}
	
	public static void updateSize() {
		hoverDeleteYes.setX(Display.getWidth()/2-130*Mideas.getDisplayXFactor());
		hoverDeleteYes.setY(Display.getHeight()/2-43*Mideas.getDisplayYFactor());
		hoverDeleteYes.setButtonWidth(127*Mideas.getDisplayXFactor());
		hoverDeleteYes.setButtonHeight(23*Mideas.getDisplayYFactor());
		hoverDeleteNo.setX(Display.getWidth()/2+7*Mideas.getDisplayXFactor());
		hoverDeleteNo.setY(Display.getHeight()/2-43*Mideas.getDisplayYFactor());
		hoverDeleteNo.setButtonWidth(127*Mideas.getDisplayXFactor());
		hoverDeleteNo.setButtonHeight(23*Mideas.getDisplayYFactor());
	}
}
