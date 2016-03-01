package com.mideas.rpg.v2.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.TTF2;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.hud.LogChat;
import com.mideas.rpg.v2.jdo.JDOStatement;
import com.mideas.rpg.v2.utils.Draw;

public class ShopManager {
	
	private static boolean button_hover;
	private static boolean[] slot_hover = new boolean[12];
	private static boolean left_arrow;
	private static boolean right_arrow;

	private static ArrayList<Shop> shopList = new ArrayList<Shop>();
	
	public static void loadStuffs() throws SQLException, CloneNotSupportedException {
		JDOStatement statement = Mideas.getJDO().prepare("SELECT id, class, price FROM shop");
		statement.execute();
		while(statement.fetch()) {
			int id = statement.getInt();
			short classeTemp = statement.getShort();
			ClassType[] classeType = StuffManager.getClasses(classeTemp);
			int sellPrice = statement.getInt();
			shopList.add(new Shop(id, classeType, sellPrice));
		}
	}
	
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
		shopHover(0, xLeft, y);
		shopHover(1, xLeft, y+yShift);
		shopHover(2, xLeft, y+2*yShift);
		shopHover(3, xLeft, y+3*yShift);
		shopHover(4, xLeft, y+4*yShift);
		shopHover(5, xRight, y);
		shopHover(6, xRight, y+yShift);
		shopHover(7, xRight, y+2*yShift);
		shopHover(8, xRight, y+3*yShift);
		shopHover(9, xRight, y+4*yShift);
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
		calcCoin(Mideas.joueur1().getGold(), xRight, y+250);
	}
	
	public static boolean mouseEvent() throws FileNotFoundException, SQLException {
		Arrays.fill(slot_hover, false);
		int xLeft = -279;
		int xRight = -114;
		int y = -275;
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
		while(i < shopList.size()) {
			buyItems(slot_hover[i], shopList.get(i));
			i++;
		}
		return false;
	}
	
	public static void buyItems(boolean slot_hover, Shop stuff) throws FileNotFoundException, SQLException {
		if(Mouse.getEventButton() == 1 && slot_hover && stuff != null) {
			if(Mouse.getEventButtonState()) {
			}
			else {
				if(Mideas.getCurrentGold() >= stuff.getSellPrice()) {
					/*if(stuff instanceof Item) {
						EndFightFrame.dropItem(stuff, 1);
						LogChat.setStatusText3("Vous avez bien acheté "+stuff.getStuffName());
						Mideas.setGold(-stuff.getSellPrice());
					}*/
					if(checkInventory(stuff)) {
						Mideas.setGold(-stuff.getSellPrice());
						LogChat.setStatusText3("Vous avez bien acheté "+StuffManager.getStuff(stuff.getId()).getStuffName());
					}
				}
				else {
					LogChat.setStatusText3("Vous n'avez pas assez d'argent");
				}
			}
		}
	}

	private static boolean checkInventory(Shop item) throws FileNotFoundException, SQLException {
		int i = 0;
		while(i < Mideas.bag().getBag().length) {
			if(Mideas.bag().getBag(i) == null) {
				Mideas.bag().setBag(i, StuffManager.getStuff(item.getId()));
				CharacterStuff.setBagItems();
				return true;
			}
			i++;
		}
		LogChat.setStatusText3("Votre inventaire est pleins");
		return false;
	}

	private static void drawShopItem(int i, int x, int y) {
		if(i < shopList.size() && shopList.get(i) != null && StuffManager.getStuff(shopList.get(i).getId()) != null) {
			Draw.drawQuad(IconsManager.getSprite35(StuffManager.getStuff(shopList.get(i).getId()).getSpriteId()), Display.getWidth()/2+x+3, Display.getHeight()/2+y+3);
		}
	}
	
	private static void shopHover(int i, int x, int y) {
		if(slot_hover[i]) {
			Draw.drawQuad(Sprites.shop_hover, Display.getWidth()/2+x, Display.getHeight()/2+y);
		}
	}
	
	public static void isSlotHover(int x, int y, int i, int j, int k) {
		if(Mideas.mouseX() >= Display.getWidth()/2+x && Mideas.mouseX() <= Display.getWidth()/2+x+42 && Mideas.mouseY() >= Display.getHeight()/2+y+i && Mideas.mouseY() <= Display.getHeight()/2+y+j) {
			slot_hover[k] = true;
		}
	}

	private static void drawGoldCoin(int i, int x, int y) throws FileNotFoundException {
		if(i < shopList.size()) {
			calcCoin(shopList.get(i).getSellPrice(), x+49, y+35);
			Draw.drawQuad(Sprites.cursor, -100, -100);
		}
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
}
