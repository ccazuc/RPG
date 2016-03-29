package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
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
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;
import com.mideas.rpg.v2.game.item.stuff.BagManager;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class ContainerFrame {
	
	private static boolean[] slot_hover = new boolean[300];
	private static Color bgColor = new Color(0, 0, 0,.8f); 
	private static int x;
	private static int xShift;
	private static int y;
	private static int yShift;
	private static boolean[] isBagOpen = new boolean[5];
	private static int[] bagSize = new int[5];
	private static boolean bagChange = true;
	
	private static void drawBags(int i, int x_item, int y_item, int x_text, int y_text, int z) throws FileNotFoundException {
		drawBag(i, x_item, y_item);
		drawHoverBag(i, x_text, y_text, x_item, y_item, z);
		
	}
	public static void draw() throws LWJGLException, IOException {
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
		if(isBagOpen[0]) {
			Draw.drawQuad(Sprites.back_bag, Display.getWidth()-220, Display.getHeight()-bagSize[0]-78);
			bagShift+= -bagSize[0]-78;
		}
		if(isBagOpen[1]) {
			if(Mideas.bag().getEquippedBag(0) != null) {
				if(isBagOpen[0]) {
					yBagShift = -bagSize[1];
				}
				else {
					yBagShift = -bagSize[1]-67;
				}
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(0)), Display.getWidth()-213+xBagShift, Display.getHeight()+bagShift+yBagShift-8);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(0).getId()), Display.getWidth()-220, Display.getHeight()+bagShift+yBagShift-10);
				if(isBagOpen[0]) {
					bagShift+= -bagSize[1];
				}
				else {
					bagShift+= -bagSize[1]-67;
				}
				yBagShift = 0;
				Draw.drawQuad(Sprites.cursor, -100, -100);
			}
		}
		if(isBagOpen[2]) {
			if(Mideas.bag().getEquippedBag(1) != null) {
				if(isBagOpen[0] || isBagOpen[1]) {
					yBagShift = -bagSize[2];
				}
				else {
					yBagShift = -bagSize[2]-67;
				}
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(1)), Display.getWidth()-213+xBagShift, Display.getHeight()+bagShift+yBagShift-8);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(1).getId()), Display.getWidth()-220+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
				if(isBagOpen[0] || isBagOpen[1]) {
					bagShift+= -bagSize[2];
				}
				else {
					bagShift+= -bagSize[2]-67;
				}
				yBagShift = 0;
				Draw.drawQuad(Sprites.cursor, -100, -100);
			}
		}
		if(isBagOpen[3]) {
			if(Mideas.bag().getEquippedBag(2) != null) {
				boolean resize = false;
				if(isBagOpen[0] || isBagOpen[1] || isBagOpen[2]) {
					yBagShift = -bagSize[3];
				}
				else {
					yBagShift = -bagSize[3]-67;
				}
				if(bagShift <= -700) {
					bagShift = 0;
					xBagShift = -200;
					yBagShift = -bagSize[3]-67;
					resize = true;
				}
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(2)), Display.getWidth()-213+xBagShift, Display.getHeight()+bagShift+yBagShift-8);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(2).getId()), Display.getWidth()-220+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
				if((isBagOpen[0] || isBagOpen[1] || isBagOpen[2]) && !resize) {
					bagShift+= -bagSize[3];
				}
				else if(!resize) {
					bagShift+= -bagSize[3]-67;
				}
				if(resize) {
					bagShift+= -bagSize[3]-67;
					resize = false;
				}
				yBagShift = 0;
				Draw.drawQuad(Sprites.cursor, -100, -100);
			}
		}
		if(isBagOpen[4]) {
			if(Mideas.bag().getEquippedBag(3) != null) {
				if(isBagOpen[0] || isBagOpen[1] || isBagOpen[2] || isBagOpen[3]) {
					yBagShift = -bagSize[4];
				}
				else {
					yBagShift = -bagSize[4]-67;
				}
				if(bagShift <= -700) {
					bagShift = 0;
					xBagShift = -200;
					yBagShift = -bagSize[4]-67;
				}
				Draw.drawQuad(IconsManager.getSprite35(Mideas.bag().getSpriteId(3)), Display.getWidth()-213+xBagShift, Display.getHeight()+bagShift+yBagShift-8);
				Draw.drawQuad(BagManager.getBagsSprites().get(Mideas.bag().getEquippedBag(3).getId()), Display.getWidth()-220+xBagShift, Display.getHeight()+bagShift+yBagShift-10);
				Draw.drawQuad(Sprites.cursor, -100, -100);
			}
		}
		x = -204;
		xShift = 42;
		y = -51;
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
		xBagShift = 0;
		yBagShift = 0;
		while(i < Mideas.bag().getBag().length) {
			if(backPack) {
				if(isBagOpen[0]) {
					z+= -bagSize[0]+22;
				}
				backPack = false;
			}
			if(i < 16) {
				if(isBagOpen[0]) {
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z, z);
				}
			}
			else if(i >= 16 && i < Mideas.bag().getEquippedBagSize(0)+16) {
				if(isBagOpen[1]) {
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z, z);
				}
			}
			else if(i >= Mideas.bag().getEquippedBagSize(0)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+16) {
				if(isBagOpen[2]) { 
					drawBags(i, x+j*xShift+xBagShift, y+k*yShift+z, x+xBagShift, y+z, z);
				}
			}
			else if(i >= Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+16) {
				if(isBagOpen[3]) {
					drawBags(i, x+j*xShift+xBagShift, k*yShift+z, x+xBagShift, z, z);
				}
			}
			else if(i >= Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+16 && i < Mideas.bag().getEquippedBagSize(0)+Mideas.bag().getEquippedBagSize(1)+Mideas.bag().getEquippedBagSize(2)+Mideas.bag().getEquippedBagSize(3)+16) {
				if(isBagOpen[4]) { 
					drawBags(i, x+j*xShift+xBagShift, k*yShift+z, x+xBagShift, z, z);
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
							z+= -bagSize[1]-8;
						}
						else {
							z+= -bagSize[1]+22;
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
							z+= -bagSize[2]-8;
						}
						else if(!isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else if(isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else {
							z+= -bagSize[2]+22;
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
						boolean resize = false;
						if(z <= -650) {
							z = 0;
							xBagShift = -200;
							yBagShift = -bagSize[3]+22;
							resize = true;
						}
						if((isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) && !resize) {
							z+= -bagSize[3]-60;
						}
						else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[3]-52;
						}
						else if(!isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize) {
							z+= -bagSize[3]-50;
						}
						else if(!resize) {
							z+= -bagSize[3]-28;
						}
						else if(resize) {
							z+= -bagSize[3]-28;
						}
						resize = false;
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
						boolean resize = false;
						if(z <= -650) {
							z = 0;
							xBagShift = -200;
							yBagShift = -bagSize[4]+22;
							resize = true;
						}
						if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[1] && isBagOpen[2] && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if((isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if((isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) && !resize) {
							z+= -bagSize[4]-60;
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
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
							z+= -bagSize[4]-28;
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
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		Arrays.fill(slot_hover, false);
		if(Mouse.getEventButtonState()) {
			if(Mideas.mouseX() >= Display.getWidth()/2+545 && Mideas.mouseX() <= Display.getWidth()/2+563 && Mideas.mouseY() >= Display.getHeight()/2-95 && Mideas.mouseY() <= Display.getHeight()/2-70 || (Mideas.mouseX() >= Display.getWidth()/2+545 && Mideas.mouseX() <= Display.getWidth()/2+563 && Mideas.mouseY() >= Display.getHeight()/2+155 && Mideas.mouseY() <= Display.getHeight()/2+175 )) {
				Interface.closeContainerFrame();
				return true;
			}
		}
		int x = -203;
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
		int size = 16;
		while(i < Mideas.bag().getBag().length) {
			if(backPack) {
				if(isBagOpen[0]) {
					z+= -bagSize[0]+22;
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
							z+= -bagSize[1]-8;
						}
						else {
							z+= -bagSize[1]+22;
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
							z+= -bagSize[2]-8;
						}
						else if(!isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else if(isBagOpen[0] && isBagOpen[1]) {
							z+= -bagSize[2];
						}
						else {
							z+= -bagSize[2]+22;
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
						boolean resize = false;
						if(z <= -650) {
							z = 0;
							xBagShift = -198;
							resize = true;
						}
						if((isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) && !resize) {
							z+= -bagSize[3]-60;
						}
						else if(isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[3]-52;
						}
						else if(!isBagOpen[0] && (isBagOpen[1] || isBagOpen[2]) && !resize) {
							z+= -bagSize[3]-50;
						}
						else if(!resize) {
							z+= -bagSize[3]-28;
						}
						else if(resize) {
							z+= -bagSize[3]-28;
						}
						resize = false;
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
						boolean resize = false;
						if(z <= -650) {
							z = 0;
							xBagShift = -198;
							resize = true;
						}
						if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(isBagOpen[1] && isBagOpen[2] && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if(isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && isBagOpen[1] && !isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if(!isBagOpen[0] && !isBagOpen[1] && isBagOpen[2] && isBagOpen[3] && !resize) {
							z+= -bagSize[4];
						}
						else if((isBagOpen[1] || isBagOpen[2]) && !resize)  {
							z+= -bagSize[4]-52;
						}
						else if((isBagOpen[0] && !isBagOpen[1] && !isBagOpen[2]) && !resize) {
							z+= -bagSize[4]-60;
						}
						else if(!isBagOpen[0] && isBagOpen[1] && isBagOpen[2] && isBagOpen[3]) {
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
							z+= -bagSize[4]-28;
						}
					}
					size+= Mideas.bag().getEquippedBagSize(3);
				}
				fourth = false;
				j = 0;
				k = 0;
			}
			/*if(i == size && backPack) {
				if(isBagOpen[1]) {
					z+= -bagSize[1]-10;
				}
				size+= Mideas.bag().getEquippedBagSize(0);
				backPack = false;
				j = 0;
				k = 0;
			}
			else if(i == size && first && Mideas.bag().getEquippedBagSize(0) != -1) {
				if(isBagOpen[2]) {
					z+= -bagSize[2]-10;
					if(z <= -302) {
						z = Display.getHeight()/2-bagSize[2]+15;
						xBagShift = -200;
					}
				}
				size+= Mideas.bag().getEquippedBagSize(1);
				first = false;
				j = 0;
				k = 0;
			}
			else if(i == size && second && Mideas.bag().getEquippedBagSize(1) != -1) {
				if(isBagOpen[3]) {
					z+= -bagSize[3]-10;
					yBagShift = -bagSize[3]+15;
					if(z <= -302) {
						xBagShift = -200;
						z = 413;
					}
				}
				size+= Mideas.bag().getEquippedBagSize(2);
				second = false;
				j = 0;
				k = 0;
			}
			else if(i == size && third && Mideas.bag().getEquippedBagSize(2) != -1) {
				if(isBagOpen[4]) {
					z+= -bagSize[4]-10;
					if(z <= -302) {
						xBagShift = -200;
						z = 413;
					}
				}
				size+= Mideas.bag().getEquippedBagSize(3);
				third = false;
				j = 0;
				k = 0;
			}*/
		}
		if(Mouse.getEventButton() == 1) {
			if(Mouse.getEventButtonState()) {
				if(DragManager.isHoverBagFrame()) {
					i = 0;
					while(i < Mideas.bag().getBag().length) {
						if(clickSellItem(i)) {
							CharacterStuff.setBagItems();
							break;
						}
						i++;
					}
				}
			}
		}
		return false;
	}
	
	public static void keyboardEvent() {
		
	}
	
	private static boolean sellItem(Item item, boolean slot_hover) throws FileNotFoundException, SQLTimeoutException, SQLException {
		if(item != null && slot_hover && Interface.getShopFrameStatus()) {
			if(item.getItemType() == ItemType.ITEM || item.getItemType() == ItemType.POTION) {
				LogChat.setStatusText3("Vous avez vendu "+Mideas.joueur1().getNumberItem(item)+" "+item.getStuffName()+" pour "+item.getSellPrice()*Mideas.joueur1().getNumberItem(item));
				Mideas.setGold(item.getSellPrice()*Mideas.joueur1().getNumberItem(item));
				Mideas.joueur1().setNumberItem(item, 0);
			}
			else {
				Mideas.setGold(item.getSellPrice());
				LogChat.setStatusText3("Vous avez vendu "+item.getStuffName()+" pour "+item.getSellPrice());
			}
			return true;
		}
		return false;
	}
	public static boolean calcCoinContainer(int cost, int x, int y) throws FileNotFoundException {
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-30-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x-5-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coinContainer.drawStringShadow(Display.getWidth()+x+20-TTF2.coinContainer.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin_container, Display.getWidth()+x+20, Display.getHeight()/2+y);
			return true;
		}
		return true;
	}
	
	private static void slotHover(int x, int y, int i) {
		if(Mideas.mouseX() >= Display.getWidth()+x-3 && Mideas.mouseX() <= Display.getWidth()+x+37 && Mideas.mouseY() >= Display.getHeight()+y-3 && Mideas.mouseY() <= Display.getHeight()+y+38) {
			slot_hover[i] = true;
		}
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
	
	private static boolean clickSellItem(int i) throws FileNotFoundException, SQLTimeoutException, SQLException {
		if(sellItem(Mideas.bag().getBag(i), slot_hover[i])) {
			Mideas.bag().setBag(i, null);
			return true;
		}
		return false;
	}
	
	private static void drawBag(int i, int x, int y) {
		if(Mideas.bag().getBag(i) != null && !(Mideas.bag().getBag(i) == DragManager.getDraggedItem())) {
			Draw.drawQuad(IconsManager.getSprite35((Mideas.bag().getBag(i).getSpriteId())), Display.getWidth()+x, Display.getHeight()+y);
			Draw.drawQuad(Sprites.bag_border1, Display.getWidth()+x-3, Display.getHeight()+y-2);
			if((Mideas.bag().getBag(i).getItemType() == ItemType.ITEM || Mideas.bag().getBag(i).getItemType() == ItemType.POTION)) {
				TTF2.itemNumber.drawStringShadow(Display.getWidth()+x+15, Display.getHeight()+y+17, Integer.toString(Mideas.bag().getNumberBagItem(Mideas.bag().getBag(i))), Color.white, Color.black, 1, 1, 1);
				if(Mideas.joueur1().getNumberItem(Mideas.bag().getBag(i)) <= 0) {
					Mideas.bag().setBag(i, null);
				}
			}
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
	
	private static void drawHoverBag(int i, int x, int z, int x_hover, int y_hover, int y_quad) throws FileNotFoundException {
		if(slot_hover[i]) {
			int shift = 60;
			if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getItemType() == ItemType.STUFF) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()-80+y_quad, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName())/2, Display.getHeight()+y+32+z, Mideas.bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				if(((Stuff)Mideas.bag().getBag(i)).getArmor() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getStamina() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getStrength() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Stuff)Mideas.bag().getBag(i)).getCritical() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "+"+((Stuff)Mideas.bag().getBag(i)).getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
				}
				ContainerFrame.calcCoinContainer(Mideas.bag().getBag(i).getSellPrice(), x-70, y+100+y_quad);
			}
			else if(Mideas.bag().getBag(i) != null && Mideas.bag().getBag(i).getItemType() == ItemType.POTION) {
				Draw.drawColorQuad(Display.getWidth()+x-300, Display.getHeight()-80+y_quad, 285, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x-180-TTF2.itemName.getWidth(Mideas.bag().getBag(i).getStuffName())/2, Display.getHeight()+y+32+z, Mideas.bag().getBag(i).getStuffName(), Color.white, Color.black, 1, 1, 1);
				if(((Potion)Mideas.bag().getBag(i)).getPotionHeal() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "Restores "+((Potion)Mideas.bag().getBag(i)).getPotionHeal()+" Hp", Color.white, Color.black, 1, 1, 1);
					shift+= 20;
				}
				if(((Potion)Mideas.bag().getBag(i)).getPotionMana() > 0) {
					TTF2.statsName.drawStringShadow(Display.getWidth()+x-295, Display.getHeight()+y+shift+z, "Restores "+((Potion)Mideas.bag().getBag(i)).getPotionMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				}
			}
			if(Mideas.bag().getBag(i) != null && DragManager.getClickBag(i) && DragManager.getDraggedItem() == null) {
				Draw.drawQuad(Sprites.bag_click_hover, Display.getWidth()+x_hover, Display.getHeight()+y_hover);
			}
			Draw.drawQuad(Sprites.bag_hover, Display.getWidth()+x_hover, Display.getHeight()+y_hover);
		}
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
}