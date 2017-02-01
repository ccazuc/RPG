package com.mideas.rpg.v2.game.redalert;

public enum DefaultRedAlert {

	ALREADY_CASTING((byte)0, "I'm already casting !"),
	STUNNED((byte)1, "I cannot do this while stunned."),
	DEAD((byte)2, "I cannot do this while dead."),
	CANNOT_EQUIP_ITEM((byte)3, "I cannot equip this item."),
	CANNOT_STACK_ITEM((byte)4, "This item cannot stack."),
	SPELL_NOT_READY_YET((byte)5, "This spell is not ready yet."),
	NOTHING_TO_ATTACK((byte)6, "You have no target."),
	NOT_ENOUGH_MANA(((byte)7), "I don't have enough mana to do this."),
	NOT_ENOUGH_GOLD(((byte)8), "I don't have enough gold."),
	MUST_MEET_MIN_BID(((byte)9), "You must meet min bid."),
	;
	
	private final byte value;
	private final String message;
	
	private DefaultRedAlert(byte value, String message) {
		this.message = message;
		this.value = value;
	}
	
	public byte getValue() {
		return this.value;
	}
	
	public String getMessage() {
		return this.message;
	}
}
