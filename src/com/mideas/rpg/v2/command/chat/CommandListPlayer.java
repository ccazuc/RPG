package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.utils.Color;

public class CommandListPlayer extends Command {
	
	@Override
	public void read() {
		int number = ConnectionManager.getConnection().readInt();
		int i = 0;
		while(i < number) {
			ChatFrame.addMessage(new Message(ConnectionManager.getConnection().readString(), false, MessageType.SELF, Color.convClassTypeToColor(ClassType.values()[ConnectionManager.getConnection().readChar()])));
			i++;
		}
	}
	
	public static void write() {
		ConnectionManager.getConnection().writeShort(PacketID.CHAT_LIST_PLAYER);
		ConnectionManager.getConnection().send();
	}
}
