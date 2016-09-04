package com.mideas.rpg.v2.hud;

import java.util.Arrays;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import com.mideas.rpg.v2.utils.Texture;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.enumlist.GemBonusType;
import com.mideas.rpg.v2.enumlist.ItemType;
import com.mideas.rpg.v2.game.IconsManager;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.gem.GemColor;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.BagManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class ContainerFrame {
	
	private static boolean[] slot_hover = new boolean[97];
	private static boolean[] itemNumberOpen = new boolean[97];
	private static Color bgColor = new Color(0, 0, 0, .6f); 
	private static Color borderColor = Color.decode("#494D4B");
	private static int x;
	private static int xShift;
	private static int y;
	private static int yShift;
	private static boolean[] isBagOpen = new boolean[5];
	private static int[] bagSize = new int[5];
	private static boolean bagChange = true;
	private static boolean[] hoverButton = new boolean[5];
	private static boolean[] buttonDown = new boolean[5];
	private static boolean isOneButtonDown;
	private static int xItemNumber;
	private static int yItemNumber;
	private static int iItemNumber;
	private static boolean itemNumberOkButton;
	private static boolean itemNumberCancelButton;
	private static boolean itemNumberLeftArrow;
	private static boolean itemNumberRightArrow;
	private static int iHover;
	private static int x_item;
	private static int y_item;
	private static int numberItem = 1;
	private static int lastSplit;
 	
	private static void drawBags(int i, int x_items, int y_items, int x_textt, int y_textt) {
		drawBag(i, x_items, y_items);
		if(slot_hover[i]) {
			iHover = i;
			x_item = x_items;
			y_item = y_items;
		}
		if(itemNumberOpen[i]) {
			iItemNumber = i;
			xItemNumber = x_items;
			yItemNumber = y_items;
		}
		//drawHoverBag(i, x_text, y_text, x_item, y_item);
	}
	public static void draw() {
		Arrays.fill(hoverButton, false);
		xItemNumber = 0;
		yItemNumber = 0;
		iHover = -1;
		if(bagChange) {
			bagSize[0] = Sprites.back_bag.getImageHeight();
			if(Mideas.bag().getEquippedBag(0) != null) {
				bagSize[1] = BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(0).getId()).getImageHeight();
			}
			else {
				bagSize[1] = 0;
				isBagOpen[1] = false;
			}
			if(Mideas.bag().getEquippedBag(1) != null) {
				bagSize[2] = BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(1).getId()).getImageHeight();
			}
			else {
				bagSize[2] = 0;
				isBagOpen[2] = false;
			}
			if(Mideas.bag().getEquippedBag(2) != null) {
				bagSize[3] = BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(2).getId()).getImageHeight();
			}
			else {
				bagSize[3] = 0;
				isBagOpen[3] = false;
			}
			if(Mideas.bag().getEquippedBag(3) != null) {
				bagSize[4] = BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(3).getId()).getImageHeight();
			}
			else {
				bagSize[4] = 0;
				isBagOpen[4] = false;
			}
			bagChange = false;
		}
		int xBagShift = 0;
		int bagShift = 0;
		int yBagShift = 0;
		int xBagIcon = -313;
		int yBagIcon = -4;
		int xCloseButton = -154;
		int yCloseButton = -2;
		Texture buttonTexture;
		if(isBagOpen[0]) {
			yBagShift = -bagSize[0]-150;
			if(Mideas.mouseX() >= Display.getWidth()+xCloseButton && Mideas.mouseX() <= Display.getWidth()+xCloseButton+Sprites.close_bag_button.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+bagShift+yBagShift+yCloseButton+9.5f && Mideas.mouseY() <=  Display.getHeight()+bagShift+yBagShift+9.5f+yCloseButton+Sprites.close_bag_button.getImageHeight()) {
				hoverButton[0] = true;
			}
			buttonTexture = getButtonTexture(0);
			Draw.drawQuad(buttonTexture, Display.getWidth()+xCloseButton, Display.getHeight()+bagShift+yBagShift+yCloseButton+9.5f);
			Draw.drawQuad(Sprites.back_bag, Display.getWidth()-320, Display.getHeight()+yBagShift);
			bagShift+= yBagShift;
		}
		if(isBagOpen[1]) {
			if(Mideas.bag().getEquippedBag(0) != null) {
				if(isBagOpen[0]) {
					yBagShift = -bagSize[1];
				}
				else {
					yBagShift = -bagSize[1]-142;
				}
				if(Mideas.mouseX() >= Display.getWidth()+xCloseButton && Mideas.mouseX() <= Display.getWidth()+xCloseButton+Sprites.close_bag_button.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+bagShift+yBagShift-2 && Mideas.mouseY() <=  Display.getHeight()+bagShift+yBagShift-2+Sprites.close_bag_button.getImageHeight()) {
					hoverButton[1] = true;
				}
				buttonTexture = getButtonTexture(1);
				Draw.drawQuad(buttonTexture, Display.getWidth()+xCloseButton, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(0)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(0).getId()), Display.getWidth()-320, Display.getHeight()+bagShift+yBagShift-10);
				bagShift+= yBagShift;
				yBagShift = 0;
			}
		}
		if(isBagOpen[2]) {
			if(Mideas.bag().getEquippedBag(1) != null) {
				if(isBagOpen[0] || isBagOpen[1]) {
					yBagShift = -bagSize[2];
				}
				else {
					yBagShift = -bagSize[2]-142;
				}
				if(Mideas.mouseX() >= Display.getWidth()+xCloseButton && Mideas.mouseX() <= Display.getWidth()+xCloseButton+Sprites.close_bag_button.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+bagShift+yBagShift-2 && Mideas.mouseY() <= Display.getHeight()+bagShift+yBagShift-2+Sprites.close_bag_button.getImageHeight()) {
					hoverButton[2] = true;
				}
				buttonTexture = getButtonTexture(2);
				Draw.drawQuad(buttonTexture, Display.getWidth()+xCloseButton, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(1)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(1).getId()), Display.getWidth()-320+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
				bagShift+= yBagShift;
				yBagShift = 0;
			}
		}
		if(isBagOpen[3]) {
			if(Mideas.bag().getEquippedBag(2) != null) {
				if(isBagOpen[0] || isBagOpen[1] || isBagOpen[2]) {
					yBagShift = -bagSize[3];
				}
				else {
					yBagShift = -bagSize[3]-142;
				}
				if(bagShift <= -700) {
					bagShift = 0;
					xBagShift = -200;
					yBagShift = -bagSize[3]-142;
				}
				if(Mideas.mouseX() >= Display.getWidth()+xCloseButton+xBagShift && Mideas.mouseX() <= Display.getWidth()+xCloseButton+xBagShift+Sprites.close_bag_button.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+bagShift+yBagShift-2 && Mideas.mouseY() <= Display.getHeight()+bagShift+yBagShift-2+Sprites.close_bag_button.getImageHeight()) {
					hoverButton[3] = true;
				}
				buttonTexture = getButtonTexture(3);
				Draw.drawQuad(buttonTexture, Display.getWidth()+xCloseButton+xBagShift, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(2)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(2).getId()), Display.getWidth()-320+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
				bagShift+= yBagShift;
				yBagShift = 0;
			}
		}
		if(isBagOpen[4]) {
			if(Mideas.bag().getEquippedBag(3) != null) {
				if(isBagOpen[0] || isBagOpen[1] || isBagOpen[2] || isBagOpen[3]) {
					yBagShift = -bagSize[4];
				}
				else {
					yBagShift = -bagSize[4]-142;
				}
				if(bagShift <= -700) {
					bagShift = 0;
					xBagShift = -200;
					yBagShift = -bagSize[4]-142;
				}
				if(Mideas.mouseX() >= Display.getWidth()+xCloseButton+xBagShift && Mideas.mouseX() <= Display.getWidth()+xCloseButton+xBagShift+Sprites.close_bag_button.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+bagShift+yBagShift-2 && Mideas.mouseY() <= Display.getHeight()+bagShift+yBagShift-2+Sprites.close_bag_button.getImageHeight()) {
					hoverButton[4] = true;
				}
				buttonTexture = getButtonTexture(4);
				Draw.drawQuad(buttonTexture, Display.getWidth()+xCloseButton+xBagShift, Display.getHeight()+bagShift+yBagShift+yCloseButton);
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(3)), Display.getWidth()+xBagIcon+xBagShift, Display.getHeight()+bagShift+yBagShift+yBagIcon);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(3).getId()), Display.getWidth()-320+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
			}
		}
		x = -303;
		xShift = 42;
		y = -50;
		yShift = 41;
		boolean backPack = true;
		boolean first = true;
		boolean second = true;
		boolean third = true;
		boolean fourth = true;
		int size = 16;
		int i = 0;
		int j = 0;
		int k = 0;
		int z = 0;
		boolean resize = false;
		xBagShift = 0;
		yBagShift = 0;
		while(i < Mideas.bag().getBag().length) {
			if(backPack) {
				if(isBagOpen[0]) {
					z+= -bagSize[0]-50;
				}
				backPack = false;
			}
			if(i < 16) {
				if(isBagOpen[0]) {
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z);
				}
			}
			else if(i >= 16 && i < Mideas.bag().getEquippedBagSize(0)+16) {
				if(isBagOpen[1]) {
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z);
				}
			}
			else if(i >= Mideas.bag().getEquippedBagSize(0)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+16) {
				if(isBagOpen[2]) { 
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z);
				}
			}
			else if(i >= Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+16) {
				if(isBagOpen[3]) {
					drawBags(i, x+j*xShift+xBagShift, k*yShift+z, x+xBagShift, z);
				}
			}
			else if(i >= Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+Mideas.bag().getEquippedBagSize(3)+16) {
				if(isBagOpen[4]) { 
					drawBags(i, x+j*xShift+xBagShift, k*yShift+z, x+xBagShift, z);
				}
			}
			i++;
			j++;
			if(j == 4) {
				j = 0;
			}
			if(i != 0 && i%4 == 0) {
				k++;
			}
			if(i == size && first) {
				if(Mideas.bag().getEquippedBag(0) != null) {
					if(isBagOpen[1]) {
						if(isBagOpen[0]) {
							z+= -bagSize[1]-10;
						}
						else {
							z+= -bagSize[1]-52;
						}
					}
					yBagShift = 0;
					size+= Mideas.bag().getEquippedBagSize(0);
				}
				first = false;
				j = 0;
				k = 0;
			}
			if(i == size && second) {
				if(Mideas.bag().getEquippedBag(1) != null) {
					if(isBagOpen[2]) {
						if(isBagOpen[0] && !isBagOpen[1]) {
							z+= -bagSize[2]-10;
						}
						else if(!isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else if(isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else {
							z+= -bagSize[2]-52;
						}
						yBagShift = 0;
					}
					size+= Mideas.bag().getEquippedBagSize(1);
				}
				second = false;
				j = 0;
				k = 0;
			}
			if(i == size && third) {
				if(Mideas.bag().getEquippedBag(2) != null) {
					if(isBagOpen[3]) {
						if(z <= -650) {
							z = 0;
							xBagShift = -201;
							yBagShift = -bagSize[3]+22;
							resize = true;
						}
						if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
							z+= -bagSize[3]-60;
						}
						else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[3]-52;
						}
						else if(!isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize) {
							z+= -bagSize[3]-50;
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) {
							z+= -bagSize[3]-52;
						}
						else if(!resize) {
							z+= -bagSize[3]-52;
						}
						else if(resize) {
							z+= -bagSize[3]-101;
						}
					}
					size+= Mideas.bag().getEquippedBagSize(2);
				}
				third = false;
				j = 0;
				k = 0;
			}
			if(i == size && fourth) {
				if(Mideas.bag().getEquippedBag(3) != null) {
					if(isBagOpen[4]) {
						if(z <= -650) {  
							z = 0;
							xBagShift = -200;
							yBagShift = -bagSize[4]+22;
							resize = true;
						}
						if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize)  {
							z+= -bagSize[3]-52;
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
							z+= -bagSize[4]-102;
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
							z+= -bagSize[4]-62;
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3]) {
							z+= -bagSize[4]-62;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
							z+= -bagSize[4]-62;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
							z+= -bagSize[4]-62;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && !resize) {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && resize) {
							z+= -bagSize[4]-102;
						}
						else if(isBagOpen[1] && isBagOpen[2] && !resize) {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !resize) {
							z+= -bagSize[4];
						}
						else if((isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
							z+= -bagSize[4]-60;
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[3] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[3] && isBagOpen[1] && isBagOpen[2] && !resize) {
							z+= -bagSize[4]-28;
						}
						else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if(!resize) {
							z+= -bagSize[4]-28;
						}
						else if(resize) {
							z+= -bagSize[4];
						}
					}
					size+= Mideas.bag().getEquippedBagSize(3);
				}
				fourth = false;
				j = 0;
				k = 0;
			}
		}
		backPack = true;
		first = true;
		second = true;
		third = true;
		fourth = true;
		size = 16;
		i = 0;
		j = 0;
		k = 0;
		z = 0;
		resize = false;
		xBagShift = 0;
		if(xItemNumber != 0) { //draw itemNumber frame
			drawItemNumber(xItemNumber, yItemNumber);
		}
		if(itemNumberOkButton) {
			Draw.drawQuad(Sprites.itemnumber_hover_ok, Display.getWidth()+xItemNumber+11, Display.getHeight()+yItemNumber+51.7f);
		}
		else if(itemNumberCancelButton) {
			Draw.drawQuad(Sprites.itemnumber_hover_cancel, Display.getWidth()+xItemNumber+87, Display.getHeight()+yItemNumber+52.3f);
		}
		/*if(iHover >= 0) {
			drawHoverBag(iHover, x_item, y_item);
		}*/
	}
	
	public static boolean mouseEvent() {
		if(DragManager.isHoverBagFrame()) {
			Arrays.fill(slot_hover, false);
		}
		itemNumberOkButton = false;
		itemNumberCancelButton = false;
		itemNumberLeftArrow = false;
		itemNumberRightArrow = false;
		if(iHover != -1) {
			slot_hover[iHover] = false;
		}
		checkItemNumberMouseEvent();
		if(!DragManager.isHoverCharacterFrame()) {
			if(!Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
					int i = 0;
					while(i < hoverButton.length) {
						if(hoverButton[i] && buttonDown[i]) {
							isBagOpen[i] = false;
							Arrays.fill(buttonDown, false);
							isOneButtonDown = false;
							return true;
						}
						i++;
					}
					if(itemNumberOkButton) {
						splitItem(iItemNumber);
						return true;
					}
					else if(itemNumberCancelButton) {
						itemNumberOpen[iItemNumber] = false;
						numberItem = 1;
						return true;
					}
					else if(itemNumberLeftArrow) {
						if(numberItem > 1) {
							numberItem--;
							return true;
						}
					}
					else if(itemNumberRightArrow) {
						if(numberItem < Mideas.joueur1().getNumberItem(Mideas.bag().getBag(iItemNumber))) {
							numberItem++;
							return true;
						}
					}
				}
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					if(isOneButtonDown) {
						Arrays.fill(buttonDown, false);
						isOneButtonDown = false;
						return true;
					}
				}
			}
			else if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0 || Mouse.getEventButton() == 1) {
					int i = 0;
					while(i < buttonDown.length) {
						if(hoverButton[i]) {
							buttonDown[i] = true;
							isOneButtonDown = true;
							break;
						}
						i++;
					}
				}
			}
			int x = -303;
			int xShift = 41;
			int yShift = 42;
			int y = -50;
			int i = 0;
			int j = 0;
			int k = 0;
			int z = 0;
			int xBagShift = 0;
			boolean backPack = true;
			boolean first = true;
			boolean second = true;
			boolean third = true;
			boolean fourth = true;
			boolean resize = false;
			int size = 16;
			while(i < Mideas.bag().getBag().length) {
				if(backPack) {
					if(isBagOpen[0]) {
						z+= -bagSize[0]-50;
					}
					backPack = false;
				}
				if(i < 16) {
					if(isBagOpen[0]) {
						slotHover(x+j*xShift+xBagShift, y+k*yShift+z, i);
					}
				}
				else if(i >= 16 && i < Mideas.bag().getEquippedBagSize(0)+16) {
					if(isBagOpen[1]) { 
						slotHover(x+j*xShift+xBagShift, y+k*yShift+z, i);
					}
				}
				else if(i >= Mideas.bag().getEquippedBagSize(0)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+16) {
					if(isBagOpen[2]) { 
						slotHover(x+j*xShift+xBagShift, y+k*yShift+z, i);
					}
				}
				else if(i >= Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+16) {
					if(isBagOpen[3]) {
						slotHover(x+j*xShift+xBagShift, k*yShift+z, i);
					}
				}
				else if(i >= Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+Mideas.bag().getEquippedBagSize(3)+16) {
					if(isBagOpen[4]) { 
						slotHover(x+j*xShift+xBagShift, k*yShift+z, i);
					}
				}
				i++;
				j++;
				if(j == 4) {
					j = 0;
				}
				if(i != 0 && i%4 == 0) {
					k++;
				}
				if(i == size && first) {
					if(Mideas.bag().getEquippedBag(0) != null) {
						if(isBagOpen[1]) {
							if(isBagOpen[0]) {
								z+= -bagSize[1]-10;
							}
							else {
								z+= -bagSize[1]-52;
							}
						}
						size+= Mideas.bag().getEquippedBagSize(0);
					}
					first = false;
					j = 0;
					k = 0;
				}
				if(i == size && second) {
					if(Mideas.bag().getEquippedBag(1) != null) {
						if(isBagOpen[2]) {
							if(isBagOpen[0] && !isBagOpen[1]) {
								z+= -bagSize[2]-10;
							}
							else if(!isBagOpen[0] && isBagOpen[1]) {
								z+= -bagSize[2];
							}
							else if(isBagOpen[0] && isBagOpen[1]) {
								z+= -bagSize[2];
							}
							else {
								z+= -bagSize[2]-52;
							}
						}
						size+= Mideas.bag().getEquippedBagSize(1);
					}
					second = false;
					j = 0;
					k = 0;
				}
				if(i == size && third) {
					if(Mideas.bag().getEquippedBag(2) != null) {
						if(isBagOpen[3]) {
							if(z <= -650) {
								z = 0;
								xBagShift = -199;
								resize = true;
							}
							if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
								z+= -bagSize[3]-60;
							}
							else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
								z+= -bagSize[3]-52;
							}
							else if(!isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize) {
								z+= -bagSize[3]-50;
							}
							else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) {
								z+= -bagSize[3]-102;
							}
							else if(!resize) {
								z+= -bagSize[3]-52;
							}
							else if(resize) {
								z+= -bagSize[3]-101;
							}
						}
						size+= Mideas.bag().getEquippedBagSize(2);
					}
					third = false;
					j = 0;
					k = 0;
				}
				if(i == size && fourth) {
					if(Mideas.bag().getEquippedBag(3) != null) {
						if(isBagOpen[4]) {
							if(z <= -650) { 
								z = 0;
								xBagShift = -199;
								resize = true;
							}
							if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize)  {
								z+= -bagSize[3]-52;
							}
							else if(!isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
								z+= -bagSize[4]-102;
							}
							else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
								z+= -bagSize[4]-62;
							}
							else if(!isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3]) {
								z+= -bagSize[4]-62;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
								z+= -bagSize[4]-62;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !isBagOpen[3] && !resize) {
								z+= -bagSize[4]-62;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && !resize) {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && !isBagOpen[3] && resize) {
								z+= -bagSize[4]-102;
							}
							else if(isBagOpen[1] && isBagOpen[2] && !resize) {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && resize) {
								z+= -bagSize[4];
							}
							else if(!isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !resize) {
								z+= -bagSize[4];
							}
							else if((isBagOpen[1] || isBagOpen[2]) && !resize)  {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
								z+= -bagSize[4]-60;
							}
							else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
								z+= -bagSize[4]-52;
							}
							else if(isBagOpen[3] && !isBagOpen[1] && !isBagOpen[2] && !resize) {
								z+= -bagSize[4];
							}
							else if(isBagOpen[3] && isBagOpen[1] && isBagOpen[2] && !resize) {
								z+= -bagSize[4]-28;
							}
							else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
								z+= -bagSize[4]-52;
							}
							else if(!resize) {
								z+= -bagSize[4]-28;
							}
							else if(resize) {
								z+= -bagSize[4];
							}
						}
						size+= Mideas.bag().getEquippedBagSize(3);
					}
					fourth = false;
					j = 0;
					k = 0;
				}
				
			}
		}
		return false;
	}
	
	private static void slotHover(int x, int y, int i) {
		if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()+x-3 && Mideas.mouseX() <= Display.getWidth()+x+37 && Mideas.mouseY() >= Display.getHeight()+y-3 && Mideas.mouseY() <= Display.getHeight()+y+38) {
			slot_hover[i] = true;
			Mideas.setHover(false);
		}
	}
	
	private static void drawBag(int i, int x, int y) {
		if(Mideas.bag().getBag(i) != null) {
			Draw.drawQuad(IconsManager.getSprite37((Mideas.bag().getBag(i).getSpriteId())), Display.getWidth()+x, Display.getHeight()+y);
			Draw.drawQuad(Sprites.bag_border1, Display.getWidth()+x-3, Display.getHeight()+y-2);
			if((Mideas.bag().getBag(i).isStackable())) {
				TTF2.itemNumber.drawStringShadow(Display.getWidth()+x+35-TTF2.font4.getWidth(Integer.toString(Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i)))), Display.getHeight()+y+20, Integer.toString(Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i))), Color.white, Color.black, 1, 1, 1);
				if(Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i)) <= 0) {
					Mideas.bag().setBag(i, null);
				}
			}
			if(slot_hover[i]) {
				Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x, Display.getHeight()+y);
			}
			if(Mideas.bag().getBag(i) == DragManager.getDraggedItem()) {
				Draw.drawColorQuad(Display.getWidth()+x, Display.getHeight()+y, 37, 35, new Color(0, 0, 0, .5f));
			}
		}
		if(DragManager.getClickBag(i)) {
			Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()+x-1, Display.getHeight()+y-1);
		}
	}
	
	private static void splitItem(int i) {
		if(Mideas.bag().getBag(i) != null) {
			if(Mideas.bag().getBag(i).getItemType() == ItemType.POTION) {
				Potion temp = PotionManager.getClone(Mideas.bag().getBag(i).getId());
				Mideas.joueur1().setNumberItem(temp, numberItem);
				DragManager.setDraggedItem(temp);
				Mideas.joueur1().setNumberItem(Mideas.bag().getBag(i), Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i))-numberItem);
			}
			else if(Mideas.bag().getBag(i).getItemType() == ItemType.ITEM) {
				
			}
			itemNumberOpen[i] = false;
			numberItem = 1;
			lastSplit = i;
			DragManager.setDraggedItemSplit(true);
		}
	}
	
	private static void checkItemNumberMouseEvent() {
		if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()+xItemNumber+10 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+10+Sprites.itemnumber_hover_ok.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+yItemNumber+55 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+55+Sprites.itemnumber_hover_ok.getImageHeight()) {
			itemNumberOkButton = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()+xItemNumber+88 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+88+Sprites.itemnumber_hover_cancel.getImageWidth() && Mideas.mouseY() >= Display.getHeight()+yItemNumber+55 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+55+Sprites.itemnumber_hover_cancel.getImageHeight()) {
			itemNumberCancelButton = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()+xItemNumber+6 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+18 && Mideas.mouseY() >= Display.getHeight()+yItemNumber+20 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+35) {
			itemNumberLeftArrow = true;
			Mideas.setHover(false);
		}
		else if(Mideas.getHover() && Mideas.mouseX() >= Display.getWidth()+xItemNumber+145 && Mideas.mouseX() <= Display.getWidth()+xItemNumber+157 && Mideas.mouseY() >= Display.getHeight()+yItemNumber+20 && Mideas.mouseY() <= Display.getHeight()+yItemNumber+35) {
			itemNumberRightArrow = true;
			Mideas.setHover(false);
		}
	}
	
	private static void drawItemNumber(int x, int y) {
		Draw.drawQuad(Sprites.itemnumber_frame, Display.getWidth()+x+30-Sprites.itemnumber_frame.getImageWidth(), Display.getHeight()+y-5-Sprites.itemnumber_frame.getImageHeight());
		TTF2.itemNumber.drawStringShadow(Display.getWidth()+x+153-Sprites.itemnumber_frame.getImageWidth()-TTF2.itemNumber.getWidth(Integer.toString(numberItem)), Display.getHeight()+y+15-Sprites.itemnumber_frame.getImageHeight(), Integer.toString(numberItem), Color.white, Color.black, 1, 1, 1);
		xItemNumber = x+30-Sprites.itemnumber_frame.getImageWidth();
		yItemNumber = y-5-Sprites.itemnumber_frame.getImageHeight();
		if(numberItem > 1) {
			Draw.drawQuad(Sprites.itemnumber_leftyellow_arrow, Display.getWidth()+xItemNumber+9, Display.getHeight()+yItemNumber+19);
		}
		if(numberItem == Mideas.joueur1().getNumberItem(Mideas.bag().getBag(iItemNumber))) {
			Draw.drawQuad(Sprites.itemnumber_rightgray_arrow, Display.getWidth()+xItemNumber+149, Display.getHeight()+yItemNumber+19);
		}
	}
	
	/*private static void drawHoverBag(int i, int x, int z, int x_hover, int y_hover) throws FileNotFoundException {
		if(slot_hover[i]) {
			int shift = 60;
			if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				if(((Stuff)Mideas.bag().getBag(i)).getArmor() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getStamina() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getStrength() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getCritical() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				}
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(i).getSellPrice(), x-70, y+100);
			}
			else if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getItemType() == ItemType.POTION) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()/2-80, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName())/2, Display.getHeight()/2+y-20, Mideas.bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				if(((Potion)Mideas.bag().getBag(i)).getPotionHeal() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "Restores "+((Potion)Mideas.bag().getBag(i)).getPotionHeal()+" Hp", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Potion)Mideas.bag().getBag(i)).getPotionMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()/2+y+shift+z, "Restores "+((Potion)Mideas.bag().getBag(i)).getPotionMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				}
			}
			if(Mideas.bag().getBag(i) != null && DragManager.getClickBag(i) && DragManager.getDraggedItem() == null) {
				Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()+x+x_hover, Display.getHeight()/2+y+y_hover);
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x+x_hover, Display.getHeight()/2+y+y_hover);
		}
	}*/
	
	public static void drawHoverBag(int i, int x, int y) {
		if(slot_hover[i] && !isHoverItemNumberFrame()) {
			if(Mideas.bag().getBag(i) != null) {
				if(Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
					drawStuff(i, x, y);
				}
				else if(Mideas.bag().getBag(i).getItemType() == ItemType.WEAPON) {
					drawWeapon(i, x, y);
				}
				else if(Mideas.bag().getBag(i).getItemType() == ItemType.POTION) {
					drawPotion(i, x, y);
				}
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x, Display.getHeight()+y-1);
		}
	}
	
	private static void drawPotion(int i, int x, int z) {
		int y = -75;
		int shift = 0;
		int xShift = TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName());
		Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, 10+y, bgColor);
		Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, 11+y, borderColor);
		y =  -65;
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, Mideas.bag().getBag(i).getStuffName(), getItemNameColor(Mideas.bag().getBag(i)), Color.black, 1, 1, 1);
		shift+= 25;
		if(((Potion)Mideas.bag().getBag(i)).getPotionHeal() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+shift, "Restores "+((Potion)Mideas.bag().getBag(i)).getPotionHeal()+" Hp", Color.green, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(((Potion)Mideas.bag().getBag(i)).getPotionMana() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+shift, "Restores "+((Potion)Mideas.bag().getBag(i)).getPotionMana()+" mana", Color.green, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(Mideas.getLevel() >= ((Potion)Mideas.bag().getBag(i)).getLevel()) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Level "+((Potion)Mideas.bag().getBag(i)).getLevel()+" required", Color.white, Color.black, 1, 1, 1);
		}
		else {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Level "+((Potion)Mideas.bag().getBag(i)).getLevel()+" required", Color.red, Color.black, 1, 1, 1);
		}
		calcCoinContainer(Mideas.bag().getBag(i).getSellPrice(), x-55, z+y+shift-5);
	}
	
	private static void drawStuff(int i, int x, int z) {
		Color temp = null;
		int xShift = 237;
		int shift = 45;
		String classe = "";
		if(((Stuff)Mideas.bag().getBag(i)).getClassType().length < 10) {
			classe = "Classes: ";
			int k = 0;
			while(k < ((Stuff)Mideas.bag().getBag(i)).getClassType().length) {
				if(k == ((Stuff)Mideas.bag().getBag(i)).getClassType().length-1) {
					classe+= ((Stuff)Mideas.bag().getBag(i)).convClassTypeToString(k);
				}
				else {
					classe+= ((Stuff)Mideas.bag().getBag(i)).convClassTypeToString(k)+", ";
				}
				k++;
			}
		}
		//if(TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName()) > 237 || TTF2.statsName.getWidth(classe) > 237) {
		xShift = Math.max(TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName()), TTF2.statsName.getWidth(classe))+15;
		//}
		int y = -75-TTF2.statsName.getLineHeight()*getNumberStats((Stuff)Mideas.bag().getBag(i));
		Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, y, bgColor);
		Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, y, borderColor);
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, Mideas.bag().getBag(i).getStuffName(), getItemNameColor(Mideas.bag().getBag(i)), Color.black, 1);
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y+23, ((Stuff)Mideas.bag().getBag(i)).convStuffTypeToString(), Color.white, Color.black, 1);
		if(DragManager.canWear((Stuff)Mideas.bag().getBag(i))) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-20-TTF2.font4.getWidth(((Stuff)Mideas.bag().getBag(i)).convWearToString()), Display.getHeight()+y+23+z, ((Stuff)Mideas.bag().getBag(i)).convWearToString(), temp, Color.black, 1);
		if(((Stuff)Mideas.bag().getBag(i)).getArmor() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Armor : "+((Stuff)Mideas.bag().getBag(i)).getArmor(), Color.white, Color.black, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getStrength() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getMana() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getStamina() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getCritical() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getGemSlot1() != GemColor.NONE) {
			if(((Stuff)Mideas.bag().getBag(i)).getEquippedGem1() != null) {
				Draw.drawQuad(GemManager.getGemSprite(((Stuff)Mideas.bag().getBag(i)).getEquippedGem1().getId()), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, writeGemStats(((Stuff)Mideas.bag().getBag(i)).getEquippedGem1()), Color.white, Color.black, 1);
			}
			else {
				Draw.drawQuad(((Stuff)Mideas.bag().getBag(i)).getFreeSlotGemSprite1(), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, convGemColorToString(((Stuff)Mideas.bag().getBag(i)).getGemSlot1())+" Socket", Color.gray, Color.black, 1);
			}
			Draw.drawQuad(Sprites.cursor, -5000, -5000);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getGemSlot2() != GemColor.NONE) {
			if(((Stuff)Mideas.bag().getBag(i)).getEquippedGem2() != null) {
				Draw.drawQuad(GemManager.getGemSprite(((Stuff)Mideas.bag().getBag(i)).getEquippedGem2().getId()), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, writeGemStats(((Stuff)Mideas.bag().getBag(i)).getEquippedGem2()), Color.white, Color.black, 1);
			}
			else {
				Draw.drawQuad(((Stuff)Mideas.bag().getBag(i)).getFreeSlotGemSprite2(), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, convGemColorToString(((Stuff)Mideas.bag().getBag(i)).getGemSlot2())+" Socket", Color.gray, Color.black, 1);
			}
			Draw.drawQuad(Sprites.cursor, -5000, -5000);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getGemSlot3() != GemColor.NONE) {
			if(((Stuff)Mideas.bag().getBag(i)).getEquippedGem3() != null) {
				Draw.drawQuad(GemManager.getGemSprite(((Stuff)Mideas.bag().getBag(i)).getEquippedGem3().getId()), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, writeGemStats(((Stuff)Mideas.bag().getBag(i)).getEquippedGem3()), Color.white, Color.black, 1);
			}
			else {
				Draw.drawQuad(((Stuff)Mideas.bag().getBag(i)).getFreeSlotGemSprite3(), Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x+15-xShift, Display.getHeight()+y+shift+z-3, convGemColorToString(((Stuff)Mideas.bag().getBag(i)).getGemSlot3())+" Socket", Color.gray, Color.black, 1);
			}
			Draw.drawQuad(Sprites.cursor, -5000, -5000);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getGemBonusType() != GemBonusType.NONE) {
			if(((Stuff)Mideas.bag().getBag(i)).getGemBonusActivated()) {
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z-3, "Socket bonus: +"+((Stuff)Mideas.bag().getBag(i)).getGemBonusValue()+" "+convGemBonusTypeToString(((Stuff)Mideas.bag().getBag(i)).getGemBonusType()), Color.green, Color.black, 1);
			}
			else {
				TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z-3, "Socket Bonus: +"+((Stuff)Mideas.bag().getBag(i)).getGemBonusValue()+" "+convGemBonusTypeToString(((Stuff)Mideas.bag().getBag(i)).getGemBonusType()), Color.gray, Color.black, 1);
			}
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).canEquipTo(DragManager.convClassType())) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		if(!classe.equals("")) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, classe, temp, Color.black, 1);
			shift+= 20;
		}
		if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel()) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Level "+((Stuff)Mideas.bag().getBag(i)).getLevel()+" required", Color.white, Color.black, 1);
		}
		else {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Level "+((Stuff)Mideas.bag().getBag(i)).getLevel()+" required", Color.red, Color.black, 1);
		}
		calcCoinContainer(Mideas.bag().getBag(i).getSellPrice(), x-55, z+y+shift-5);
	}
	
	private static void drawWeapon(int i, int x, int z) {
		Color temp = null;
		int xShift = 0;
		int shift = 45;
		String classe = "";
		int y = -75-TTF2.statsName.getLineHeight()*getNumberStats((Stuff)Mideas.bag().getBag(i));
		if(((Stuff)Mideas.bag().getBag(i)).getClassType().length < 10) {
			classe = "Classes: ";
			int k = 0;
			while(k < ((Stuff)Mideas.bag().getBag(i)).getClassType().length) {
				if(k == ((Stuff)Mideas.bag().getBag(i)).getClassType().length-1) {
					classe+= ((Stuff)Mideas.bag().getBag(i)).convClassTypeToString(k);
				}
				else {
					classe+= ((Stuff)Mideas.bag().getBag(i)).convClassTypeToString(k)+", ";
				}
				k++;
			}
		}
		//if(TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName()) > 285 || TTF2.statsName.getWidth(classe) > 285) {
			xShift = Math.max(TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName())-275, TTF2.statsName.getWidth(classe))+15;
		//}
		Draw.drawColorQuad(Display.getWidth()+x-1, Display.getHeight()+z-2, -5-xShift, y, bgColor);
		Draw.drawColorQuadBorder(Display.getWidth()+x-1, Display.getHeight()+z-2, -6-xShift, y, borderColor);
		TTF2.itemName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+z+y, Mideas.bag().getBag(i).getStuffName(), getItemNameColor(Mideas.bag().getBag(i)), Color.black, 1, 1, 1);
		if(DragManager.canWear((Stuff)Mideas.bag().getBag(i))) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-25-TTF2.font4.getWidth(((Stuff)Mideas.bag().getBag(i)).convTypeToString()), Display.getHeight()+y+25+z, ((Stuff)Mideas.bag().getBag(i)).convTypeToString(), temp, Color.black, 1, 1, 1);
		TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+25+z, ((Stuff)Mideas.bag().getBag(i)).convSlotToString(), Color.white, Color.black, 1, 1, 1);
		if(((Stuff)Mideas.bag().getBag(i)).getArmor() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Armor : "+((Stuff)Mideas.bag().getBag(i)).getArmor(), Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getStrength() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getMana() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getStamina() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).getCritical() > 0) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(((Stuff)Mideas.bag().getBag(i)).canEquipTo(DragManager.convClassType())) {
			temp = Color.white;
		}
		else {
			temp = Color.red;
		}
		if(!classe.equals("")) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, classe, temp, Color.black, 1, 1, 1);
			shift+= 20;
		}
		if(Mideas.getLevel() >= ((Stuff)Mideas.bag().getBag(i)).getLevel()) {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Level "+((Stuff)Mideas.bag().getBag(i)).getLevel()+" required", Color.white, Color.black, 1, 1, 1);
		}
		else {
			TTF2.statsName.drawStringShadow(Display.getWidth()+x-2-xShift, Display.getHeight()+y+shift+z, "Level "+((Stuff)Mideas.bag().getBag(i)).getLevel()+" required", Color.red, Color.black, 1, 1, 1);
		}
		calcCoinContainer(Mideas.bag().getBag(i).getSellPrice(), x-55, z+y+shift-2);
	}
	
	public static Texture getButtonTexture(int i) {
		if(buttonDown[i]) {
			if(hoverButton[i]) {
				return Sprites.close_bag_button_down_hover;
			}
			else {
				return Sprites.close_bag_button_down;
			}
		}
		else if(hoverButton[i]) {
			return Sprites.close_bag_button_hover;
		}
		else {
			return Sprites.close_bag_button;
		}
	}
	
	public static int getNumberStats(Stuff stuff) {
		int i = 0;
		if(stuff.getArmor() > 0) {
			i++;
		}
		if(stuff.getCritical() > 0) {
			i++;
		}
		if(stuff.getMana() > 0) {
			i++;
		}
		if(stuff.getStamina() > 0) {
			i++;
		}
		if(stuff.getStrength() > 0) {
			i++;
		}
		if(stuff.getClassType().length < 10) {
			i++;
		}
		if(stuff.getGemSlot1() != GemColor.NONE)  {
			i+= 2; //bonus type + gem
		}
		if(stuff.getGemSlot2() != GemColor.NONE) {
			i++;
		}
		if(stuff.getGemSlot3() != GemColor.NONE) {
			i++;
		}
		return i;
	}
	
	public static String convGemColorToString(GemColor color) {
		if(color == GemColor.BLUE) {
			return "Blue";
		}
		if(color == GemColor.GREEN) {
			return "Green";
		}
		if(color == GemColor.META) {
			return "Meta";
		}
		if(color == GemColor.ORANGE) {
			return "Orange";
		}
		if(color == GemColor.PURPLE) {
			return "Purple";
		}
		if(color == GemColor.RED) {
			return "Red";
		}
		if(color == GemColor.YELLOW) {
			return "Yellow";
		}
		return null;
	}
	
	public static String convGemBonusTypeToString(GemBonusType bonus) {
		if(bonus == GemBonusType.ARMOR) {
			return "Armor";
		}
		if(bonus == GemBonusType.CRITICAL) {
			return "Critical";
		}
		if(bonus == GemBonusType.MANA) {
			return "Mana";
		}
		if(bonus == GemBonusType.STAMINA) {
			return "Stamina";
		}
		if(bonus == GemBonusType.STRENGTH) {
			return "Strength";
		}
		return null;
	}
	
	public static Color getItemNameColor(Item item) {
		if(item.getQuality() == 0) {
			return Color.gray;
		}
		if(item.getQuality() == 1) {
			return Color.white;
		}
		if(item.getQuality() == 2) {
			return Color.green;
		}
		if(item.getQuality() == 3) {
			return Color.decode("#0268CC");
		}
		if(item.getQuality() == 4) {
			return Color.decode("#822CB7");
		}
		if(item.getQuality() == 5) {
			return Color.decode("#FF800D");
		}
		return Color.white;
	}
	
	public static String writeGemStats(Gem gem) {
		boolean stats = false;
		StringBuilder result = new StringBuilder();
		if(gem.getArmor() > 0) {
			result.append("+"+gem.getArmor()+" armor");
			stats = true;
		}
		if(gem.getCritical() > 0) {
			if(stats) {
				result.append(" and ");
			}
			result.append("+"+gem.getCritical()+" critical");
			stats = true;
		}
		if(gem.getStamina() > 0) {
			if(stats) {
				result.append(" and ");
			}
			result.append("+"+gem.getStamina()+" stamina");
			stats = true;
		}
		if(gem.getStrength() > 0) {
			if(stats) {
				result.append(" and ");
			}
			result.append("+"+gem.getStrength()+" strength");
			stats = true;
		}
		return result.toString();
	}
	
	public static int getSlotItem(Item item) {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(item != null && Mideas.bag().getBag(i) != null && item.getId() == Mideas.bag().getBag(i).getId()) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static void setBagOpen(int i, boolean we) {
		if(i < isBagOpen.length) {
			isBagOpen[i] = we;
		}
	}
	
	public static boolean getBagOpen(int i) {
		if(i < isBagOpen.length) {
			return isBagOpen[i];
		}
		return false;
	}
	
	public static void setBagchange(boolean we) {
		bagChange = we;
	}
	
	public static void setItemNumberOpen(int i, boolean we) {
		itemNumberOpen[i] = we;
	}
	
	public static void setIsOneButtonDown(boolean we) {
		isOneButtonDown = we;
	}
	
	public static boolean isHoverItemNumberFrame() {
		if(Mideas.mouseX() >= Display.getWidth()+xItemNumber && Mideas.mouseX() <= Display.getWidth()+xItemNumber+Sprites.itemnumber_frame.getImageWidth()+8 && Mideas.mouseY() >= Display.getHeight()+yItemNumber && Mideas.mouseY() <= Display.getHeight()+yItemNumber+Sprites.itemnumber_frame.getImageHeight()+4) {
			return true;
		}
		return false;
	}

	
	public static boolean calcCoinContainer(int cost, int x, int y) {
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()+y);
			return true;
		}
		return true;
	}
	
	public static boolean getContainerFrameSlotHover(int i) {
		if(i < slot_hover.length) {
			return slot_hover[i];
		}
		return false;
	}
	
	public static boolean[] getContainerFrameSlotHover() {
		return slot_hover;
	}
	
	public static int getLastSplit() {
		return lastSplit;
	}
	
	public static int getItemHover() {
		return iHover;
	}
	
	public static int getXItemHover() {
		return x_item;
	}
	
	public static int getYItemHover() {
		return y_item;
	}
}