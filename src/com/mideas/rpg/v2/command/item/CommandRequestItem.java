package com.mideas.rpg.v2.command.item;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandRequestItem extends Command {

	@Override
	public void read() {}
	
	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.REQUEST_ITEM);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
