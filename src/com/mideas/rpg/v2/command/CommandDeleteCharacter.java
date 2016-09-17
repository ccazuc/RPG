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
		ConnectionManager.getConnection().writeByte(DELETE_CHARACTER);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
