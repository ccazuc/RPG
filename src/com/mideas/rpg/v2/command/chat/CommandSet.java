package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.Mideas;
import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandSet extends Command {

	public static void write(short packetID, int playerID, int value, int rank) {
		if(Mideas.getRank() >= rank) {
			ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHAT_SET);
			ConnectionManager.getWorldServerConnection().writeShort(packetID);
			ConnectionManager.getWorldServerConnection().writeInt(playerID);
			ConnectionManager.getWorldServerConnection().writeInt(value);
			ConnectionManager.getWorldServerConnection().send();
		}
		else {
			ChatFrame.addMessage(new Message("You don't have the right to do this.", false, MessageType.SELF, true));
		}
	}
}
