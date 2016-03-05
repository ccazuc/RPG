package com.mideas.rpg.v2.game.item.potion;

import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;

public class Potion extends Item {

	protected int doHeal;
	protected int doMana;

	public Potion(Potion potion) {
		super(potion.id, potion.sprite_id, potion.itemType, potion.name, potion.sellPrice, potion.maxStack);
		this.doHeal = potion.doHeal;
		this.doMana = potion.doMana;
		
	}
	
	public Potion(int id, String sprite_id, String name, int doHeal, int doMana, int sellPrice) {
		super(id, sprite_id, ItemType.POTION, name, sellPrice, 200);
		this.doHeal = doHeal;
		this.doMana = doMana;
	}
	
	public int getPotionHeal() {
		return doHeal;
	}
	
	public int getPotionMana() {
		return doMana;
	}
}
