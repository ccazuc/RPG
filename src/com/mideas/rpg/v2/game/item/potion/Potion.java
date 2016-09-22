package com.mideas.rpg.v2.game.item.potion;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Potion extends Item {

	protected int doHeal;
	protected int doMana;
	protected int level;
	protected boolean isLoaded;
	
	public Potion(int id) {
		super(id, "", ItemType.POTION, "", 1, 0, 1);
		this.id = id;
	}

	public Potion(Potion potion) {
		super(potion.id, potion.sprite_id, potion.itemType, potion.name, 1, potion.sellPrice, potion.maxStack);
		this.doHeal = potion.doHeal;
		this.doMana = potion.doMana;
		this.isLoaded = true;
	}
	
	public Potion(int id, String sprite_id, String name, int level, int doHeal, int doMana, int sellPrice) {
		super(id, sprite_id, ItemType.POTION, name, 1, sellPrice, 200);
		this.doHeal = doHeal;
		this.doMana = doMana;
		this.level = level;
		this.isLoaded = true;
	}
	
	public boolean getIsLoaded() {
		return this.isLoaded;
	}
	
	public void setIsLoaded(boolean we) {
		this.isLoaded = we;
	}
	
	public int getPotionHeal() {
		return this.doHeal;
	}
	
	public int getPotionMana() {
		return this.doMana;
	}
	
	public int getLevel() {
		return this.level;
	}
}
