package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageColor;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.utils.Color;

public class CommandSendMessage extends Command {
	
	@Override
	public void read() {
		MessageType type = MessageType.values()[ConnectionManager.getWorldServerConnection().readByte()];
		String message = ConnectionManager.getWorldServerConnection().readString();
		if(type == MessageType.SELF || type == MessageType.GM_ANNOUNCE || type == MessageType.ANNOUNCE) {
			boolean hasAuthor = ConnectionManager.getWorldServerConnection().readBoolean();
			String author = null;
			Color color = null;
			if(hasAuthor) {
				author = ConnectionManager.getWorldServerConnection().readString();
			}
			boolean knownColor = ConnectionManager.getWorldServerConnection().readBoolean();
			if(knownColor) {
				color = MessageColor.values()[ConnectionManager.getWorldServerConnection().readByte()].getColor();
			}
			else {
				color = ConnectionManager.getWorldServerConnection().readColor();
			}
			if(author == null) {
				ChatFrame.addMessage(new Message(message, false, type, color));
			}
			else {
				ChatFrame.addMessage(new Message(message, author, false, type, color, false));
			}
		}
		else if(type == MessageType.CHANNEL) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			boolean hasAuthor = ConnectionManager.getWorldServerConnection().readBoolean();
			if(hasAuthor) {
			String author = ConnectionManager.getWorldServerConnection().readString();
			boolean isGM = ConnectionManager.getWorldServerConnection().readBoolean();
				ChatFrame.addMessage(new Message(message, channelName, author, false, isGM, true));
			}
			else {
				ChatFrame.addMessage(new Message(message, channelName, "", false, false, false));
			}
				
		}
		else if(type == MessageType.WHISPER) {
			String name = ConnectionManager.getWorldServerConnection().readString();
			boolean isTarget = ConnectionManager.getWorldServerConnection().readBoolean();
			boolean isGM = ConnectionManager.getWorldServerConnection().readBoolean();
			ChatFrame.addMessage(new Message(message, name, false, MessageType.WHISPER, isTarget, isGM));
			ChatFrame.setPreviousWhisper(name);
		}
		else if(type == MessageType.SAY || type == MessageType.BATTLEGROUND || type == MessageType.GUILD || type == MessageType.PARTY || type == MessageType.RAID || type == MessageType.YELL || type == MessageType.PARTY_LEADER) {
			String author = ConnectionManager.getWorldServerConnection().readString();
			boolean isGM = ConnectionManager.getWorldServerConnection().readBoolean();
			ChatFrame.addMessage(new Message(message, author, false, type, isGM));
		}
	}
	
	public static void write(String message, MessageType type, Connection connection) {
		connection.startPacket();
		connection.writeShort(PacketID.SEND_MESSAGE);
		connection.writeString(message);
		connection.writeByte(type.getValue());
		connection.endPacket();
		connection.send();
	}
	
	public static void writeWhisper(String message, String target) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.SEND_MESSAGE);
		ConnectionManager.getWorldServerConnection().writeString(message);
		ConnectionManager.getWorldServerConnection().writeByte(MessageType.WHISPER.getValue());
		ConnectionManager.getWorldServerConnection().writeString(target);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void writeChannel(String message, String channelName) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.SEND_MESSAGE);
		ConnectionManager.getWorldServerConnection().writeString(message);
		ConnectionManager.getWorldServerConnection().writeByte(MessageType.CHANNEL.getValue());
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
