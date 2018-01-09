package com.mideas.rpg.v2.stresstest;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageColor;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.connection.Connection;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;
import com.mideas.rpg.v2.utils.Color;

public class CommandSendMessage implements Command {
	
	@Override
	public void read(Stresstest client) {
		MessageType type = MessageType.values()[client.getWorldServerConnection().readByte()];
		String message = client.getWorldServerConnection().readString();
		if(type == MessageType.SELF || type == MessageType.GM_ANNOUNCE || type == MessageType.ANNOUNCE) {
			boolean hasAuthor = client.getWorldServerConnection().readBoolean();
			String author = null;
			Color color = null;
			if(hasAuthor) {
				author = client.getWorldServerConnection().readString();
			}
			boolean knownColor = client.getWorldServerConnection().readBoolean();
			if(knownColor) {
				color = MessageColor.values()[client.getWorldServerConnection().readByte()].getColor();
			}
			else {
				color = client.getWorldServerConnection().readColor();
			}
			if(author == null) {
				//ChatFrame.addMessage(new Message(message, false, type, color));
			}
			else {
				//ChatFrame.addMessage(new Message(message, author, false, type, color, false));
			}
		}
		else if(type == MessageType.CHANNEL) {
			String channelName = client.getWorldServerConnection().readString();
			boolean hasAuthor = client.getWorldServerConnection().readBoolean();
			if(hasAuthor) {
				String author = client.getWorldServerConnection().readString();
				boolean isGM = client.getWorldServerConnection().readBoolean();
				//ChatFrame.addMessage(new Message(message, channelName, author, false, isGM, true));
			}
			else {
				//ChatFrame.addMessage(new Message(message, channelName, "", false, false, false));
			}
				
		}
		else if(type == MessageType.WHISPER) {
			String name = client.getWorldServerConnection().readString();
			boolean isTarget = client.getWorldServerConnection().readBoolean();
			boolean isGM = client.getWorldServerConnection().readBoolean();
			//ChatFrame.addMessage(new Message(message, name, false, MessageType.WHISPER, isTarget, isGM));
			//ChatFrame.setPreviousWhisper(name);
		}
		else if(type == MessageType.SAY || type == MessageType.BATTLEGROUND || type == MessageType.GUILD || type == MessageType.PARTY || type == MessageType.RAID || type == MessageType.YELL || type == MessageType.PARTY_LEADER) {
			String author = client.getWorldServerConnection().readString();
			boolean isGM = client.getWorldServerConnection().readBoolean();
			//ChatFrame.addMessage(new Message(message, author, false, type, isGM));
		}
	}
	
	public static void write(Stresstest client, String message, MessageType type) {
		client.getWorldServerConnection().startPacket();
		client.getWorldServerConnection().writeShort(PacketID.SEND_MESSAGE);
		client.getWorldServerConnection().writeString(message);
		client.getWorldServerConnection().writeByte(type.getValue());
		client.getWorldServerConnection().endPacket();
		client.getWorldServerConnection().send();
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
