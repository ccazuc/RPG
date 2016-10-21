package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;

public class CommandNotAllowed extends Command {
	
	@Override
	public void read() {
		ChatFrame.addMessage(new Message("You don't have the right to do this.", false, MessageType.SELF));
	}
}
