package com.mideas.rpg.v2.command.chat;

import com.mideas.rpg.v2.chat.ChatFrame;
import com.mideas.rpg.v2.chat.Message;
import com.mideas.rpg.v2.chat.MessageType;
import com.mideas.rpg.v2.chat.channel.ChannelMgr;
import com.mideas.rpg.v2.command.Command;
import com.mideas.rpg.v2.connection.ConnectionManager;
import com.mideas.rpg.v2.connection.PacketID;

public class CommandChannel extends Command {

	@Override
	public void read() {
		short packetId = ConnectionManager.getConnection().readShort();
		if(packetId == PacketID.CHANNEL_JOIN) {
			String channelName = ConnectionManager.getConnection().readString();
			String channelID = ConnectionManager.getConnection().readString();
			int channelValue = ConnectionManager.getConnection().readInt();
			String password = ConnectionManager.getConnection().readString();
			ChannelMgr.addChannel(channelName, channelID, channelValue, password);
			ChatFrame.addMessage(new Message("Joined channel : ["+channelValue+". "+channelName+']', false, MessageType.SELF, MessageType.CHANNEL.getColor()));
		}
		else if(packetId == PacketID.CHANNEL_LEAVE) {
			String channelName = ConnectionManager.getConnection().readString();
			ChatFrame.addMessage(new Message("Left channel : ["+ChannelMgr.getChannelIndex(channelName)+". "+channelName+']', false, MessageType.SELF, MessageType.CHANNEL.getColor()));
			ChannelMgr.leaveChannel(channelName);
		}
		else if(packetId == PacketID.CHANNEL_SEND_MEMBERS) {
			String channelName = ConnectionManager.getConnection().readString();
			int leaderID = ConnectionManager.getConnection().readInt();
			int id = 0;
			while((id = ConnectionManager.getConnection().readInt()) != -1) {
				String name = ConnectionManager.getConnection().readString();
				ChannelMgr.addMember(channelName, name, id);
			}
			ChannelMgr.setLeader(channelName, leaderID);
		}
		else if(packetId == PacketID.CHANNEL_MEMBER_JOINED) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			ChannelMgr.addMember(channelName, name, id);
			ChatFrame.addMessage(new Message(" joined the channel.", channelName, name, false, false, false));
		}
		else if(packetId == PacketID.CHANNEL_MEMBER_LEFT) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			ChatFrame.addMessage(new Message(" left the channel.", channelName, ChannelMgr.getMemberName(channelName, id), false, false, false));
			ChannelMgr.removeMember(channelName, id);
		}
		else if(packetId == PacketID.CHANNEL_SET_LEADER) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			ChannelMgr.setLeader(channelName, id);
			ChatFrame.addMessage(new Message("["+ChannelMgr.getChannelIndex(channelName)+". "+channelName+"] "+ChannelMgr.getLeaderName(channelName)+" is now the leader.", false, MessageType.SELF, MessageType.CHANNEL.getColor()));
		}
		else if(packetId == PacketID.CHANNEL_KICK_PLAYER) {
			String channelName = ConnectionManager.getConnection().readString();
			String kickerName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			ChatFrame.addMessage(new Message(ChannelMgr.getChannelHeader(channelName)+" "+kickerName+" kicked "+ChannelMgr.getMemberName(channelName, id), false, MessageType.SELF, MessageType.CHANNEL.getColor()));
			ChannelMgr.removeMember(channelName, id);
		}
	}
	
	public static void joinChannel(String channelName, int value) {
		joinChannel(channelName, value, "");
	}
	
	public static void joinChannel(String channelName, int value, String password) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getConnection().writeShort(PacketID.CHANNEL_JOIN);
		ConnectionManager.getConnection().writeString(channelName);
		ConnectionManager.getConnection().writeInt(value);
		ConnectionManager.getConnection().writeString(password);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
	
	public static void leaveChannel(String channelName) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getConnection().writeShort(PacketID.CHANNEL_LEAVE);
		ConnectionManager.getConnection().writeString(channelName);
		ConnectionManager.getConnection().endPacket();
		ConnectionManager.getConnection().send();
	}
}
