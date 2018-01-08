package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;

public class CommandDeleteItem extends Command {

	@Override
	public void read() {
		DragItem type = DragItem.values()[ConnectionManager.getWorldServerConnection().readByte()];
		int slot = ConnectionManager.getWorldServerConnection().readInt();
		if(type == DragItem.BAG) {
			Mideas.joueur1().bag().setBag(slot, null);
		}
		else if(type == DragItem.INVENTORY) {
			Mideas.joueur1().setStuff(slot, null);
		}
	}
	
	public static void deleteItem(DragItem slotType, int slot) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.DELETE_ITEM);
		ConnectionManager.getWorldServerConnection().writeByte(slotType.getValue());
		ConnectionManager.getWorldServerConnection().writeInt(slot);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
