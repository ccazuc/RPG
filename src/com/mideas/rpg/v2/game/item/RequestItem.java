package com.mideas.rpg.v2.game.item;

import com.mideas.rpg.v2.command.item.RequestItemSlotType;

public class RequestItem {

	private RequestItemSlotType slotType;
	private int slot;
	private int id;
	
	public RequestItem(int id, RequestItemSlotType slotType, int slot) {
		this.slotType = slotType;
		this.slot = slot;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public RequestItemSlotType getSlotType() {
		return this.slotType;
	}
	
	public int getSlot() {
		return this.slot;
	}
} 
