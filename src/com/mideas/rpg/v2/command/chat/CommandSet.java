package com.mideas.rpg.v2.command.chat;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandSet extends Command {

	public static void write(byte packetID, int playerID, int value, int rank) {
		if(Mideas.getRank() >= rank) {
			ConnectionManager.getConnection().writeByte(PacketID.CHAT_SET);
			ConnectionManager.getConnection().writeByte(packetID);
			ConnectionManager.getConnection().writeInt(playerID);
			ConnectionManager.getConnection().writeInt(value);
			ConnectionManager.getConnection().send();
		}
		else {
			ChatFrame.addMessage(new Message("You don't have the right to do this", false, Color.yellow));
		}
	}
}
