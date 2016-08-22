package com.mideas.rpg.v2.game;

import com.mideas.rpg.v2.game.item.Item;

public class Drop {

	private Item item;
	private int dropRate;
	private int amount;
	
	public Drop(Item item, int dropRate, int amount) {
		this.item = item;
		this.dropRate = dropRate;
		this.amount = amount;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public int getDropRate() {
		return this.dropRate;
	}
	
	public int getAmount() {
		return this.amount;
	}
}
