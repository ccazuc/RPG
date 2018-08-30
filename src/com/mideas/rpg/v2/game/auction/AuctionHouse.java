package com.mideas.rpg.v2.game.auction;

import java.util.ArrayList;

import com.mideas.rpg.v2.hud.auction.old.AuctionHouseFrame;

public class AuctionHouse {

	private final ArrayList<AuctionEntry> queryList;
	private final ArrayList<AuctionEntry> soldItemList;
	private final ArrayList<AuctionEntry> bidList;
	
	public AuctionHouse() {
		this.queryList = new ArrayList<AuctionEntry>();
		this.soldItemList = new ArrayList<AuctionEntry>();
		this.bidList = new ArrayList<AuctionEntry>();
	}
	
	public void clearQueryList() {
		this.queryList.clear();
	}
	
	public void queryReceived() {
		clearQueryList();
		AuctionHouseFrame.queryReceived();
	}
	
	public void clearSoldItemList() {
		this.soldItemList.clear();
	}
	
	public void addBidEntry(AuctionEntry entry) {
		this.bidList.add(entry);
	}
	
	public void addQueryItem(AuctionEntry entry) {
		this.queryList.add(entry);
	}
	
	public AuctionEntry getQueryEntry(int slot) {
		return this.queryList.get(slot);
	}
	
	public ArrayList<AuctionEntry> getQueryList() {
		return this.queryList;
	}
	
	public void addSoldItem(AuctionEntry entry) {
		this.soldItemList.add(entry);
	}
	
	public void removeSoldItem(int entryID) {
		int i = this.soldItemList.size();
		while(--i >= 0 ) {
			if(this.soldItemList.get(i).getEntryID() == entryID) {
				this.soldItemList.remove(i);
				return;
			}
		}
	}
	
	public AuctionEntry getEntry(int entryID) {
		int i = this.queryList.size();
		while(--i >= 0) {
			if(this.queryList.get(i).getEntryID() == entryID) {
				return this.queryList.get(i);
			}
		}
		return null;
	}
}
