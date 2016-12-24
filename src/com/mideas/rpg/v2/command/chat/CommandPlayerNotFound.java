package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;

public class CommandPlayerNotFound extends Command {
	
	@Override
	public void read() {
		String name = ConnectionManager.getConnection().readString();
		ChatFrame.addMessage(new Message("Player "+name+" not found.", false, MessageType.SELF));
	}
}
