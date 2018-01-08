package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.hud.SelectScreen;

import static com.mideas.rpg.v2.connection.PacketID.DELETE_CHARACTER;

public class CommandDeleteCharacter extends Command {

	@Override
	public void read() {
		SelectScreen.characterDeleted();
		CommandSelectScreenLoadCharacters.write();
	}
	
	public static void write(int id) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(DELETE_CHARACTER);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
