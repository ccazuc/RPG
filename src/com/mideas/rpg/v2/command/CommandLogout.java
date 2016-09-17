package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.LOGOUT;

import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandLogout extends Command {

	@Override
	public void read() {}
	
	public static void write() {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getConnection().writeByte(LOGOUT);
			ConnectionManager.getConnection().send();
			ConnectionManager.close();
		}
	}
}
