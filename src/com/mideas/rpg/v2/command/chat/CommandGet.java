package com.mideas.rpg.v2.command.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandGet extends Command {
	
	@Override
	public void read() {
		byte type = ConnectionManager.getConnection().readByte();
		if(type == PacketID.INT) {
			ChatFrame.addMessage(new Message(Integer.toString(ConnectionManager.getConnection().readInt()), false, Color.yellow));
		}
		else if(type == PacketID.STRING) {
			ChatFrame.addMessage(new Message(ConnectionManager.getConnection().readString(), false, Color.yellow));
		}
	}
	
	public static void write(byte packetID, int playerID) {
		ConnectionManager.getConnection().writeByte(PacketID.CHAT_GET);
		ConnectionManager.getConnection().writeByte(packetID);
		ConnectionManager.getConnection().writeInt(playerID);
		ConnectionManager.getConnection().send();
	}
	
	public static void write(byte packetID, String string) {
		ConnectionManager.getConnection().writeByte(PacketID.CHAT_GET);
		ConnectionManager.getConnection().writeByte(packetID);
		ConnectionManager.getConnection().writeString(string);
		ConnectionManager.getConnection().send();
	}
}
