package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.Item;
import com.mideas.rpg.v2.game.item.RequestItem;
import com.mideas.rpg.v2.game.item.stuff.Stuff;

public class CommandRequestItem extends Command {

	@Override
	public void read() {
		Item item = ConnectionManager.getConnection().readItem();
		RequestItemSlotType slotType = RequestItemSlotType.values()[ConnectionManager.getConnection().readByte()];
		int slot = ConnectionManager.getConnection().readInt();
		boolean isGem = ConnectionManager.getConnection().readBoolean();
		int gemSlot = 0;
		if(isGem) {
			gemSlot = ConnectionManager.getConnection().readInt();
			System.out.println(item.getStuffName()+" "+slotType+" "+slot+" "+isGem+" "+gemSlot+" "+item.getSpriteId());
			setGem(item, slotType, slot, gemSlot);
			return;
		}
		System.out.println(item.getStuffName()+" "+slotType+" "+slot+" "+isGem+" "+gemSlot);
		setItem(item, slotType, slot);
	}
	
	public static void write(RequestItem item) {
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
		ConnectionManager.getConnection().send();
	}
	
	private static void setItem(Item item, RequestItemSlotType slotType, int slot) {
		if(slotType == RequestItemSlotType.BANK) {
			
		}
		else if(slotType == RequestItemSlotType.CHARACTER) {
			Mideas.joueur1().updateStuff(slot, item);
		}
		else if(slotType == RequestItemSlotType.CONTAINER) {
			Mideas.joueur1().bag().updateBag(slot, item);
		}
		else if(slotType == RequestItemSlotType.DISPLAYED) {
			
		}
		else if(slotType == RequestItemSlotType.GUILDBANK) {
			
		}
	}
	
	private static void setGem(Item item, RequestItemSlotType slotType, int slot, int gemSlot) {
		if(slotType == RequestItemSlotType.BANK) {
			
		}
		else if(slotType == RequestItemSlotType.CHARACTER) {
			Mideas.joueur1().updateStuffGem(slot, gemSlot, item);
		}
		else if(slotType == RequestItemSlotType.CONTAINER) {
			Mideas.joueur1().bag().updateBagGem(slot, gemSlot, item);
		}
		else if(slotType == RequestItemSlotType.DISPLAYED) {
			
		}
		else if(slotType == RequestItemSlotType.GUILDBANK) {
			
		}
	}
}
