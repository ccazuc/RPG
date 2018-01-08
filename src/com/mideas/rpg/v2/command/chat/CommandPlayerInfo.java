package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandPlayerInfo extends Command {

	@Override
	public void read() {
		String name = ConnectionManager.getWorldServerConnection().readString();
		int id = ConnectionManager.getWorldServerConnection().readInt();
		int rank = ConnectionManager.getWorldServerConnection().readInt();
		String adress = ConnectionManager.getWorldServerConnection().readString();
		ChatFrame.addMessage(new Message((name+": id = "+id+", rank = "+rank+", ip = "+adress), false, MessageType.SELF));
	}
	
	public static void write(int id) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHAT_PLAYER_INFO);
		ConnectionManager.getWorldServerConnection().writeInt(id);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
