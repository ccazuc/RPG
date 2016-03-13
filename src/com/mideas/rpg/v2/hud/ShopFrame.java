package com.mideas.rpg.v2.hud;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.utils.Draw;

public class ShopFrame {
	
	private static boolean button_hover;
	private static boolean[] slot_hover = new boolean[12];
	private static boolean left_arrow;
	private static boolean right_arrow;
	private static String shopPage = "shopPage1";
	private static Color bgColor = new Color(0, 0, 0,.8f); 
	
	public static void draw() throws LWJGLException, IOException {
		int xLeft = -279;
		int xRight = -114;
		int y = -275;
		int yShift = 52;
		Draw.drawQuad(Sprites.shop_frame, Display.getWidth()/2-300, Display.getHeight()/2-350);
		drawShopItem(0, xLeft, y);
		drawShopItem(1, xLeft, y+yShift);
		drawShopItem(2, xLeft, y+2*yShift);
		drawShopItem(3, xLeft, y+3*yShift);
		drawShopItem(4, xLeft, y+4*yShift);
		drawShopItem(5, xRight, y);
		drawShopItem(6, xRight, y+yShift);
		drawShopItem(7, xRight, y+2*yShift);
		drawShopItem(8, xRight, y+3*yShift);
		drawShopItem(9, xRight, y+4*yShift);
		if(button_hover) {
			Draw.drawQuad(Sprites.close_shop_hover, Display.getWidth()/2+27, Display.getHeight()/2-337);
		}
		//slotHover(slot_hover1, xLeft, y);
		/*if(slot_hover1) {
			Draw.drawQuad(Sprites.shop_hover, Display.getWidth()/2+xLeft, Display.getHeight()/2+y);
			Mideas.cursorFrame("sprite/interface/cursor_buy.png");
		}*/
		shopHover(1, xLeft, y+yShift);
		shopHover(2, xLeft, y+2*yShift);
		shopHover(3, xLeft, y+3*yShift);
		shopHover(4, xLeft, y+4*yShift);
		shopHover(5, xRight, y);
		shopHover(6, xRight, y+yShift);
		shopHover(7, xRight, y+2*yShift);
		shopHover(8, xRight, y+3*yShift);
		shopHover(9, xRight, y+4*yShift);
		//else if(!Mideas.getCursor().equals("sprite/interface/cursor.png") && !Interface.getContainerFrameStatus()) {
			//Mideas.cursorFrame("sprite/interface/cursor.png");
		//}
		if(!shopPage.equals("shopPage1")) {
			Draw.drawQuad(Sprites.left_colored_arrow, Display.getWidth()/2+xLeft+3, Display.getHeight()/2+y+268);
		}
		if(!shopPage.equals("shopPage3")) {
			Draw.drawQuad(Sprites.right_colored_arrow, Display.getWidth()/2+xRight+125, Display.getHeight()/2+y+268);
		}
		if(right_arrow && !shopPage.equals("shopPage3")) {
			Draw.drawQuad(Sprites.right_arrow_hover, Display.getWidth()/2+xRight+125, Display.getHeight()/2+y+268);
		}
		if(left_arrow && !shopPage.equals("shopPage1")) {
			Draw.drawQuad(Sprites.left_arrow_hover, Display.getWidth()/2+xLeft+3, Display.getHeight()/2+y+268);
		}
		if(shopPage.equals("shopPage3")) {
			Draw.drawQuad(Sprites.right_uncolored_arrow, Display.getWidth()/2+xRight+125, Display.getHeight()/2+y+268);
		}
		/*if(Mideas.shop.stuff1 != null) {
			calcCoin(Mideas.shop.stuff1.getPrice(), xLeft+49, y+35);
			if(slot_hover1) {
				Draw.drawColorQuad(Display.getWidth()+xLeft-300, Display.getHeight()/2-80, 150+TTF.itemName.getWidth(Mideas.shop.stuff1.getStuffName())/2, 80+TTF.statsName.getHeight()*4, bgColor);
				TTF.itemName.drawStringShadow(Display.getWidth()+xLeft-180-TTF.itemName.getWidth(Mideas.shop.stuff1.getStuffName())/2, Display.getHeight()/2+y-20, Mideas.shop.stuff1.getStuffName());
				TTF.statsName.drawStringShadow(Display.getWidth()+xLeft-295, Display.getHeight()/2+y, "+"+Mideas.shop.stuff1.getArmor()+" Armor");
				TTF.statsName.drawStringShadow(Display.getWidth()+xLeft-295, Display.getHeight()/2+y+20, "+"+Mideas.shop.stuff1.getStamina()+" Stamina");
				TTF.statsName.drawStringShadow(Display.getWidth()+xLeft-295, Display.getHeight()/2+y+40, "+"+Mideas.shop.stuff1.getMana()+" Mana");
				TTF.statsName.drawStringShadow(Display.getWidth()+xLeft-295, Display.getHeight()/2+y+60, "+"+Mideas.shop.stuff1.getStrengh()+" Strengh");
				TTF.statsName.drawStringShadow(Display.getWidth()+xLeft-295, Display.getHeight()/2+y+80, "+"+Mideas.shop.stuff1.getCritical()+" Critical");
			}
		}*/
		drawGoldCoin(0, xLeft, y);
		drawGoldCoin(1, xLeft, y+yShift);
		drawGoldCoin(2, xLeft, y+2*yShift);
		drawGoldCoin(3, xLeft, y+3*yShift);
		drawGoldCoin(4, xLeft, y+4*yShift);
		drawGoldCoin(5, xRight, y);
		drawGoldCoin(6, xRight, y+yShift);
		drawGoldCoin(7, xRight, y+2*yShift);
		drawGoldCoin(8, xRight, y+3*yShift);
		drawGoldCoin(9, xRight, y+4*yShift);
		if(Mideas.shop().getStuff(0) != null) {
			calcCoin(Mideas.shop().getStuff(0).getPrice(), xLeft+49, y+35);
			//shopItemsHover(slot_hover1, Mideas.shop().stuff1, xLeft, -80, -295, y, -180);
			shopLeftItemsHover(slot_hover[0], Mideas.shop().getStuff(0), xLeft, -568, -257, 110, -460, -280, -275, -410, -270, "");
			Draw.drawQuad(Sprites.cursor, -100, -100);
		}
		calcCoin(Mideas.joueur1().getGold(), xRight, y+250);
 	}

	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		setHoverShopFalse();
		Arrays.fill(slot_hover, false);
		int xLeft = -279;
		int xRight = -114;
		int y = -275;
		if(Mideas.mouseX() >= Display.getWidth()/2+xRight+126 && Mideas.mouseX() <= Display.getWidth()/2+xRight+151 && Mideas.mouseY() >= Display.getHeight()/2+y+266 && Mideas.mouseY() <= Display.getHeight()/2+y+292) {
			right_arrow = true;
		}
		else if(Mideas.mouseX() >= Display.getWidth()/2+xRight-161 && Mideas.mouseX() <= Display.getWidth()/2+xRight-136 && Mideas.mouseY() >= Display.getHeight()/2+y+266 && Mideas.mouseY() <= Display.getHeight()/2+y+292) {
			left_arrow = true;
		}
		if(Mouse.getEventButtonState()) {
			if(Mideas.mouseX() >= Display.getWidth()/2+27 && Mideas.mouseX() <= Display.getWidth()/2+46 && Mideas.mouseY() >= Display.getHeight()/2-337 && Mideas.mouseY() <= Display.getHeight()/2-319) {
				Interface.closeShopFrame();
				return true;
			}
			else if(shopPage.equals("shopPage1") && right_arrow) {
				shopPage = "shopPage2";
				Interface.setIsShopLoaded(false);
			}
			else if(shopPage.equals("shopPage2") && right_arrow) {
				shopPage = "shopPage3";
				Interface.setIsShopLoaded(false);
			}
			else if(shopPage.equals("shopPage2") && left_arrow) {
				shopPage = "shopPage1";
				Interface.setIsShopLoaded(false);
			}
			else if(shopPage.equals("shopPage3") && left_arrow) {
				shopPage = "shopPage2";
				Interface.setIsShopLoaded(false);
			}			
		}
		if(Mideas.mouseX() >= Display.getWidth()/2+27 && Mideas.mouseX() <= Display.getWidth()/2+46 && Mideas.mouseY() >= Display.getHeight()/2-337 && Mideas.mouseY() <= Display.getHeight()/2-319) {
			button_hover = true;
		}
		isSlotHover(xLeft, y, 0, 41, 0);
		isSlotHover(xLeft, y, 52, 93, 1);
		isSlotHover(xLeft, y, 104, 145, 2);
		isSlotHover(xLeft, y, 156, 197, 3);
		isSlotHover(xLeft, y, 208, 249, 4);
		isSlotHover(xRight, y, 0, 41, 5);
		isSlotHover(xRight, y, 52, 93, 6);
		isSlotHover(xRight, y, 104, 145, 7);
		isSlotHover(xRight, y, 156, 197, 8);
		isSlotHover(xRight, y, 208, 249, 9);	
		int i = 0;
		while(i < Mideas.shop().getstuff().length) {
			buyItems(slot_hover[i], Mideas.shop().getStuff(i));
			i++;
		}
		return false;
	}
	
	public static void keyboardEvent() {
		
	}
	
	public static boolean calcCoin(int cost, int x, int y) throws FileNotFoundException {
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+48+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost)+Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+38+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x+20+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+21+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))+String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) > 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcGoldCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.gold_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcGoldCoinCost(cost))), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) > 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 <= 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(Mideas.calcSilverCoinCost(cost)), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.silver_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(Mideas.calcSilverCoinCost(cost))), Display.getHeight()/2+y);
			return true;
		}
		if(Mideas.calcGoldCoinCost(cost) <= 0 && Mideas.calcSilverCoinCost(cost) <= 0 && cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100 > 0) {
			TTF2.coin.drawStringShadow(Display.getWidth()/2+x, Display.getHeight()/2+y, String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100), Color.white, Color.black, 1, 1, 1);
			Draw.drawQuad(Sprites.copper_coin, Display.getWidth()/2+x+1+TTF2.coin.getWidth(String.valueOf(cost-Mideas.calcGoldCoinCost(cost)*10000-Mideas.calcSilverCoinCost(cost)*100)), Display.getHeight()/2+y);
			return true;
		}
		return true;
	}
	
	private static boolean dropRate(Item item) throws FileNotFoundException, SQLException {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				Mideas.bag().setBag(i, item);
				SpellBarFrame.setBagChange(true);
				CharacterStuff.setBagItems();
				return true;
			}
			i++;
		}
		LogChat.setStatusText3("Votre inventaire est pleins");
		return false;
	}
	
	public static void buyItems(boolean slot_hover, Item stuff) throws FileNotFoundException, SQLException {
		if(Mouse.getEventButton() == 1 && slot_hover && stuff != null) {
			if(Mouse.getEventButtonState()) {
			}
			else {
				if(Mideas.getCurrentGold() >= stuff.getSellPrice()) {
					if(stuff.getItemType() == ItemType.POTION || stuff.getItemType() == ItemType.ITEM) {
						EndFightFrame.dropItem((Potion)stuff, 1);
						LogChat.setStatusText3("Vous avez bien acheté "+stuff.getStuffName());
						Mideas.setGold(-stuff.getSellPrice());
					}
					else if(dropRate(stuff)) {
						Mideas.setGold(-stuff.getSellPrice());
						LogChat.setStatusText3("Vous avez bien acheté "+stuff.getStuffName());
					}
				}
				else {
					LogChat.setStatusText3("Vous n'avez pas assez d'argent");
				}
			}
		}
	}
	
	public static void isSlotHover(int x, int y, int i, int j, int k) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+42 && Mideas.mouseY() >= Display.getHeight()/2+y+i && Mideas.mouseY() <= Display.getHeight()/2+y+j) {
			slot_hover[k] = true;
		}
	}
	
	public static void shopItemsHover(boolean slot_hover, Stuff stuff, int x_bg, int y_bg, int x_string, int y_string, int x_name) throws FileNotFoundException {
		if(stuff != null) {
			if(slot_hover) {
				Draw.drawColorQuad(Display.getWidth()+x_string+x_bg, Display.getHeight()/2+y_bg, 150+TTF2.itemName.getWidth(stuff.getStuffName())/2, y_bg+TTF2.statsName.getLineHeight()*4, ShopFrame.bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()+x_string+x_name+115-TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_string-20, stuff.getStuffName(), Color.white, Color.black, 1, 1, 1);;
				TTF2.statsName.drawStringShadow(Display.getWidth()+x_string+x_bg, Display.getHeight()/2+y_string, "+"+stuff.getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x_string+x_bg, Display.getHeight()/2+y_string+20, "+"+stuff.getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x_string+x_bg, Display.getHeight()/2+y_string+40, "+"+stuff.getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x_string+x_bg, Display.getHeight()/2+y_string+60, "+"+stuff.getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()+x_string+x_bg, Display.getHeight()/2+y_string+80, "+"+stuff.getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);
			}
		}
	}
	
	public static void shopLeftItemsHover(boolean hoverCharacter, Stuff stuff, int x_stuff_hover, int x_item, int y_item, int move_item_name, int x_bg, int y_bg, int y_stuff_hover, int x_item_name, int y_item_name, String base_name) {
		if(hoverCharacter) {
			if(stuff != null) {
				Draw.drawColorQuad(Display.getWidth()/2+x_bg-TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_bg, 150+TTF2.itemName.getWidth(stuff.getStuffName())/2, 80+TTF2.statsName.getLineHeight()*4, bgColor);
				TTF2.itemName.drawStringShadow(Display.getWidth()/2+x_item+move_item_name-TTF2.itemName.getWidth(stuff.getStuffName())/2, Display.getHeight()/2+y_item-20, stuff.getStuffName(), Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item, "+"+stuff.getArmor()+" Armor", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+20, "+"+stuff.getStamina()+" Stamina", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+40, "+"+stuff.getMana()+" Mana", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+60, "+"+stuff.getStrength()+" Strengh", Color.white, Color.black, 1, 1, 1);
				TTF2.statsName.drawStringShadow(Display.getWidth()/2+x_item, Display.getHeight()/2+y_item+80, "+"+stuff.getCritical()+" Critical", Color.white, Color.black, 1, 1, 1);		
			}
			Draw.drawQuad(Sprites.shop_hover, Display.getWidth()/2+x_stuff_hover, Display.getHeight()/2+y_stuff_hover);
		}
	}
	
	private static void drawGoldCoin(int i, int x, int y) throws FileNotFoundException {
		if(Mideas.shop().getStuff(i) != null) {
			calcCoin(Mideas.shop().getStuff(i).getPrice(), x+49, y+35);
			Draw.drawQuad(Sprites.cursor, -100, -100);
		}
	}
	
	private static void shopHover(int i, int x, int y) {
		if(slot_hover[i]) {
			Draw.drawQuad(Sprites.shop_hover, Display.getWidth()/2+x, Display.getHeight()/2+y);
		}
	}
	
	private static void drawShopItem(int i, int x, int y) {
		if(Mideas.shop().getStuff(i) != null) {
			Draw.drawQuad(IconsManager.getSprite35((Mideas.shop().getStuff(i).getSpriteId())), Display.getWidth()/2+x+3, Display.getHeight()/2+y+3);
			Draw.drawQuad(Sprites.shop_border, Display.getWidth()/2+x, Display.getHeight()/2+y);
		}
	}
	
	public static boolean[] getShopHover() {
		return slot_hover;
	}
	
	public static String getShopPage() {
		return shopPage;
	}
	public static void setHoverShopFalse() {
		button_hover = false;
		right_arrow = false;
		left_arrow = false;
	}
}