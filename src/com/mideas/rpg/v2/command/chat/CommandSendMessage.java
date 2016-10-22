package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandSendMessage extends Command {
	
	@Override
	public void read() {
		MessageType type = MessageType.values()[ConnectionManager.getConnection().readChar()];
		String message = ConnectionManager.getConnection().readString();
		if(type == MessageType.SELF) {
			ChatFrame.addMessage(new Message(message, false, MessageType.SELF));
		}
		else if(type == MessageType.WHISPER) {
			String name = ConnectionManager.getConnection().readString();
			boolean isTarget = ConnectionManager.getConnection().readBoolean();
			ChatFrame.addMessage(new Message(message, name, false, MessageType.WHISPER, isTarget));
			ChatFrame.setPreviousWhisper(name);
		}
		else if(type == MessageType.SAY || type == MessageType.BATTLEGROUND || type == MessageType.GUILD || type == MessageType.PARTY || type == MessageType.RAID || type == MessageType.YELL) {
			String author = ConnectionManager.getConnection().readString();
			ChatFrame.addMessage(new Message(message, author, false, type));
		}
	}
	
	public static void write(String message, MessageType type) {
		ConnectionManager.getConnection().writeByte(PacketID.SEND_MESSAGE);
		ConnectionManager.getConnection().writeString(message);
		ConnectionManager.getConnection().writeChar(type.getValue());
		ConnectionManager.getConnection().send();
		System.out.println(message);
	}
	
	public static void writeWhisper(String message, String target) {
		ConnectionManager.getConnection().writeByte(PacketID.SEND_MESSAGE);
		ConnectionManager.getConnection().writeString(message);
		ConnectionManager.getConnection().writeChar(MessageType.WHISPER.getValue());
		ConnectionManager.getConnection().writeString(target);
		ConnectionManager.getConnection().send();
	}
}
