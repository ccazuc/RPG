package com.mideas.rpg.v2.game.item;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.bag.BagManager;
import com.mideas.rpg.v2.game.item.gem.GemManager;
import com.mideas.rpg.v2.game.item.potion.PotionManager;
import com.mideas.rpg.v2.game.item.stuff.StuffManager;
import com.mideas.rpg.v2.game.item.weapon.WeaponManager;

public class Item implements Cloneable {

	protected ItemType itemType;
	protected String sprite_id;
	protected int sellPrice;
	protected int maxStack;
	protected String name;
	protected int quality;
	protected int id;
	protected boolean isLoaded;
	protected String deleteConfirm;
	
	private final static String delete = "Voulez vous supprimer ";
	
	public Item(int id, String sprite_id, ItemType itemType, String name, int quality, int sellPrice, int maxStack) {
		this.sellPrice = sellPrice;
		this.sprite_id = sprite_id;
		this.maxStack = maxStack;
		this.itemType = itemType;
		this.quality = quality;
		this.name = name;
		this.id = id;
		buildAllString();
	}
	
	public Item() {}

	private void buildAllString() {
		buildDeleteConfirm();
	}
	
	private void buildDeleteConfirm() {
		this.deleteConfirm = delete+this.name;
	}
	
	public String getDeleteConfirm() {
		return this.deleteConfirm;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setIsLoaded(boolean we) {
		this.isLoaded = we;
	}
	
	public boolean getIsLoaded() {
		return this.isLoaded;
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
	
	public static void requestItem(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.REQUEST_ITEM);
		ConnectionManager.getConnection().writeInt(id);
	}
	
	public boolean isStackable() {
		return this.itemType == ItemType.ITEM || this.itemType == ItemType.POTION;
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
	
	public boolean isStuff() {
		return this.itemType == ItemType.STUFF;
	}
	
	public boolean isWeapon() {
		return this.itemType == ItemType.WEAPON;
	}
	
	public boolean isItem() {
		return this.itemType == ItemType.ITEM;
	}
	
	public boolean isPotion() {
		return this.itemType == ItemType.POTION;
	}
	
	public boolean isGem() {
		return this.itemType == ItemType.GEM;
	}
	
	public boolean isContainer() {
		return this.itemType == ItemType.CONTAINER;
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