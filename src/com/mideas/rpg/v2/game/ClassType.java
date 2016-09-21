package com.mideas.rpg.v2.game;

public enum ClassType {

	DEATHKNIGHT(0),
	GUERRIER(1),
	HUNTER(2),
	MAGE(3),
	MONK(4),
	PALADIN(5),
	PRIEST(6),
	ROGUE(7),
	SHAMAN(8),
	WARLOCK(9);
	
	private int value;
	
	private ClassType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
