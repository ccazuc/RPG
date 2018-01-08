package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;

public class CommandDragItems extends Command {

	@Override
	public void read() {
		
	}
	
	/*public static void write(DragItem sourceType, int source, DragItem destinationType, int destination) {
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.DRAG_ITEM);
		ConnectionManager.getWorldServerConnection().writeByte(sourceType.getValue());
		ConnectionManager.getWorldServerConnection().writeInt(source);
		ConnectionManager.getWorldServerConnection().writeByte(destinationType.getValue());
		ConnectionManager.getWorldServerConnection().writeInt(destination);
		ConnectionManager.getWorldServerConnection().writeInt(0);
		ConnectionManager.getWorldServerConnection().send();
	}*/
	
	public static void write(DragItem sourceType, int source, DragItem destinationType, int destination, int amount) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.DRAG_ITEM);
		ConnectionManager.getWorldServerConnection().writeByte(sourceType.getValue());
		ConnectionManager.getWorldServerConnection().writeInt(source);
		ConnectionManager.getWorldServerConnection().writeByte(destinationType.getValue());
		ConnectionManager.getWorldServerConnection().writeInt(destination);
		ConnectionManager.getWorldServerConnection().writeInt(amount);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
