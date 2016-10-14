package com.mideas.rpg.v2.hud;

import org.lwjgl.opengl.Display;

import com.mideas.rpg.v2.Sprites;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.utils.Draw;

public class TradeFrame {

	private static String name = "";
	private static Item[] itemList = new Item[6];
	
	public static void draw() {
		Draw.drawQuad(Sprites.trade_frame, Display.getWidth()/2, Display.getHeight()/2);
	}
	
	public static boolean mouseEvent() {
		return false;
	}
	
	public static void setName(String names) {
		name = names;
	}
	
	public static void addItem(int id, int slot) {
		if(slot < itemList.length) {
			itemList[slot] = Item.getItem(id);
		}
	}
	
	public static void addItem(Item item, int slot) {
		if(item != null && slot < itemList.length) {
			itemList[slot] = item;
		}
	}
	
	public static Item getItem(int slot) {
		if(slot < itemList.length) {
			return itemList[slot];
		}
		return null;
	}
}
