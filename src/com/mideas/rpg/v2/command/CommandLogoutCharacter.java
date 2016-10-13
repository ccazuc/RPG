package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandLogoutCharacter extends Command {

	public static void write() {
		ConnectionManager.getConnection().writeByte(PacketID.CHARACTER_LOGOUT);
		ConnectionManager.getConnection().send();
	}
}
