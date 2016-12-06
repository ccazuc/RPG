package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandLoadCharacter extends Command {

	@Override
	public void read() {}
	
	public static void write(int id) {
		ConnectionManager.getConnection().writeShort(PacketID.LOAD_CHARACTER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
