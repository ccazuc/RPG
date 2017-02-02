package com.mideas.rpg.v2.game.auction;

public enum AuctionHouseLeftDuration {

	SHORT((byte)0, "Less than 30 mins"),
	MEDIUM((byte)1, "Between 30 mins and 2 hrs"),
	LONG((byte)2, "Between 2 hrs and 12 hrs"),
	VERY_LONG((byte)3, "Greater than 12 hrs"),
	
	;
	
	private final byte value;
	private final String duration;
	
	private AuctionHouseLeftDuration(byte value, String duration) {
		this.value = value;
		this.duration = duration;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public byte getValue() {
		return this.value;
	}
}
