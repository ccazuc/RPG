package com.mideas.rpg.v2.game.auction;

public enum AuctionHouseLeftDuration {

	SHORT((byte)0, "Less than 30 mins", "Short"),
	MEDIUM((byte)1, "Between 30 mins and 2 hrs", "Medium"),
	LONG((byte)2, "Between 2 hrs and 12 hrs", "Long"),
	VERY_LONG((byte)3, "Greater than 12 hrs", "Very Long"),
	
	;
	
	private final byte value;
	private final String duration;
	private final String durationHovered;
	
	private AuctionHouseLeftDuration(byte value, String durationHovered, String duration) {
		this.value = value;
		this.duration = duration;
		this.durationHovered = durationHovered;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public String getDurationHovered() {
		return this.durationHovered;
	}
	
	public byte getValue() {
		return this.value;
	}
}
