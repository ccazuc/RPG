package com.mideas.rpg.v2.game.item.stuff;

import java.util.HashMap;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;

public class Bag {
	
	private Item[] bag = new Item[33];
	private int[] numberItem = new int[33];
	private static HashMap<Item, Integer> numberStack = new HashMap<Item, Integer>();
	
	public Item[] getBag() {
		return bag;
	}
	
	public HashMap<Item, Integer> getNumberStack() {
		return numberStack;
	}
	
	public Item getEquals(Item item) {
		int i = 0;
		while(i < numberStack.size()) {
			if(numberStack.get(i) != null && numberStack.get(i).equals(item)) {
				return getKey(item);
			}
			i++;
		}
		return null;
	}
	
	public static Item getKey(Item item){
	    for(Item key : numberStack.keySet()) {
	       if(key.getId() == item.getId()) {
	        	return key;
	        }
	    }
	    return null;
	}
	
	public int getNumberBagItem(Item item) {
		return numberStack.get(item);
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
		numberStack.put(stuff, number);
	}
}
