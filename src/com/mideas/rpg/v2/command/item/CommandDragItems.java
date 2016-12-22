package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.item.DragItem;

public class CommandDragItems extends Command {

	@Override
	public void read() {
		
	}
	
	public static void write(DragItem sourceType, int source, DragItem destinationType, int destination) {
		ConnectionManager.getConnection().writeShort(PacketID.DRAG_ITEM);
		ConnectionManager.getConnection().writeByte(sourceType.getValue());
		ConnectionManager.getConnection().writeInt(source);
		ConnectionManager.getConnection().writeByte(destinationType.getValue());
		ConnectionManager.getConnection().writeInt(destination);
		ConnectionManager.getConnection().writeInt(0);
		ConnectionManager.getConnection().send();
	}
	
	public static void write(DragItem sourceType, int source, DragItem destinationType, int destination, int amount) {
		ConnectionManager.getConnection().writeShort(PacketID.DRAG_ITEM);
		ConnectionManager.getConnection().writeByte(sourceType.getValue());
		ConnectionManager.getConnection().writeInt(source);
		ConnectionManager.getConnection().writeByte(destinationType.getValue());
		ConnectionManager.getConnection().writeInt(destination);
		ConnectionManager.getConnection().writeInt(amount);
		ConnectionManager.getConnection().send();
	}
}
