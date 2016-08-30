package com.mideas.rpg.v2.game.item;

import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.BagManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.stuff.WeaponManager;

public class Item implements Cloneable {

	protected ItemType itemType;
	protected String sprite_id;
	protected int sellPrice;
	protected int maxStack;
	protected String name;
	protected int quality;
	protected int id;
	
	public Item(int id, String sprite_id, ItemType itemType, String name, int quality, int sellPrice, int maxStack) {
		this.sellPrice = sellPrice;
		this.sprite_id = sprite_id;
		this.maxStack = maxStack;
		this.itemType = itemType;
		this.quality = quality;
		this.name = name;
		this.id = id;
	}
	
	public Item() {}

	public int getId() {
		return this.id;
	}
	
	public String getSpriteId() {
		return this.sprite_id;
	}
	
	public boolean equals(Item item) {
		return item != null && item.getId() == this.id;
	}
	
	public String getStuffName() {
		return this.name;
	}
	
	public int getQuality() {
		return this.quality;
	}
	
	public int getSellPrice() {
		return this.sellPrice;
	}
	
	public ItemType getItemType() {
		return this.itemType;
	}
	
	public static Item getItem(int id) {
		if(BagManager.exists(id)) {
			return BagManager.getClone(id);
		}
		if(StuffManager.exists(id)) {
			return StuffManager.getClone(id);
		}
		if(WeaponManager.exists(id)) {
			return WeaponManager.getClone(id);
		}
		if(GemManager.exists(id)) {
			return GemManager.getClone(id);
		}
		if(PotionManager.exists(id)) {
			return PotionManager.getClone(id);
		}
		return null;
	}
	
	public static boolean exists(int id) {
		if(BagManager.exists(id)) {
			return true;
		}
		if(StuffManager.exists(id)) {
			return true;
		}
		if(WeaponManager.exists(id)) {
			return true;
		}
		if(GemManager.exists(id)) {
			return true;
		}
		if(PotionManager.exists(id)) {
			return true;
		}
		return false;
	}
}