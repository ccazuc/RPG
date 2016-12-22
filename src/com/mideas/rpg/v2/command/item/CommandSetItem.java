package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;
import com.mideas.rpg.v2.game.item.Item;

public class CommandSetItem extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.SET_ITEM_AMOUNT) {
			DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int slot = ConnectionManager.getConnection().readInt();
			int amount = ConnectionManager.getConnection().readInt();
			if(type == DragItem.BAG) {
				Mideas.joueur1().bag().getBag(slot).setAmount(amount);
			}
			else if(type == DragItem.BANK) {
				
			}
			else if(type == DragItem.GUILDBANK) {
				
			}
			else if(type == DragItem.INVENTORY) {
				
			}
		}
		else if(packetId == PacketID.SET_ITEM_NULL) {
			DragItem type = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int slot = ConnectionManager.getConnection().readInt();
			if(type == DragItem.BAG) {
				Mideas.joueur1().bag().setBag(slot, null);
			}
			else if(type == DragItem.BANK) {
				
			}
			else if(type == DragItem.GUILDBANK) {
				
			}
			else if(type == DragItem.INVENTORY) {
				
			}
		}
		else if(packetId == PacketID.SET_ITEM_SWAP) {
			DragItem sourceType = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int source = ConnectionManager.getConnection().readInt();
			DragItem destinationType = DragItem.values()[ConnectionManager.getConnection().readByte()];
			int destination = ConnectionManager.getConnection().readInt();
			Item tmp = null;
			if(sourceType == DragItem.BAG) {
				tmp = Mideas.joueur1().bag().getBag(source);
				Mideas.joueur1().bag().setBag(source, Mideas.joueur1().bag().getBag(destination));
				Mideas.joueur1().bag().setBag(destination, tmp);
				if(Mideas.joueur1().bag().getBag(source) != null) {
					Mideas.joueur1().bag().getBag(source).setIsSelectable(true);
				}
				if(Mideas.joueur1().bag().getBag(destination) != null) {
					Mideas.joueur1().bag().getBag(destination).setIsSelectable(true);
				}
			}
			else if(sourceType == DragItem.BANK) {
				
			}
			else if(sourceType == DragItem.GUILDBANK) {
				
			}
			else if(sourceType == DragItem.INVENTORY) {
				
			}
		}
	}
}
