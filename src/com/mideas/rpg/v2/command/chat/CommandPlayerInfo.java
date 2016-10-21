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
		String name = ConnectionManager.getConnection().readString();
		int id = ConnectionManager.getConnection().readInt();
		int rank = ConnectionManager.getConnection().readInt();
		String adress = ConnectionManager.getConnection().readString();
		ChatFrame.addMessage(new Message((name+": id = "+id+", rank = "+rank+", ip = "+adress), false, MessageType.SELF));
	}
	
	public static void write(int id) {
		ConnectionManager.getConnection().writeByte(PacketID.CHAT_PLAYER_INFO);
		ConnectionManager.getConnection().writeInt(id);
		ConnectionManager.getConnection().send();
	}
}
