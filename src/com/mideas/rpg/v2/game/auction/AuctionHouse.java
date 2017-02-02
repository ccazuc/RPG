package com.mideas.rpg.v2.game.auction;

import java.util.ArrayList;

public class AuctionHouse {

	private final ArrayList<AuctionEntry> entryList;
	
	public AuctionHouse() {
		this.entryList = new ArrayList<AuctionEntry>();
	}
	
	public void clearList() {
		this.entryList.clear();
	}
	
	public void addItem(AuctionEntry entry) {
		this.entryList.add(entry);
	}
	
	public AuctionEntry getEntry(int entryID) {
		int i = this.entryList.size();
		while(--i >= 0) {
			if(this.entryList.get(i).getEntryID() == entryID) {
				return this.entryList.get(i);
			}
		}
		return null;
	}
}
