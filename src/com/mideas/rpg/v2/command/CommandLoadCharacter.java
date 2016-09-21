package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.LOAD_EQUIPPED_ITEMS;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandLoadCharacter extends Command {

	@Override
	public void read() {
		byte id = ConnectionManager.getConnection().readByte();
		if(id == LOAD_EQUIPPED_ITEMS) {
			ConnectionManager.getCommandList().get(LOAD_EQUIPPED_ITEMS).read();
			id = -1;
		}
	}
	
	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.LOAD_CHARACTER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}

}
