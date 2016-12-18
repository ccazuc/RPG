package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.RequestItem;

public class CommandRequestItem extends Command {

	@Override
	public void read() {
		Item item = ConnectionManager.getConnection().readItem();
		RequestItemSlotType slotType = RequestItemSlotType.values()[ConnectionManager.getConnection().readByte()];
		int slot = ConnectionManager.getConnection().readInt();
		setItem(item, slotType, slot);
	}
	
	public static void write(RequestItem item) {
		ConnectionManager.getConnection().writeShort(PacketID.REQUEST_ITEM);
		ConnectionManager.getConnection().writeInt(item.getId());
		ConnectionManager.getConnection().writeByte(item.getSlotType().getValue());
		ConnectionManager.getConnection().writeInt(item.getSlot());
		ConnectionManager.getConnection().send();
	}
	
	private static void setItem(Item item, RequestItemSlotType slotType, int slot) {
		if(slotType == RequestItemSlotType.BANK) {
			
		}
		else if(slotType == RequestItemSlotType.CHARACTER) {
			Mideas.joueur1().updateStuff(slot, item);
		}
		else if(slotType == RequestItemSlotType.CONTAINER) {
			Mideas.joueur1().bag().updateBagItem(slot, item);
		}
		else if(slotType == RequestItemSlotType.DISPLAYED) {
			
		}
		else if(slotType == RequestItemSlotType.GUILDBANK) {
			
		}
	}
}
