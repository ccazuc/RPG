package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.game.item.container.Container;
import com.mideas.rpg.v2.hud.auction.old.AuctionHouseFrame;

public class CommandRequestItem extends Command {

	@Override
	public void read() {
		boolean knownItem = ConnectionManager.getWorldServerConnection().readBoolean();
		Item item = null;
		if(knownItem) {
			item = Item.getItem(ConnectionManager.getWorldServerConnection().readInt());
		}
		else {
			item = ConnectionManager.getWorldServerConnection().readItem();
		}
		DragItem slotType = DragItem.values()[ConnectionManager.getWorldServerConnection().readByte()];
		int slot = ConnectionManager.getWorldServerConnection().readInt();
		boolean isGem = ConnectionManager.getWorldServerConnection().readBoolean();
		int gemSlot = 0;
		if(isGem) {
			gemSlot = ConnectionManager.getWorldServerConnection().readInt();
			setGem(item, slotType, slot, gemSlot);
			return;
		}
		setItem(item, slotType, slot);
	}
	
	public static void write(RequestItem item) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.REQUEST_ITEM);
		ConnectionManager.getWorldServerConnection().writeInt(item.getId());
		ConnectionManager.getWorldServerConnection().writeByte(item.getSlotType().getValue());
		ConnectionManager.getWorldServerConnection().writeInt(item.getSlot());
		if(item.getGemSlot() == -1) {
			ConnectionManager.getWorldServerConnection().writeBoolean(false);
		}
		else {
			ConnectionManager.getWorldServerConnection().writeBoolean(true);
			ConnectionManager.getWorldServerConnection().writeInt(item.getGemSlot());
		}
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	private static void setItem(Item item, DragItem slotType, int slot) {
		Item.storeItem(item);
		if(slotType == DragItem.BANK) {
			
		}
		else if(slotType == DragItem.INVENTORY) {
			Mideas.joueur1().updateStuff(slot, item);
		}
		else if(slotType == DragItem.BAG) {
			Mideas.joueur1().bag().updateBag(slot, item);
		}
		else if(slotType == DragItem.TRADE) {
			
		}
		else if(slotType == DragItem.GUILDBANK) {
			
		}
		else if(slotType == DragItem.EQUIPPED_CONTAINER) {
			Mideas.joueur1().bag().setEquippedBag(slot, (Container)item);
		}
		else if(slotType == DragItem.AUCTION_HOUSE_BROWSE) {
			AuctionHouseFrame.updateUnloadedBrowseItem(item.getId());
		}
	}
	
	private static void setGem(Item item, DragItem slotType, int slot, int gemSlot) {
		Item.storeItem(item);
		if(slotType == DragItem.BANK) {
			
		}
		else if(slotType == DragItem.INVENTORY) {
			Mideas.joueur1().updateStuffGem(slot, gemSlot, item);
		}
		else if(slotType == DragItem.BAG) {
			Mideas.joueur1().bag().updateBagGem(slot, gemSlot, item);
		}
		else if(slotType == DragItem.TRADE) {
			
		}
		else if(slotType == DragItem.GUILDBANK) {
			
		}
	}
}
