package com.mideas.rpg.v2.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Interface;
import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.command.item.CommandDragItems;
import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.game.CharacterStuff;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.PopupType;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.StuffType;
import com.mideas.rpg.v2.game.item.weapon.WeaponSlot;
import com.mideas.rpg.v2.game.redalert.DefaultRedAlert;
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
	private final static int PICK_UP_ITEM_RANGE = 15;
	private static boolean leftClickInventoryDown;
	private static boolean leftClickBagDown;
	private static boolean rightClickInventoryDown;
	private static boolean rightClickBagDown;
	private static int mouseXDown;
	private static int mouseYDown;
	static Item draggedItem;
	private static int bagLeftClickedSlot = -1;
	private static int bagRightClickedSlot = -1;
	private static int inventoryLeftClickedSlot = -1;
	private static int inventoryRightClickedSlot = -1;
	private static int mouseX;
	private static int mouseY;
	private static Button hoverDeleteYes = new Button(Display.getWidth()/2-130*Mideas.getDisplayXFactor(), Display.getHeight()/2-43*Mideas.getDisplayYFactor(), 127*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "Yes", 14, 1) {
		@Override
		public void eventButtonClick() {
			if(draggedItem.isStackable()) {
				Mideas.joueur1().deleteItem(draggedItem, draggedItem.getAmount());
			}
			else {
				deleteItem(draggedItem);
			}
			if(checkCharacterItems(draggedItem)) {
				calcStatsLess(draggedItem);
			}
			draggedItem = null;
			CharacterStuff.setEquippedItems();
			CharacterStuff.setBagItems();
			this.setHoverFalse();
			deleteItem = false;
		}
	};

	private static Button hoverDeleteNo = new Button(Display.getWidth()/2+7*Mideas.getDisplayXFactor(), Display.getHeight()/2-43*Mideas.getDisplayYFactor(), 127*Mideas.getDisplayXFactor(), 23*Mideas.getDisplayYFactor(), "No", 14, 1) {
		@Override
		public void eventButtonClick() {
			if(!checkBagItems(draggedItem) && !checkCharacterItems(draggedItem)) {
				checkFreeSlotBag(draggedItem);
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
			Draw.drawQuad(IconsManager.getSprite37((draggedItem.getSpriteId())), Mideas.mouseX(), Mideas.mouseY());
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
	
	private static boolean bagLeftClickDown() {
		if(ContainerFrame.getContainerFrameSlotHover() == -1) {
			return false;
		}
		bagLeftClickedSlot = ContainerFrame.getContainerFrameSlotHover();
		leftClickBagDown = true;
		mouseXDown = Mideas.mouseX();
		mouseYDown = Mideas.mouseY();
		return false;
	}
	
	private static boolean bagRightClickDown() {
		if(ContainerFrame.getContainerFrameSlotHover() == -1) {
			return false;
		}
		bagRightClickedSlot = ContainerFrame.getContainerFrameSlotHover();
		rightClickBagDown = true;
		return false;
	}
	
	private static boolean bagRightClickUp() {
		if(bagRightClickedSlot == ContainerFrame.getContainerFrameSlotHover() && Mideas.joueur1().bag().getBag(ContainerFrame.getContainerFrameSlotHover()) != null && Mideas.joueur1().bag().getBag(ContainerFrame.getContainerFrameSlotHover()).isStackable() && Mideas.joueur1().bag().getBag(ContainerFrame.getContainerFrameSlotHover()).getAmount() > 1 && Keyboard.isKeyDown(42)) {
			ContainerFrame.setItemNumberOpen(ContainerFrame.getContainerFrameSlotHover());
			bagRightClickedSlot = -1;
			rightClickBagDown = false;
			return true;
		}
		bagRightClickedSlot = -1;
		rightClickBagDown = false;
		return false;
	}
	
	private static boolean bagLeftClickUp() {
		bagLeftClickedSlot = -1;
		inventoryLeftClickedSlot = -1;
		leftClickBagDown = false;
		leftClickInventoryDown = false;
		if(ContainerFrame.getContainerFrameSlotHover() == -1) {
			return false;
		}
		if(DragSpellManager.getDraggedSpell() != null) {
			DragSpellManager.setDraggedSpell(null);
			return false;
		}
		Item bagItem = Mideas.joueur1().bag().getBag(ContainerFrame.getContainerFrameSlotHover());
		if(bagItem == null) {
			if(draggedItem == null) {
				return false;
			}
			/*deleteItem(draggedItem);
			Mideas.joueur1().bag().setBag(ContainerFrame.getContainerFrameSlotHover(), draggedItem);*/
			draggedItem.setIsSelectable(false);
			CommandDragItems.write(DragItem.BAG, getBagSlot(draggedItem), DragItem.BAG, ContainerFrame.getContainerFrameSlotHover());
			draggedItem = null;
			return true;
		}
		if(!bagItem.isSelectable()) {
			draggedItem = null;
			return false;
		}
		if(draggedItem == null) {
			draggedItem = bagItem;
			return true;
		}
		if(checkCharacterItems(draggedItem)) {
			if(!bagItem.isStuff() && !bagItem.isWeapon()) {
				RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
				return false;
			}
			if(!StuffManager.canEquipStuff((Stuff)bagItem)) {
				RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
				return false;
			}
			/*Stuff tmp = (Stuff)bagItem;
			Mideas.joueur1().bag().setBag(ContainerFrame.getContainerFrameSlotHover(), draggedItem);
			Mideas.joueur1().setStuff(((Stuff)draggedItem).getType().getSlot(), tmp);*/
			CommandDragItems.write(DragItem.INVENTORY, getInventorySlot(draggedItem), DragItem.BAG, ContainerFrame.getContainerFrameSlotHover());
			draggedItem.setIsSelectable(false);
			bagItem.setIsSelectable(false);
			draggedItem = null;
			return true;
		}
		if(checkBagItems(draggedItem)) {
			if(draggedItem == bagItem) {
				draggedItem = null;
				return true;
			}
			int slot = checkItemSlotBag(draggedItem);
			if(slot == -1) {
				return false;
			}
			Item tmp = bagItem;
			if(draggedItem.getId() == bagItem.getId() && draggedItem.isStackable() && bagItem.isStackable()) {
				if(draggedItem.getAmount()+bagItem.getAmount() > draggedItem.getMaxStack()) {
					int amount = bagItem.getAmount();
					bagItem.setAmount(bagItem.getMaxStack());
					draggedItem.setAmount(bagItem.getMaxStack()-amount-draggedItem.getAmount());
					draggedItem = null;
					return true;
				}
				bagItem.setAmount(draggedItem.getAmount()+bagItem.getAmount());
				deleteItem(draggedItem);
				draggedItem = null;
				return true;
			}
			Mideas.joueur1().bag().setBag(ContainerFrame.getContainerFrameSlotHover(), draggedItem);
			Mideas.joueur1().bag().setBag(slot, tmp);
			draggedItem = null;
		}
		return false;
	}
	
	private static boolean mouseMove() {
		if((ContainerFrame.getContainerFrameSlotHover() == -1 && bagLeftClickedSlot == -1) && (CharacterFrame.getSlotHover() == -1 && inventoryLeftClickedSlot == -1)) {
			return false;
		}
		if(!leftClickBagDown && !leftClickInventoryDown) {
			return false;
		}
		if(bagLeftClickedSlot != -1 && inventoryLeftClickedSlot != -1) {
			return false;
		}
		if(!(Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseXDown)) >= PICK_UP_ITEM_RANGE || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseYDown)) >= PICK_UP_ITEM_RANGE)) {
			return false;
		}
		if(bagLeftClickedSlot != -1 && leftClickBagDown) {
			final Item bagItem = Mideas.joueur1().bag().getBag(bagLeftClickedSlot);
			int bagClickedSlot = DragManager.bagLeftClickedSlot;
			DragManager.bagLeftClickedSlot = -1;
			if(bagItem == null) {
				if(DragSpellManager.getDraggedSpell() != null)  {
					return false;
				}
				deleteItem(draggedItem);
				Mideas.joueur1().bag().setBag(bagClickedSlot, draggedItem);
				draggedItem = null;
				return true;
			}
			if(!bagItem.isSelectable()) {
				if(bagItem == draggedItem) {
					draggedItem = null;
					return true;
				}
				return false;
			}
			if(draggedItem != null) {
				if(checkBagItems(draggedItem)) {
					Item tmp = bagItem;
					int slot = checkItemSlotBag(draggedItem);
					if(slot == -1) {
						return false;
					}
					Mideas.joueur1().bag().setBag(bagClickedSlot, draggedItem);
					Mideas.joueur1().bag().setBag(slot, tmp);
					draggedItem = null;
					return true;
				}
				if(checkCharacterItems(draggedItem)) {
					if(!bagItem.isStuff()) {
						draggedItem = null;
						RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
						return false;
					}
					draggedItem = null;
					if(((Stuff)draggedItem).getType() != ((Stuff)bagItem).getType()) {
						RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
						return false;
					}
					if(!StuffManager.canEquipStuff((Stuff)bagItem)) {
						draggedItem = null;
						RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
						return false;
					}
					int slot = checkItemSlotInventory(draggedItem);
					if(slot == -1) {
						System.out.println("Error in DragManager mouseMove in Bag for item slot inventory of draggedItem");
						return false;
					}
					Stuff tmp = (Stuff)bagItem;
					Mideas.joueur1().bag().setBag(bagClickedSlot, draggedItem);
					Mideas.joueur1().setStuff(slot, tmp);
					return true;
				}
				draggedItem = bagItem;
				return false;
			}
			if(draggedItem == null) {
				DragSpellManager.setDraggedSpell(null);
				draggedItem = bagItem;
				return true;
			}
			System.out.println("Error in DragManager mouseMove in Bag");
			return false;
		}
		if(inventoryLeftClickedSlot != -1 && leftClickInventoryDown) {
			int inventoryLeftClickedSlot = DragManager.inventoryLeftClickedSlot;
			DragManager.inventoryLeftClickedSlot = -1;
			final Stuff stuff = Mideas.joueur1().getStuff(inventoryLeftClickedSlot);
			if(draggedItem == null && DragSpellManager.getDraggedSpell() == null) {
				if(stuff == null) {
					return false;
				}
				draggedItem = stuff;
				return true;
			}
			if(draggedItem != null) {
				if(checkBagItems(draggedItem)) {
					if(!draggedItem.isStuff() && !draggedItem.isWeapon()) {
						draggedItem = null;
						RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
						return false;
					}
					int slot = checkItemSlotBag(draggedItem);
					if(slot == -1) {
						return false;
					}
					Stuff toBag = Mideas.joueur1().getStuff(((Stuff)draggedItem).getType().getSlot());
					Mideas.joueur1().setStuff(((Stuff)draggedItem).getType().getSlot(), draggedItem);
					Mideas.joueur1().bag().setBag(slot, toBag);
					draggedItem = null;
					return true;
				}
				if(checkCharacterItems(draggedItem)) {
					if(Mideas.joueur1().getStuff(inventoryLeftClickedSlot) == null) {
						if(CharacterFrame.getStuffType(inventoryLeftClickedSlot) != ((Stuff)draggedItem).getType()) {
							draggedItem = null;
							return false;
						}
						deleteItem(draggedItem);
						Mideas.joueur1().setStuff(inventoryLeftClickedSlot, draggedItem);
						draggedItem = null;
						return true;
					}
					draggedItem = null;
					return false;
				}
			}
		}
		return false;
	}
	
	private static boolean characterLeftClickDown() {
		if(CharacterFrame.getSlotHover() == -1) {
			return false;
		}
		inventoryLeftClickedSlot = CharacterFrame.getSlotHover();
		leftClickInventoryDown = true;
		mouseXDown = Mideas.mouseX();
		mouseYDown = Mideas.mouseY();
		return false;
	}
	
	private static boolean characterLeftClickUp() {
		leftClickInventoryDown = false;
		leftClickBagDown = false;
		bagLeftClickedSlot = -1;
		if(inventoryLeftClickedSlot != CharacterFrame.getSlotHover()) {
			if(checkBagItems(draggedItem)) {
				if(!draggedItem.isStuff() && !draggedItem.isWeapon()) {
					draggedItem = null;
					RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
					return false;
				}
				int bagSlot = checkItemSlotBag(draggedItem);
				Stuff tmp = Mideas.joueur1().getStuff(((Stuff)draggedItem).getType().getSlot());
				Mideas.joueur1().setStuff(((Stuff)draggedItem).getType().getSlot(), draggedItem);
				Mideas.joueur1().bag().setBag(bagSlot, tmp);
				draggedItem = null;
			}
			inventoryLeftClickedSlot = -1;
			return false;
		}
		inventoryLeftClickedSlot = -1;
		if(draggedItem == null && CharacterFrame.getSlotHover() != -1) {
			draggedItem = Mideas.joueur1().getStuff(CharacterFrame.getSlotHover());
			return true;
		}
		if(checkBagItems(draggedItem)) {
			if(!draggedItem.isStuff() && !draggedItem.isWeapon()) {
				draggedItem = null;
				RedAlertFrame.addNewAlert(DefaultRedAlert.CANNOT_EQUIP_ITEM);
				return false;
			}
			int bagSlot = checkItemSlotBag(draggedItem);
			Stuff tmp = Mideas.joueur1().getStuff(((Stuff)draggedItem).getType().getSlot());
			Mideas.joueur1().setStuff(((Stuff)draggedItem).getType().getSlot(), draggedItem);
			Mideas.joueur1().bag().setBag(bagSlot, tmp);
			draggedItem = null;
		}
		if(checkCharacterItems(draggedItem)) {
			
		}
		return false;
	}
	
	private static boolean characterRightClickDown() {
		if(CharacterFrame.getSlotHover() == -1) {
			return false;
		}
		inventoryRightClickedSlot = CharacterFrame.getSlotHover();
		rightClickInventoryDown = true;
		return false;
	}
	
	private static boolean characterRightClickUp() {
		rightClickInventoryDown = false;
		inventoryRightClickedSlot = -1;
		return false;
	}
	
	public static boolean mouseEvent() {
		if(mouseX != Mideas.mouseX() || mouseY != Mideas.mouseY()) {
			mouseX = Mideas.mouseX();
			mouseY = Mideas.mouseY();
			mouseMove();
		}
		if(Mouse.getEventButtonState() && Mouse.getEventButton() == 1) {
			if(draggedItem != null || DragSpellManager.getDraggedSpell() != null) {
				draggedItem = null;
				DragSpellManager.setDraggedSpell(null);
				if(PopupFrame.getCurrentPopup() == PopupType.DELETE_ITEM) {
					PopupFrame.closePopup();
				}
				return true;
			}
		}
		if(ContainerFrame.isHoverItemNumberFrame()) {
			return false;
		}
		if(isHoverBagFrame()) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					return bagLeftClickDown();
				}
				else if(Mouse.getEventButton() == 1) {
					return bagRightClickDown();
				}
			}
			else {
				if(Mouse.getEventButton() == 0) {
					return bagLeftClickUp();
				}
				else if(Mouse.getEventButton() == 1) {
					return bagRightClickUp();
				}
			}
		}
		else if(isHoverCharacterFrame()) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					return characterLeftClickDown();
				}
				else if(Mouse.getEventButton() == 1) {
					return characterRightClickDown();
				}
			}
			else {
				if(Mouse.getEventButton() == 0) {
					return characterLeftClickUp();
				}
				else if(Mouse.getEventButton() == 1) {
					return characterRightClickUp();
				}
			}
		}
		if(!Mouse.getEventButtonState()) {
			if(Mouse.getEventButton() == 0) {
				if(draggedItem != null) {
					if(PopupFrame.getCurrentPopup() == PopupType.DELETE_ITEM) {
						PopupFrame.closePopup();
						return true;
					}
					PopupFrame.activateDeleteItemWithoutConfirm(draggedItem.getStuffName());
				}
				else if(DragSpellManager.getDraggedSpell() != null) {
					DragSpellManager.setDraggedSpell(null);
				}
			}
			if(Mouse.getEventButton() == 1) {
				rightClickBagDown = false;
				rightClickInventoryDown = false;
				bagRightClickedSlot = -1;
				inventoryRightClickedSlot = -1;
			}
		}
		/*if(!ContainerFrame.isHoverItemNumberFrame()) {
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
		}*/
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
	
	public static int getBagSlot(Item item) {
		if(item == null) {
			return -1;
		}
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == item) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static int getInventorySlot(Item item) {
		if(item == null) {
			return -1;
		}
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) == item) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/*private static boolean equipBagItem(int i) {
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
	}*/
	
	/*private static void doHealingPotion(Potion item, boolean hover, int i) {
		if(hover && item != null && item.isPotion() && Mideas.joueur1().getLevel() >= item.getLevel()) {
			if(Mideas.joueur1().getStamina()+item.getPotionHeal() >= Mideas.joueur1().getMaxStamina() && Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				LogChat.setStatusText3("Vous vous êtes rendu "+(Mideas.joueur1().getMaxStamina()-Mideas.joueur1().getStamina())+" hp");
				Mideas.joueur1().setStamina(Mideas.joueur1().getMaxStamina());
				item.setAmount(item.getAmount()-1);
			}
			else if(Mideas.joueur1().getStamina() != Mideas.joueur1().getMaxStamina()) {
				Mideas.joueur1().setStamina(Mideas.joueur1().getStamina()+item.getPotionHeal());
				LogChat.setStatusText3("Vous vous êtes rendu "+item.getPotionHeal()+" hp");
				item.setAmount(item.getAmount()-1);
			}
			else {
				LogChat.setStatusText3("Vos HP étaient déjà au maximum");
			}
			if(item.getAmount() <= 0) {
				Mideas.joueur1().bag().setBag(i, null);
			}
			CharacterStuff.setBagItems();
		}
	}*/
	
	
	/*private static void equipItem(int i) {
		if(Mideas.joueur1().bag().getBag(i).isStuff() && draggedItem.isStuff()) {
			if(Mideas.joueur1().getLevel() >= ((Stuff)Mideas.joueur1().bag().getBag(i)).getLevel() && ((Stuff)Mideas.joueur1().bag().getBag(i)).canEquipTo(Joueur.convStringToClassType(Mideas.joueur1().getClasseString())) && ((Stuff)Mideas.joueur1().bag().getBag(i)).getType() == ((Stuff)draggedItem).getType()) {
				Stuff temp = (Stuff)Mideas.joueur1().bag().getBag(i);
				Mideas.joueur1().bag().setBag(i, draggedItem);
				calcStatsLess(draggedItem);
				Mideas.joueur1().setStuff(i, temp);
				calcStats(temp);
			}
		}
	}*/
	
	/*private static boolean clickBagItem(int i) {
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
					Mideas.joueur1().bag().setBag(checkItemSlotBag(draggedItem), tempItem);
					Mideas.joueur1().bag().setBag(i, draggedItem);
					draggedItem = null;
					CharacterStuff.setBagItems();
					return true;
				}
			}
			else {
				if(Mideas.joueur1().bag().getBag(i) == null) {
					Mideas.joueur1().bag().setBag(i, draggedItem);
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
	}*/
	
	/*private static boolean clickInventoryItem(int i) {
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
	}*/
	
	/*private static boolean setDraggedItemForCharacter() {
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
	}*/
	
	/*private static boolean setDraggedItemForBag() {
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
	}*/
	
	/*private static boolean checkBagClick() {
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
	}*/
	
	/*private static boolean setNullCharacter(Item tempItem) {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(tempItem == Mideas.joueur1().getStuff(i)) {
				Mideas.joueur1().setStuff(i, null);
				return true;
			}
			i++;
		}
		return false;
	}*/

	static boolean deleteItem(Item draggedItem2) {
		int i = 0;
		while(i < Mideas.joueur1().getSpellsListSize()) {
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
				return true;
			}
			i++;
		}
		return false;
	}
	
	static boolean checkBagItems(Item item) {
		if(item == null) {
			return false;
		}
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
		return Mideas.mouseX() >= Display.getWidth()/2+(-Sprites.final_spellbar.getImageWidth()+120)*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()/2+(Sprites.final_spellbar.getImageWidth()+120)*Mideas.getDisplayXFactor() 
				&& Mideas.mouseY() >= Display.getHeight()-60;
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
			if(Mideas.mouseX() >= Display.getWidth()/2-300*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX()+CharacterFrame.getGemFrameSize() && Mideas.mouseX() <= Display.getWidth()/2-300*Mideas.getDisplayXFactor()+CharacterFrame.getGemFrameSize()+Sprites.character_frame.getImageWidth()*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX() && Mideas.mouseY() >= Display.getHeight()/2-380*Mideas.getDisplayYFactor()+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2-380*Mideas.getDisplayYFactor()+Sprites.character_frame.getImageHeight()*Mideas.getDisplayYFactor()+CharacterFrame.getMouseY()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoverBagFrame() {
		if(Interface.getContainerFrameStatus()) {
			if(Mideas.mouseX() >= Display.getWidth()-520*Mideas.getDisplayXFactor() && Mideas.mouseX() <= Display.getWidth()-320*Mideas.getDisplayXFactor()+Sprites.back_bag.getImageWidth()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoverSocketingFrame() {
		if(Interface.isSocketingFrameActive()) {
			if(Mideas.mouseX() >= Display.getWidth()/2-300*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX() && Mideas.mouseX() <= Display.getWidth()/2-300*Mideas.getDisplayXFactor()+CharacterFrame.getMouseX()+Sprites.socketing_frame.getImageWidth()*Mideas.getDisplayXFactor() && Mideas.mouseY() >= Display.getHeight()/2-380*Mideas.getDisplayYFactor()+CharacterFrame.getMouseY() && Mideas.mouseY() <= Display.getHeight()/2-380*Mideas.getDisplayYFactor()+CharacterFrame.getMouseY()+Sprites.socketing_frame.getImageHeight()*Mideas.getDisplayXFactor()) {
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
	
	/*private static boolean checkBagHover() {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(bagClickedSlot == i && true == ContainerFrame.getContainerFrameSlotHover(i)) {
				return true;
			}
			i++;
		}
		return false;
	}*/
	
	/*private static boolean checkCharacterHover() {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(inventoryLeftClickedSlot == i && CharacterFrame.getHoverCharacterFrame(i) == true) {
				return true;
			}
			i++;
		}
		return false;
	}*/
	
	public static int checkItemSlotBag(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().bag().getBag().length) {
			if(Mideas.joueur1().bag().getBag(i) == item) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static int checkItemSlotInventory(Item item) {
		int i = 0;
		while(i < Mideas.joueur1().getStuff().length) {
			if(Mideas.joueur1().getStuff(i) == item) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static boolean getClickBag(int i) {
		return bagLeftClickedSlot == i;
	}
	
	public static boolean getLeftClickInventory(int i) {
		return inventoryLeftClickedSlot == i;
	}
	
	public static boolean getRightClickInventory(int i) {
		return inventoryRightClickedSlot == i;
	}
	
	public static WeaponSlot getWeaponSlot(int i) {
		if(i < weaponSlot.length) {
			return weaponSlot[i];
		}
		return null;
	}
	
	public static void resetInventoryClickedSlot() {
		inventoryLeftClickedSlot = -1;
	}
	
	public static void resetBagClickedSlot() {
		bagLeftClickedSlot = -1;
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
