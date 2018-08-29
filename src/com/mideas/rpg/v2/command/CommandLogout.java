package com.mideas.rpg.v2.command;

import static com.mideas.rpg.v2.connection.PacketID.LOGOUT;

import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.files.logs.LogsMgr;
import com.mideas.rpg.v2.stresstest.Stresstest;

public class CommandLogout extends Command {

	@Override
	public void read() {
		System.out.println("Logout received");
		ConnectionManager.disconnect();
		LogsMgr.writeConnectionLog("Logout from account accepted.");
	}
	
	public static void write() {
		if(ConnectionManager.isConnected()) {
			ConnectionManager.getWorldServerConnection().startPacket();
			ConnectionManager.getWorldServerConnection().writeShort(LOGOUT);
			ConnectionManager.getWorldServerConnection().endPacket();
			ConnectionManager.getWorldServerConnection().send();
		}
		if(ConnectionManager.isAuthServerConnected()) {
			ConnectionManager.getAuthConnection().startPacket();
			ConnectionManager.getAuthConnection().writeShort(LOGOUT);
			ConnectionManager.getAuthConnection().endPacket();
			ConnectionManager.getAuthConnection().send();
		}
		LogsMgr.writeConnectionLog("Logout from account requested.");
	}
	
	public static void logoutWorldServer()
	{
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(LOGOUT);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
		System.out.println("Logout sent to wordl server.");
	}
	
	public static void write(Stresstest client)
	{
		Connection worldConnection = client.getWorldServerConnection();
		Connection authConnection = client.getAuthConnection();
		if (worldConnection.getSocket() != null && worldConnection.getSocket().isConnected())
		{
			worldConnection.startPacket();
			worldConnection.writeShort(LOGOUT);
			worldConnection.endPacket();
			worldConnection.send();
		}
		if (authConnection.getSocket() != null && authConnection.getSocket().isConnected())
		{
			authConnection.startPacket();
			authConnection.writeShort(LOGOUT);
			authConnection.endPacket();
			authConnection.send();
		}
	}
}