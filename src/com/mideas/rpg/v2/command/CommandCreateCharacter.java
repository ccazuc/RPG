package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.CREATE_CHARACTER;

import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandCreateCharacter extends Command {
	
	@Override
	public void read() {
	}
	
	public static void write(String name) {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getConnection().writeByte(CREATE_CHARACTER);
			ConnectionManager.getConnection().writeString(name);
			ConnectionManager.getConnection().send();
		}
	}
}
