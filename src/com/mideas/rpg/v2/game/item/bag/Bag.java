package com.mideas.rpg.v2.game.item.bag;

import java.util.HashMap;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.gem.Gem;
import com.mideas.rpg.v2.game.item.stuff.Stuff;
import com.mideas.rpg.v2.hud.SpellBarFrame;

public class Bag extends Item implements Cloneable {
	
	private Item[] bag = new Item[16];
	private Container[] equippedBag = new Container[4];
	private static boolean bagChange = true;
	private static int numberFreeSlotBag;
	
	private static HashMap<Integer, Integer> itemList = new HashMap<Integer, Integer>();
	private static HashMap<Integer, String> itemListString = new HashMap<Integer, String>();
	
	public Bag() {}
	
	public void event() {
		if(bagChange) {
			updateBagItem();
			if(Mideas.joueur1() != null) {
				if(Mideas.joueur1().getFirstProfession() != null) {
					Mideas.joueur1().getFirstProfession().updateNumberPossibleCraft();
				}
				else if(Mideas.joueur1().getSecondProfession() != null) {
					Mideas.joueur1().getSecondProfession().updateNumberPossibleCraft();
				}
			}
			bagChange = false;
		}
		SpellBarFrame.setNumberFreeSlotBag(numberFreeSlotBag);
	}
	
	public Item[] getBag() {
		return this.bag;
	}
	
	public Container[] getEquippedBag() {
		return this.equippedBag;
	}
	
	public Container getEquippedBag(int i) {
		return this.equippedBag[i];
	}
	
	public int getNumberFreeSlotBag() {
		return numberFreeSlotBag;
	}
	
	public String getSpriteId(int i) {
		if(i < this.equippedBag.length) {
			return this.equippedBag[i].getSpriteId();
		}
		return null;
	}
	
	public void setEquippedBag(int i, Container bag) {
		if(i < this.equippedBag.length) {
			if(bag != null) {
				int length = Mideas.joueur1().bag().getBag().length;
				Item[] tempBag = this.bag.clone();
				if(this.equippedBag[i] != null) {
					int tempBagSize = this.equippedBag[i].getSize();
					this.equippedBag[i] = bag;
					if(tempBagSize >= this.equippedBag[i].getSize()) {
						this.bag = new Item[length+(tempBagSize-this.equippedBag[i].getSize())];
					}
					else {
						this.bag = new Item[length+(this.equippedBag[i].getSize()-tempBagSize)];
					}
				}
				else {
					this.equippedBag[i] = bag;
					this.bag = new Item[length+this.equippedBag[i].getSize()];
				}
				int j = 0;
				while(j < tempBag.length && j < this.bag.length) {
					this.bag[j] = tempBag[j];
					j++;
				}
			}
			else {
				this.equippedBag[i] = bag;
			}
		}
	}
	
	public int getEquippedBagSize(int i) {
		if(i < this.equippedBag.length && this.equippedBag[i] != null) {
			return this.equippedBag[i].getSize();
		}
		return 0;
	}
	
	public void updateBagItem() {
		numberFreeSlotBag = 0;
		itemList.clear();
		int i = 0;
		while(i < this.bag.length) {
			if(this.bag[i] != null) {
				if(itemList.containsKey(this.bag[i].getId())) {
					if(this.bag[i].isStackable()) {
						itemList.put(this.bag[i].getId(), this.bag[i].getAmount()+itemList.get(this.bag[i].getId()));
					}
					else {
						itemList.put(this.bag[i].getId(), itemList.get(this.bag[i].getId())+1);
					}
				}
				else {
					if(this.bag[i].isStackable()) {
						itemList.put(this.bag[i].getId(), this.bag[i].getAmount());
					}
					else {
						itemList.put(this.bag[i].getId(), 1);
					}
				}
			}
			else {
				numberFreeSlotBag++;
			}
			i++;
		}
	}
	
	public int getNumberItemInBags(int id) {
		if(itemList.containsKey(id)) {
			return itemList.get(id);
		}
		return 0;
	}
	
	public int getBagSlot(Item item) {
		if(itemList.containsKey(item.getId())) {
			int i = 0;
			while(i < this.bag.length) {
				if(this.bag[i] == item) {
					return i;
				}
				i++;
			}
		}
		return -1;
	}
	
	public String getNumberItemInBagsString(int id) {
		if(itemListString.containsKey(id)) {
			return itemListString.get(id);
		}
		return "0";
	}
	
	public HashMap<Integer, Integer> getItemList() {
		return itemList;
	}
	
	public int getItemNumber(Item item) {
		if(itemList.containsKey(item.getId())) {
			return itemList.get(item.getId());
		}
		return 0;
	}
	
	public Item getBag(int i) {
		if(i >= 0 && i < this.bag.length) {
			return this.bag[i];
		}
		System.out.println("Error on getBag slot "+i);
		return null;
	}
	
	public void setBag(int i, Item stuff) {
		if(i >= 0 && i < this.bag.length) {
			this.bag[i] = stuff;
			bagChange = true;
		}
		else {
			System.out.println("Error on setBag for slot :"+i);
		}
	}
	
	public void setBag(int i, Item stuff, int number) {
		if(i >= 0 && i < this.bag.length) {
			if(number <= 0) {
				this.bag[i] = null;
			}
			else {
				this.bag[i] = stuff;
				this.bag[i].setAmount(number);
			}
			bagChange = true;
		}
		else {
			System.out.println("Error on setBag for slot :"+i);
		}
	}
	
	public void updateBag(int slot, Item item) {
		if(item == null) {
			System.out.println("Error on updateBag for slot "+slot);
			return;
		}
		item.setIsLoaded(true);
		if(this.bag[slot] == null) {
			this.bag[slot] = item;
			return;
		}
		if((item.isStuff() || item.isWeapon()) && (this.bag[slot].isStuff() || this.bag[slot].isWeapon())) {
			int i = 0; 
			while(i < ((Stuff)this.bag[slot]).getEquippedGems().length) {
				((Stuff)item).setEquippedGem(i, ((Stuff)this.bag[slot]).getEquippedGem(i));
				i++;
			}
		}
		if(item.isStackable()) {
			
		}
		this.bag[slot] = item;
	}
	
	public void updateBagGem(int slot, int gemSlot, Item item) {
		if(item == null) {
			System.out.println("Error on updateBagGem for slot "+slot);
			return;
		}
		item.setIsLoaded(true);
		if(this.bag[slot] == null) {
			return;
		}
		if(item.isGem() && (this.bag[slot].isStuff() || this.bag[slot].isWeapon())) {
			((Stuff)this.bag[slot]).setEquippedGem(gemSlot, (Gem)item);
		}
	}
}
