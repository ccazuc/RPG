package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.auction.AuctionHouseDuration;

public class CommandAuction extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.AUCTION_SEARCH_QUERY) {
			boolean itemFound = ConnectionManager.getConnection().readBoolean();
			if(!itemFound) {
				//TODO: set no result in AH frame
				return;
			}
			byte amountResult = ConnectionManager.getConnection().readByte();
			int i = -1;
			while(++i < amountResult) {
				int entryID = ConnectionManager.getConnection().readInt();
				String sellerName = ConnectionManager.getConnection().readString();
				int itemId = ConnectionManager.getConnection().readInt();
				int amount = ConnectionManager.getConnection().readInt();
				int buyoutPrice = ConnectionManager.getConnection().readInt();
				int bidPrice = ConnectionManager.getConnection().readInt();
				AuctionHouseDuration duration = AuctionHouseDuration.values()[ConnectionManager.getConnection().readByte()];
				
			}
		}
	}
}
