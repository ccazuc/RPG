package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.LOGOUT;

import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.DebugUtils;

public class CommandLogout extends Command {

	@Override
	public void read() {
		System.out.println("Logout received");
		ConnectionManager.disconnect();
	}
	
	public static void write() {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getConnection().startPacket();
			ConnectionManager.getConnection().writeShort(LOGOUT);
			ConnectionManager.getConnection().endPacket();
			ConnectionManager.getConnection().send();
		}
		if(ConnectionManager.isAuthServerConnected()) {
			ConnectionManager.getAuthConnection().startPacket();
			ConnectionManager.getAuthConnection().writeShort(LOGOUT);
			ConnectionManager.getAuthConnection().endPacket();
			ConnectionManager.getAuthConnection().send();
		}
	}
}