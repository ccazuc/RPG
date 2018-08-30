package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.auction.AuctionEntry;
import com.mideas.rpg.v2.game.auction.AuctionHouseLeftDuration;
import com.mideas.rpg.v2.game.auction.AuctionHouseSort;
import com.mideas.rpg.v2.game.auction.AuctionHouseFilter;
import com.mideas.rpg.v2.game.auction.AuctionHouseQualityFilter;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.hud.auction.old.AuctionHouseFrame;

public class CommandAuction extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.AUCTION_SEARCH_QUERY) {
			Mideas.joueur1().getAuctionHouse().queryReceived();
			boolean itemFound = ConnectionManager.getWorldServerConnection().readBoolean();
			if(!itemFound) {
				AuctionHouseFrame.noItemFound();
				return;
			}
			int amountTotalResult = ConnectionManager.getWorldServerConnection().readInt();
			short page = ConnectionManager.getWorldServerConnection().readShort();
			byte amountResult = ConnectionManager.getWorldServerConnection().readByte();
			AuctionHouseFrame.buildResultString(amountResult, amountTotalResult, page, (short)Math.ceil(amountTotalResult/50));
			int i = -1;
			while(++i < amountResult) {
				int entryID = ConnectionManager.getWorldServerConnection().readInt();
				String sellerName = ConnectionManager.getWorldServerConnection().readString();
				ItemType type = ItemType.values()[ConnectionManager.getWorldServerConnection().readByte()];
				int itemID = ConnectionManager.getWorldServerConnection().readInt();
				int amount = ConnectionManager.getWorldServerConnection().readInt();
				int buyoutPrice = ConnectionManager.getWorldServerConnection().readInt();
				int bidPrice = ConnectionManager.getWorldServerConnection().readInt();
				AuctionHouseLeftDuration duration = AuctionHouseLeftDuration.values()[ConnectionManager.getWorldServerConnection().readByte()];
				Item item;
				if((item = Item.getStoredItem(itemID)) == null) {
					Item.storeTempItem(type, itemID);
					item = Item.getStoredItem(itemID);
					CommandRequestItem.write(new RequestItem(itemID, DragItem.AUCTION_HOUSE_BROWSE, 0));
				}
				item.setAmount(amount);
				Mideas.joueur1().getAuctionHouse().addQueryItem(new AuctionEntry(entryID, sellerName, item, buyoutPrice, bidPrice, duration));
			}
			AuctionHouseFrame.updateQueryButtonList();
		}
		else if(packetId == PacketID.AUCTION_CANCEL_SELL) {
			int entryID = ConnectionManager.getWorldServerConnection().readInt();
			Mideas.joueur1().getAuctionHouse().removeSoldItem(entryID);
			//TODO: remove entry
		}
		else if(packetId == PacketID.AUCTION_INIT_SELL_ITEM) {
			Mideas.joueur1().getAuctionHouse().clearSoldItemList();
			short length = ConnectionManager.getWorldServerConnection().readShort();
			int i = -1;
			while(++i < length) {
				int entryID = ConnectionManager.getWorldServerConnection().readInt();
				ItemType type = ItemType.values()[ConnectionManager.getWorldServerConnection().readByte()];
				int itemID = ConnectionManager.getWorldServerConnection().readInt();
				int amount = ConnectionManager.getWorldServerConnection().readInt();
				AuctionHouseLeftDuration duration = AuctionHouseLeftDuration.values()[ConnectionManager.getWorldServerConnection().readByte()];
				int bidPrice = ConnectionManager.getWorldServerConnection().readInt();
				int buyoutPrice = ConnectionManager.getWorldServerConnection().readInt();
				String buyerName = ConnectionManager.getWorldServerConnection().readString();
				Item item;
				if((item = Item.getStoredItem(itemID)) == null) {
					Item.storeTempItem(type, itemID);
					item = Item.getStoredItem(itemID);
					CommandRequestItem.write(new RequestItem(itemID, DragItem.NONE, 0));
				}
				item.setAmount(amount);
				Mideas.joueur1().getAuctionHouse().addSoldItem(new AuctionEntry(entryID, buyerName, item, buyoutPrice, bidPrice, duration));
			}
		}
	}
	
	public static void sendQuery(AuctionHouseFilter filter, AuctionHouseSort sort, AuctionHouseQualityFilter qualityFilter, String word, short page, boolean isUsable, short minLevel, short maxLevel, boolean exactWord) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.AUCTION);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.AUCTION_SEARCH_QUERY);
		ConnectionManager.getWorldServerConnection().writeByte(filter.getValue());
		ConnectionManager.getWorldServerConnection().writeByte(qualityFilter.getValue());
		ConnectionManager.getWorldServerConnection().writeByte(sort.getValue());
		ConnectionManager.getWorldServerConnection().writeShort(page);
		ConnectionManager.getWorldServerConnection().writeBoolean(isUsable);
		ConnectionManager.getWorldServerConnection().writeShort(minLevel);
		ConnectionManager.getWorldServerConnection().writeShort(maxLevel);
		ConnectionManager.getWorldServerConnection().writeBoolean(exactWord);
		ConnectionManager.getWorldServerConnection().writeString(word);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
 	public static void makeABid(AuctionEntry entry, int bid) {
 		ConnectionManager.getWorldServerConnection().startPacket();
 		ConnectionManager.getWorldServerConnection().writeShort(PacketID.AUCTION);
 		ConnectionManager.getWorldServerConnection().writeShort(PacketID.AUCTION_MAKE_BID);
 		ConnectionManager.getWorldServerConnection().writeInt(entry.getEntryID());
 		ConnectionManager.getWorldServerConnection().writeInt(bid);
 		ConnectionManager.getWorldServerConnection().endPacket();
 		ConnectionManager.getWorldServerConnection().send();
 	}
 	
 	public static void buyout(AuctionEntry entry) {
 		ConnectionManager.getWorldServerConnection().startPacket();
 		ConnectionManager.getWorldServerConnection().writeShort(PacketID.AUCTION);
 		ConnectionManager.getWorldServerConnection().writeShort(PacketID.AUCTION_BUYOUT);
 		ConnectionManager.getWorldServerConnection().writeInt(entry.getEntryID());
 		ConnectionManager.getWorldServerConnection().endPacket();
 		ConnectionManager.getWorldServerConnection().send();
 	}
}
