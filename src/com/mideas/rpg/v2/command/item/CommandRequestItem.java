package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.game.item.container.Container;

public class CommandRequestItem extends Command {

	@Override
	public void read() {
		boolean knownItem = ConnectionManager.getConnection().readBoolean();
		Item item = null;
		if(knownItem) {
			item = Item.getItem(ConnectionManager.getConnection().readInt());
		}
		else {
			item = ConnectionManager.getConnection().readItem();
		}
		DragItem slotType = DragItem.values()[ConnectionManager.getConnection().readByte()];
		int slot = ConnectionManager.getConnection().readInt();
		boolean isGem = ConnectionManager.getConnection().readBoolean();
		int gemSlot = 0;
		if(isGem) {
			gemSlot = ConnectionManager.getConnection().readInt();
			setGem(item, slotType, slot, gemSlot);
			return;
		}
		setItem(item, slotType, slot);
	}
	
	public static void write(RequestItem item) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.REQUEST_ITEM);
		ConnectionManager.getConnection().writeInt(item.getId());
		ConnectionManager.getConnection().writeByte(item.getSlotType().getValue());
		ConnectionManager.getConnection().writeInt(item.getSlot());
		if(item.getGemSlot() == -1) {
			ConnectionManager.getConnection().writeBoolean(false);
		}
		else {
			ConnectionManager.getConnection().writeBoolean(true);
			ConnectionManager.getConnection().writeInt(item.getGemSlot());
		}
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	private static void setItem(Item item, DragItem slotType, int slot) {
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
		else if(slotType == DragItem.NONE) {
			//do nothing
		}
		Item.storeItem(item);
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
