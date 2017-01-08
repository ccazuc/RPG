package com.mideas.rpg.v2.game.unit;

public enum TargetType {

	ARENA((byte)0),
	ARENA_PET((byte)1),
	FOCUS((byte)2),
	MOUSEOVER((byte)3),
	PARTY((byte)4),
	PARTY_PET((byte)5),
	PET((byte)6),
	PLAYER((byte)7),
	RAID((byte)8),
	RAID_PET((byte)9),
	TARGET((byte)10),
	VEHICULE((byte)11),
	
	;
	
	private byte value;
	
	private TargetType(byte value) {
		this.value = value;
	}
	
	public byte getValue() {
		return this.value;
	}
}
