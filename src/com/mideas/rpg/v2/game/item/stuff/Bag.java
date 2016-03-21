package com.mideas.rpg.v2.game.item.stuff;

import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.potion.Potion;

public class Bag implements Cloneable {
	
	private Item[] bag = new Item[16];
	private Bag[] equippedBag = new Bag[4];
	private int id;
	private String sprite_id;
	private String name;
	private int size;
	
	private static HashMap<Item, Integer> numberStack = new HashMap<Item, Integer>();
	
	public Bag() {
		
	}
	
	public Bag(Bag bag) {
		this.id = bag.id;
		this.sprite_id = bag.sprite_id;
		this.name = bag.name;
		this.size = bag.size;
	}
	public Bag(int id, String sprite_id, String name, int size) {
		this.id = id;
		this.sprite_id = sprite_id;
		this.name = name;
		this.size = size;
	}
	
	public Item[] getBag() {
		return bag;
	}
	
	public Bag[] getEquippedBag() {
		return equippedBag;
	}
	
	public Bag getEquippedBag(int i) {
		return equippedBag[i];
	}
	
	public String getSpriteId(int i) {
		if(i < equippedBag.length) {
			return equippedBag[i].sprite_id;
		}
		return null;
	}
	
	public void setEquippedBag(int i, Bag bag) throws CloneNotSupportedException {
		if(i < equippedBag.length) {
			int length = Mideas.bag().getBag().length;
			Item[] tempBag = (Item[])this.bag.clone();
			if(equippedBag[i] != null) {
				int tempBagSize = equippedBag[i].size;
				equippedBag[i] = bag;
				if(tempBagSize >= equippedBag[i].size) {
					this.bag = new Item[length+(tempBagSize-equippedBag[i].size)];
				}
				else {
					this.bag = new Item[length+(equippedBag[i].size-tempBagSize)];
				}
			}
			else {
				equippedBag[i] = bag;
				this.bag = new Item[length+equippedBag[i].size];
			}
			int j = 0;
			while(j < tempBag.length && j < this.bag.length) {
				this.bag[j] = tempBag[j];
				j++;
			}
		}
	}
	
	public int getEquippedBagSize(int i) {
		if(i < equippedBag.length && equippedBag[i] != null) {
			return equippedBag[i].size;
		}
		return -1;
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
		if(i < bag.length && bag[i] != null) {
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
		if(i < bag.length) {
			bag[i] = stuff;
		}
	}
	
	public void setBag(int i, Item stuff, int number) {
		if(i < bag.length) {
			bag[i] = stuff;
			numberStack.put(stuff, number);
		}
	}
	
	public int getId() {
		return id;
	}
}
