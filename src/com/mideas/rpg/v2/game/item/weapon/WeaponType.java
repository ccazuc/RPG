package com.mideas.rpg.v2.game.item.weapon;

public enum WeaponType {

	ONEHANDEDAXE(0),
	TWOHANDEDAXE(1),
	ONEHANDEDSWORD(2),
	TWOHANDEDSWORD(3),
	ONEHANDEDMACE(4),
	TWOHANDEDMACE(5),
	POLEARM(6),
	STAFF(7),
	DAGGER(8),
	FISTWEAPON(9),
	BOW(10),
	CROSSBOW(11),
	GUN(12),
	THROWN(13),
	WAND(14);
	
	private int value;
	
	private WeaponType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
