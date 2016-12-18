package com.mideas.rpg.v2.game.item;

import com.mideas.rpg.v2.command.item.RequestItemSlotType;

public class RequestItem {

	private RequestItemSlotType slotType;
	private int gemSlot;
	private int slot;
	private int id;
	
	public RequestItem(int id, RequestItemSlotType slotType, int slot, int gemSlot) {
		this.slotType = slotType;
		this.gemSlot = gemSlot;
		this.slot = slot;
		this.id = id;
	}
	
	public RequestItem(int id, RequestItemSlotType slotType, int slot) {
		this(id, slotType, slot, -1);
	}
	
	public RequestItemSlotType getSlotType() {
		return this.slotType;
	}
	
	public int getGemSlot() {
		return this.gemSlot;
	}
	
	public int getSlot() {
		return this.slot;
	}
	
	public int getId() {
		return this.id;
	}
} 
