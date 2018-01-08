package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandPing extends Command {
	
	private static long timer;
	private static boolean pingSent;
	
	@Override
	public void read() {
		Mideas.setPing((System.currentTimeMillis()-timer));
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.PING_CONFIRMED);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
		pingSent = false;
	}
	
	public static void write() {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.PING);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
		timer = System.currentTimeMillis();
		pingSent = true;
	}
	
	public static long getTimer() {
		return timer;
	}
	
	public static boolean getPingStatus() {
		return pingSent;
	}
	
	public static void setPingStatus(boolean we) {
		pingSent = we;
	}
}
