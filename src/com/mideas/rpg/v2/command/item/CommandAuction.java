package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.auction.AuctionEntry;
import com.mideas.rpg.v2.game.auction.AuctionHouseLeftDuration;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.ItemType;
import com.mideas.rpg.v2.game.item.RequestItem;

public class CommandAuction extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.AUCTION_SEARCH_QUERY) {
			Mideas.joueur1().getAuctionHouse().clearQueryList();
			boolean itemFound = ConnectionManager.getConnection().readBoolean();
			if(!itemFound) {
				return;
			}
			byte amountResult = ConnectionManager.getConnection().readByte();
			int i = -1;
			while(++i < amountResult) {
				int entryID = ConnectionManager.getConnection().readInt();
				String sellerName = ConnectionManager.getConnection().readString();
				ItemType type = ItemType.values()[ConnectionManager.getConnection().readByte()];
				int itemID = ConnectionManager.getConnection().readInt();
				int amount = ConnectionManager.getConnection().readInt();
				int buyoutPrice = ConnectionManager.getConnection().readInt();
				int bidPrice = ConnectionManager.getConnection().readInt();
				AuctionHouseLeftDuration duration = AuctionHouseLeftDuration.values()[ConnectionManager.getConnection().readByte()];
				Item item;
				if((item = Item.getStoredItem(itemID)) == null) {
					Item.storeTempItem(type, itemID);
					item = Item.getStoredItem(itemID);
					CommandRequestItem.write(new RequestItem(itemID, DragItem.NONE, 0));
				}
				item.setAmount(amount);
				Mideas.joueur1().getAuctionHouse().addQueryItem(new AuctionEntry(entryID, sellerName, item, buyoutPrice, bidPrice, duration));
			}
		}
		else if(packetId == PacketID.AUCTION_CANCEL_SELL) {
			int entryID = ConnectionManager.getConnection().readInt();
			Mideas.joueur1().getAuctionHouse().removeSoldItem(entryID);
			//TODO: remove entry
		}
		else if(packetId == PacketID.AUCTION_INIT_SELL_ITEM) {
			Mideas.joueur1().getAuctionHouse().clearSoldItemList();
			short length = ConnectionManager.getConnection().readShort();
			int i = -1;
			while(++i < length) {
				int entryID = ConnectionManager.getConnection().readInt();
				ItemType type = ItemType.values()[ConnectionManager.getConnection().readByte()];
				int itemID = ConnectionManager.getConnection().readInt();
				int amount = ConnectionManager.getConnection().readInt();
				AuctionHouseLeftDuration duration = AuctionHouseLeftDuration.values()[ConnectionManager.getConnection().readByte()];
				int bidPrice = ConnectionManager.getConnection().readInt();
				int buyoutPrice = ConnectionManager.getConnection().readInt();
				String buyerName = ConnectionManager.getConnection().readString();
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
	
 	public static void makeABid(AuctionEntry entry, int bid) {
 		ConnectionManager.getConnection().startPacket();
 		ConnectionManager.getConnection().writeShort(PacketID.AUCTION);
 		ConnectionManager.getConnection().writeShort(PacketID.AUCTION_MAKE_BID);
 		ConnectionManager.getConnection().writeInt(entry.getEntryID());
 		ConnectionManager.getConnection().writeInt(bid);
 		ConnectionManager.getConnection().endPacket();
 		ConnectionManager.getConnection().send();
 	}
 	
 	public static void buyout(AuctionEntry entry) {
 		ConnectionManager.getConnection().startPacket();
 		ConnectionManager.getConnection().writeShort(PacketID.AUCTION);
 		ConnectionManager.getConnection().writeShort(PacketID.AUCTION_BUYOUT);
 		ConnectionManager.getConnection().writeInt(entry.getEntryID());
 		ConnectionManager.getConnection().endPacket();
 		ConnectionManager.getConnection().send();
 	}
}
