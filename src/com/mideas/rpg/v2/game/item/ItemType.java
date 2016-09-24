package com.mideas.rpg.v2.game.item;

public enum ItemType {

	STUFF((char)0),
	ITEM((char)1),
	POTION((char)2),
	BAG((char)3),
	WEAPON((char)4),
	GEM((char)5);
	
	private char value;
	
	private ItemType(char value) {
		this.value = value;
	}
	
	public char getValue() {
		return this.value;
	}
}