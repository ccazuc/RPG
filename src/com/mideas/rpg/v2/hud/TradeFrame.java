package com.mideas.rpg.v2.hud;

import java.util.ArrayList;

import com.mideas.rpg.v2.game.item.Item;

public class TradeFrame {

	private static int characterId;
	private static String name = "";
	private static Item[] itemList = new Item[6];
	
	public static void draw() {
		//draw trade frame
		
	}
	
	public static boolean mouseEvent() {
		return false;
	}
	
	public static void setName(String names) {
		name = names;
	}
	
	public static void setCharacterId(int id) {
		characterId = id;
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
