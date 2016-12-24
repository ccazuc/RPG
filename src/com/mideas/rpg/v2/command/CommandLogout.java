package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.LOGOUT;

import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandLogout extends Command {

	@Override
	public void read() {}
	
	public static void write() {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getConnection().startPacket();
			ConnectionManager.getConnection().writeShort(LOGOUT);
			ConnectionManager.getConnection().endPacket();
			ConnectionManager.getConnection().send();
			ConnectionManager.close();
		}
		if(ConnectionManager.isAuthServerConnected()) {
			ConnectionManager.getConnection().startPacket();
			ConnectionManager.getAuthConnection().writeShort(LOGOUT);
			ConnectionManager.getConnection().endPacket();
			ConnectionManager.getAuthConnection().send();
			ConnectionManager.closeAuth();
		}
	}
}
