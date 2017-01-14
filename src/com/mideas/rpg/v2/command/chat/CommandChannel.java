package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandChannel extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.CHANNEL_JOIN) {
			
		}
		else if(packetId == PacketID.CHANNEL_LEAVE) {
			
		}
		else if(packetId == PacketID.CHANNEL_SEND_MEMBERS) {
			
		}
	}
	
	public static void joinChannel(String channelName) {
		
	}
	
	public static void leaveChannel(String channelName) {
		
	}
}
