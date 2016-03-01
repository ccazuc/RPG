package com.mideas.rpg.v2.game.item.stuff;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;

public class Bag {
	
	private Item[] bag = new Item[33];
	private int[] numberItem = new int[33];
	
	public Item[] getBag() {
		return bag;
	}
	
	public int getNumberBagItem(int i) {
		return numberItem[i];
	}
	
	public void setNumberBagItem(int i, int number) {
		numberItem[i]+= number;
	}
	
	public Item getBag(int i) {
		if(bag[i] != null) {
			if(bag[i].getItemType() == ItemType.STUFF) {
			return (Stuff)bag[i];
			}
			else if(bag[i].getItemType() == ItemType.POTION) {
				return (Potion)bag[i];
			}
			else if(bag[i].getItemType() == ItemType.ITEM) {
				return (Item)bag[i];
			}
		}
		return null;
	}
	
	public void setBag(int i, Item stuff) {
		bag[i] = stuff;
	}
	
	public void setBag(int i, Item stuff, int number) {
		bag[i] = stuff;
		numberItem[i] = number;
	}
}
