package com.mideas.rpg.v2.game.auction;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;

public class AuctionEntry {

	private final int entryID;
	private final Item item;
	private final int buyoutPrice;
	private final int bidPrice;
	private final String sellerName;
	private final boolean canBeBuy;
	private final AuctionHouseLeftDuration duration;
	private boolean locked;
	
	public AuctionEntry(int entryID, String sellerName, Item item, int buyoutPrice, int bidPrice, AuctionHouseLeftDuration duration) {
		this.entryID = entryID;
		this.sellerName = sellerName;
		this.item = item;
		this.buyoutPrice = buyoutPrice;
		this.bidPrice = bidPrice;
		this.canBeBuy = buyoutPrice > 0 && !sellerName.equals(Mideas.joueur1().getName());
		this.duration = duration;
	}
	
	public boolean canBeBuy() {
		return this.canBeBuy;
	}
	
	public void lock() {
		this.locked = true;
	}
	
	public boolean isLocked() {
		return this.locked;
	}
	
	public AuctionHouseLeftDuration getDuration() {
		return this.duration;
	}
	
	public int getEntryID() {
		return this.entryID;
	}
	
	public String getSellerName() {
		return this.sellerName;
	}
	
	public int getItemID() {
		return this.item.getId();
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public int getBuyoutPrice() {
		return this.buyoutPrice;
	}
	
	public int getBidPrice() {
		return this.bidPrice;
	}
}
