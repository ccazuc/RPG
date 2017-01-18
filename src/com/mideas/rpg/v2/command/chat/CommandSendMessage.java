package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageColor;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.utils.Color;

public class CommandSendMessage extends Command {
	
	@Override
	public void read() {
		MessageType type = MessageType.values()[ConnectionManager.getConnection().readByte()];
		String message = ConnectionManager.getConnection().readString();
		if(type == MessageType.SELF || type == MessageType.GM_ANNOUNCE || type == MessageType.ANNOUNCE) {
			boolean hasAuthor = ConnectionManager.getConnection().readBoolean();
			String author = null;
			Color color = null;
			if(hasAuthor) {
				author = ConnectionManager.getConnection().readString();
			}
			boolean knownColor = ConnectionManager.getConnection().readBoolean();
			if(knownColor) {
				color = MessageColor.values()[ConnectionManager.getConnection().readByte()].getColor();
			}
			else {
				color = ConnectionManager.getConnection().readColor();
			}
			if(author == null) {
				ChatFrame.addMessage(new Message(message, false, type, color));
			}
			else {
				ChatFrame.addMessage(new Message(message, author, false, type, color, false));
			}
		}
		else if(type == MessageType.CHANNEL) {
			String channelName = ConnectionManager.getConnection().readString();
			String author = ConnectionManager.getConnection().readString();
			boolean isGM = ConnectionManager.getConnection().readBoolean();
			ChatFrame.addMessage(new Message(message, channelName, author, false, isGM, true));
				
		}
		else if(type == MessageType.WHISPER) {
			String name = ConnectionManager.getConnection().readString();
			boolean isTarget = ConnectionManager.getConnection().readBoolean();
			boolean isGM = ConnectionManager.getConnection().readBoolean();
			ChatFrame.addMessage(new Message(message, name, false, MessageType.WHISPER, isTarget, isGM));
			ChatFrame.setPreviousWhisper(name);
		}
		else if(type == MessageType.SAY || type == MessageType.BATTLEGROUND || type == MessageType.GUILD || type == MessageType.PARTY || type == MessageType.RAID || type == MessageType.YELL || type == MessageType.PARTY_LEADER) {
			String author = ConnectionManager.getConnection().readString();
			boolean isGM = ConnectionManager.getConnection().readBoolean();
			ChatFrame.addMessage(new Message(message, author, false, type, isGM));
		}
	}
	
	public static void write(String message, MessageType type) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.SEND_MESSAGE);
		ConnectionManager.getConnection().writeString(message);
		ConnectionManager.getConnection().writeByte(type.getValue());
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void writeWhisper(String message, String target) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.SEND_MESSAGE);
		ConnectionManager.getConnection().writeString(message);
		ConnectionManager.getConnection().writeByte(MessageType.WHISPER.getValue());
		ConnectionManager.getConnection().writeString(target);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void writeChannel(String message, String channelName) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.SEND_MESSAGE);
		ConnectionManager.getConnection().writeString(message);
		ConnectionManager.getConnection().writeByte(MessageType.CHANNEL.getValue());
		ConnectionManager.getConnection().writeString(channelName);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
