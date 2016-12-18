package com.mideas.rpg.v2.game.item;

import com.mideas.rpg.v2.command.item.RequestItemSlotType;

public class RequestItem {

	private int id;
	private RequestItemSlotType slot;
	
	public RequestItem(int id, RequestItemSlotType slot) {
		this.id = id;
		this.slot = slot;
	}
	
	public int getId() {
		return this.id;
	}
	
	public RequestItemSlotType getSlot() {
		return this.slot;
	}
} 
