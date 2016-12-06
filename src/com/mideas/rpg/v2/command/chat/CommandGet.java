package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandGet extends Command {
	
	@Override
	public void read() {
		short type = ConnectionManager.getConnection().readShort();
		if(type == PacketID.INT) {
			ChatFrame.addMessage(new Message(Integer.toString(ConnectionManager.getConnection().readInt()), false, MessageType.SELF));
		}
		else if(type == PacketID.STRING) {
			ChatFrame.addMessage(new Message(ConnectionManager.getConnection().readString(), false, MessageType.SELF));
		}
	}
	
	public static void write(short packetID, int playerID) {
		ConnectionManager.getConnection().writeShort(PacketID.CHAT_GET);
		ConnectionManager.getConnection().writeShort(packetID);
		ConnectionManager.getConnection().writeInt(playerID);
		ConnectionManager.getConnection().send();
	}
	
	public static void write(short packetID, String string) {
		ConnectionManager.getConnection().writeShort(PacketID.CHAT_GET);
		ConnectionManager.getConnection().writeShort(packetID);
		ConnectionManager.getConnection().writeString(string);
		ConnectionManager.getConnection().send();
	}
}
