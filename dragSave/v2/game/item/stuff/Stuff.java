package com.mideas.rpg.v2.game.item.stuff;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Stuff extends Item {

	protected StuffType type;
	protected ClassType[] classType;
	protected int critical;
	protected int strength;
	protected int stamina;
	protected int price;
	protected int armor;
	protected int mana;

	public Stuff(Stuff stuff) {
		super(stuff.id, stuff.sprite_id, stuff.itemType, stuff.name, stuff.sellPrice, stuff.maxStack);
		this.type = stuff.type;
		this.classType = stuff.classType;
		this.critical = stuff.critical;
		this.strength = stuff.strength;
		this.stamina = stuff.stamina;
		this.armor = stuff.armor;
		this.mana = stuff.mana;
	}
	
	public Stuff(StuffType type, ClassType[] classType, String sprite_id, int id, String name, int critical, int strength, int stamina, int armor, int mana, int sellPrice) {
		super(id, sprite_id, ItemType.STUFF, name, sellPrice, 1);
		this.type = type;
		this.classType = classType;
		this.critical = critical;
		this.strength = strength;
		this.stamina = stamina;
		this.armor = armor;
		this.mana = mana;
	}
	
	public int getCritical() {
		return critical;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public int getArmor() {
		return armor;
	}
	
	public int getMana() {
		return mana;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getSpriteId() {
		return sprite_id;
	}
	
	public StuffType getType() {
		return this.type;
	}
	
	public ClassType[] getClassType() {
		return classType;
	}
	
	public ClassType getClassType(int i) {
		return classType[i];
	}
	
	public boolean isHead() {
		return type == StuffType.HEAD;
	}
	
	public boolean canEquipTo(ClassType type) {
		int i = 0;
		while(i < classType.length) {
			if(type == classType[i]) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public boolean isNecklace() {
		return type == StuffType.NECKLACE;
	}
	
	public boolean isShoulders() {
		return type == StuffType.SHOULDERS;
	}
	
	public boolean isChest() {
		return type == StuffType.CHEST;
	}
	
	public boolean isBack() {
		return type == StuffType.BACK;
	}
	
	public boolean isWrists() {
		return type == StuffType.WRISTS;
	}
	
	public boolean isGloves() {
		return type == StuffType.GLOVES;
	}
	
	public boolean isBelt() {
		return type == StuffType.BELT;
	}
	
	public boolean isLeggings() {
		return type == StuffType.LEGGINGS;
	}
	
	public boolean isBoots() {
		return type == StuffType.BOOTS;
	}
	
	public boolean isRing() {
		return type == StuffType.RING;
	}
	
	public boolean isTrinket() {
		return type == StuffType.TRINKET;
	}
	
	public boolean isMainHand() {
		return type == StuffType.MAINHAND;
	}
	
	public boolean isOffHand() {
		return type == StuffType.OFFHAND;
	}
	
	public boolean isRanged() {
		return type == StuffType.RANGED;
	}
	
	public boolean equals(Stuff item) {
		return item != null && item.getId() == id;
	}
	
	public String getStuffName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}

	@Override
	public int getSellPrice() {
		return sellPrice;
	}
}
