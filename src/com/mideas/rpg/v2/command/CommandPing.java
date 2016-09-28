package com.mideas.rpg.v2.command;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandPing extends Command {
	
	private static long timer;
	
	@Override
	public void read() {
		Mideas.setPing((int)(System.currentTimeMillis()-timer));
	}
	
	public static void write() {
		ConnectionManager.getConnection().writeByte(PacketID.PING);
		ConnectionManager.getConnection().send();
		timer = System.currentTimeMillis();
	}
}
