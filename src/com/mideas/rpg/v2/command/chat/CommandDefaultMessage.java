package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageColor;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.utils.Color;

public class CommandDefaultMessage extends Command {

	@Override
	public void read() {
		DefaultMessage value = DefaultMessage.values()[ConnectionManager.getWorldServerConnection().readByte()];
		boolean knownColor = ConnectionManager.getWorldServerConnection().readBoolean();
		Color color;
		if(knownColor) {
			color = MessageColor.values()[ConnectionManager.getWorldServerConnection().readByte()].getColor();
		}
		else {
			color = ConnectionManager.getWorldServerConnection().readColor();
		}
		ChatFrame.addMessage(new Message(value.getMessage(), false, MessageType.SELF, color));
	}
}
