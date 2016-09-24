package com.mideas.rpg.v2.hud;

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
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.utils.Draw;

public class DragBagManager {

	private static boolean[] hoverBag = new boolean[5];
	private static boolean [] clickBag = new boolean[5];
	private static boolean leftClickDown;
	private static int mouseX;
	private static int mouseY;
	private static boolean deleteItem;
	private static boolean hoverDelete;
	private static boolean hoverSave;
	private static Item draggedBag;
	
	public static void draw() {
		hoverDelete = false;
		hoverSave = false;
		if(draggedBag != null) {
			Draw.drawQuad(IconsManager.getSprite42(draggedBag.getSpriteId()), Mideas.mouseX(), Mideas.mouseY());
			Draw.drawQuad(Sprites.stuff_border, Mideas.mouseX()-5, Mideas.mouseY()-5);
		}
		if(deleteItem && draggedBag != null) {
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
				draggedBag = null;
				deleteItem = false;
			}
			TTF2.font4.drawStringShadow(Display.getWidth()/2-TTF2.font4.getWidth("Voulez vous supprimer "+draggedBag.getStuffName())/2, Display.getHeight()/2-65, "Voulez vous supprimer "+draggedBag.getStuffName(), Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Oui")/2-69, Display.getHeight()/2-41, "Oui", Color.white, Color.black, 1, 1, 1);
			TTF2.buttonFont.drawStringShadow(Display.getWidth()/2-TTF2.buttonFont.getWidth("Non")/2+70, Display.getHeight()/2-40, "Non", Color.white, Color.black, 1, 1, 1);
		}
	}
	
	public static boolean mouseEvent() throws SQLException {
		//hover BagFrame
		if(Mouse.getEventButton() == 0) {
			if(Mouse.getEventButtonState()) {
				if(checkBagClick() && draggedBag == null) {
					leftClickDown = true;
					mouseX = Mideas.mouseX();
					mouseY = Mideas.mouseY();
				}
			}
			else {
				if(draggedBag != null) {
					if(draggedBag != null && !leftClickDown && !deleteItem && !DragManager.isSpellBarHover() && !DragManager.isHoverCharacterFrame() && !DragManager.isHoverBagFrame()) {
						deleteItem = true;
						return true;
					}
					int i = 0;
					while(i < Mideas.bag().getEquippedBag().length) {
						if(clickBag(i)) {
							return true;
						}
						i++;
					}
				}
				Arrays.fill(clickBag, false);
				leftClickDown = false;
			}
		}
		if(leftClickDown && draggedBag == null) {
			if(Math.abs(Math.abs(Mideas.mouseX())-Math.abs(mouseX)) >= 27 || Math.abs(Math.abs(Mideas.mouseY())-Math.abs(mouseY)) >= 27) {
				setDraggedItemForBag();
				Arrays.fill(clickBag, false);
				return true;
			}
		}
		if(Mouse.getEventButton() == 0) {
			if(!Mouse.getEventButtonState()) {
				if(hoverDelete && deleteItem) {
					if(checkEmptyBag()) {
						deleteBag(draggedBag);
						draggedBag = null;
						CharacterStuff.setEquippedBags();
						CharacterStuff.setBagItems();
						Mideas.bag().setBagChange(true);
						deleteItem = false;
						ContainerFrame.setBagchange(true);
					}
				}
				if(hoverSave && deleteItem) {
				//	if(!checkBagItems(draggedItem) && !checkCharacterItems(draggedItem)) {
				//		checkFreeSlotBag(draggedItem);
					//	SpellBarFrame.setBagChange(true);
					//}
					draggedBag = null;
					deleteItem = false;
				}
				if(deleteItem) {
					draggedBag = null;
				}
				deleteItem = false;
			}
		}
		if(Mouse.getEventButton() == 1) {
			if(Mouse.getEventButtonState()) {
				if(draggedBag != null) {
					draggedBag = null;
				}
			}
		}
		return false;
	}
	
	public static void openBag() {
		Arrays.fill(hoverBag, false);
		int i = 0;
		while(i < hoverBag.length) {
			bagHover(i, 491-48.2f*i);
			i++;
		}
		if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
			if(!Mouse.getEventButtonState()) {
				i = 0;
				if(Mideas.mouseX() >= Display.getWidth()/2+539 && Mideas.mouseX() <= Display.getWidth()/2+539+40 && Mideas.mouseY() >= Display.getHeight()-40 && Mideas.mouseY() <= Display.getHeight()-3) {
					ContainerFrame.setBagOpen(0, !ContainerFrame.getBagOpen(0));
				}
				while(i < hoverBag.length) {
					if(hoverBag[i]) {
						ContainerFrame.setBagOpen(i+1, !ContainerFrame.getBagOpen(i+1));
						break;
					}
					i++;
				}
			}
		}
	}
	
	private static void bagHover(int i, float x) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+37 && Mideas.mouseY() >= Display.getHeight()-40 && Mideas.mouseY() <= Display.getHeight()-3) {
			hoverBag[i] = true;
		}
	}
	
	private static boolean clickBag(int i) {
		if(hoverBag[i]) {
			if(Mideas.bag().getEquippedBag(i) != null) {
				
			}
		}
		return false;
	}

	private static boolean checkBagClick() {
		int i = 0 ;
		while(i < clickBag.length) {
			if(hoverBag[i] == true && draggedBag == null) {
				clickBag[i] = true;
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
				draggedBag = Mideas.bag().getEquippedBag(i);
				return true;
			}
			i++;
		}
		return false;
	}
	
	private static boolean checkEmptyBag() {
		int i = 0;
		int position = -1;
		while(i < Mideas.bag().getEquippedBag().length) {
			if(draggedBag == Mideas.bag().getEquippedBag(i)) {
				position = i;
				break;
			}
			i++;
		}
		int size = 0;
		i = 0;
		while(i < position) {
			size+= Mideas.bag().getEquippedBagSize(i);
			i++;
		}
		i = size+16;
		while(i < size+Mideas.bag().getEquippedBagSize(position)+16) {
			if(Mideas.bag().getBag(i) != null) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	private static void deleteBag(Item item) {
		int i = 0;
		while(i < Mideas.bag().getEquippedBag().length) {
			if(Mideas.bag().getEquippedBag(i) == item) {
				Mideas.bag().setEquippedBag(i, null);
				return;
			}
			i++;
		}
	}
	
	public static boolean getHoverBag(int i) {
		if(i < hoverBag.length) {
			return hoverBag[i];
		}
		return false;
	}
	
	public static boolean getClickBag(int i) {
		if(i < clickBag.length) {
			return clickBag[i];
		}
		return false;
	}
	
	public static Item getDraggedBag() {
		return draggedBag;
	}
}
