/*package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandGet extends Command {
	
	@Override
	public void read() {
		short type = ConnectionManager.getWorldServerConnection().readShort();
		if(type == PacketID.INT) {
			ChatFrame.addMessage(new Message(Integer.toString(ConnectionManager.getWorldServerConnection().readInt()), false, MessageType.SELF));
		}
		else if(type == PacketID.STRING) {
			ChatFrame.addMessage(new Message(ConnectionManager.getWorldServerConnection().readString(), false, MessageType.SELF));
		}
	}
	
	public static void write(short packetID, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHAT_GET);
		ConnectionManager.getWorldServerConnection().writeShort(packetID);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void write(short packetID, String string) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHAT_GET);
		ConnectionManager.getWorldServerConnection().writeShort(packetID);
		ConnectionManager.getWorldServerConnection().writeString(string);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}*/
