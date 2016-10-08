package com.mideas.rpg.v2.command.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandGet extends Command {
	
	@Override
	public void read() {
		ChatFrame.addMessage(new Message(Integer.toString(ConnectionManager.getConnection().readInt()), false, Color.yellow));
	}
	
	public static void write(byte packetID, int playerID) {
		ConnectionManager.getConnection().writeByte(packetID);
		ConnectionManager.getConnection().writeInt(playerID);
		ConnectionManager.getConnection().send();
	}
	
	public static void write(byte packetID, String string) {
		ConnectionManager.getConnection().writeByte(packetID);
		ConnectionManager.getConnection().writeString(string);
		ConnectionManager.getConnection().send();
	}
}
