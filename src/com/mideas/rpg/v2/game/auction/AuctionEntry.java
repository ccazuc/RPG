package com.mideas.rpg.v2.game.auction;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.unit.Joueur;
import com.mideas.rpg.v2.utils.StringUtils;

public class AuctionEntry {

	private final int entryID;
	private final Item item;
	private final int buyoutPrice;
	private final int bidPrice;
	private final String buyoutPriceString;
	private final String bidPriceString;
	private final String sellerName;
	private final boolean canBeBuy;
	private final boolean canBeBuyout;
	private final AuctionHouseLeftDuration duration;
	private final String buyoutGoldString;
	private final String buyoutSilverString;
	private final String buyoutCopperString;
	private final String bidGoldString;
	private final String bidSilverString;
	private final String bidCopperString;
	private final int bidGold;
	private final byte bidSilver;
	private final byte bidCopper;
	private boolean locked;
	
	public AuctionEntry(int entryID, String sellerName, Item item, int buyoutPrice, int bidPrice, AuctionHouseLeftDuration duration) {
		this.entryID = entryID;
		this.sellerName = sellerName;
		this.item = item;
		this.buyoutPrice = buyoutPrice;
		this.bidPrice = bidPrice;
		this.canBeBuy = buyoutPrice > 0 && !sellerName.equals(Mideas.joueur1().getName());
		this.canBeBuyout = buyoutPrice > 0;
		this.duration = duration;
		this.buyoutPriceString = String.valueOf(this.buyoutPrice);
		this.bidPriceString = String.valueOf(this.bidPrice);
		this.buyoutGoldString = String.valueOf(Joueur.getGoldPiece(this.buyoutPrice));
		this.buyoutSilverString = StringUtils.value[Joueur.getSilverPiece(this.buyoutPrice)];
		this.buyoutCopperString = StringUtils.value[Joueur.getCopperPiece(this.buyoutPrice)];
		this.bidGold = Joueur.getGoldPiece(this.bidPrice);
		this.bidGoldString = String.valueOf(this.bidGold);
		this.bidSilver = (byte)Joueur.getSilverPiece(this.bidPrice);
		this.bidSilverString = StringUtils.value[this.bidSilver];
		this.bidCopper = (byte)Joueur.getCopperPiece(this.bidPrice);
		this.bidCopperString = StringUtils.value[this.bidCopper];
	}
	
	public int getBidGold() {
		return this.bidGold;
	}
	
	public byte getBidSilver() {
		return this.bidSilver;
	}
	
	public byte getBidCopper() {
		return this.bidCopper;
	}
	
	public String getBuyoutGoldString() {
		return this.buyoutGoldString;
	}
	
	public String getBuyoutSilverString() {
		return this.buyoutSilverString;
	}
	
	public String getBuyoutCopperString() {
		return this.buyoutCopperString;
	}
	
	public String getBidGoldString() {
		return this.bidGoldString;
	}
	
	public String getBidSilverString() {
		return this.bidSilverString;
	}
	
	public String getBidCopperString() {
		return this.bidCopperString;
	}
	
	public boolean canBeBuy() {
		return this.canBeBuy;
	}
	
	public boolean canBeBuyout() {
		return this.canBeBuyout;
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
	
	public String getBuyoutPriceString() {
		return this.buyoutPriceString;
	}
	
	public String getBidPriceString() {
		return this.bidPriceString;
	}
}
