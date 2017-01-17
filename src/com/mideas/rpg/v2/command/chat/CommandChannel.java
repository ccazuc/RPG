package com.mideas.rpg.v2.command.chat;

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
			String password = ConnectionManager.getConnection().readString();
			ChannelMgr.addChannel(channelName, password);
			//TODO: send message in chat
		}
		else if(packetId == PacketID.CHANNEL_LEAVE) {
			String channelName = ConnectionManager.getConnection().readString();
			ChannelMgr.leaveChannel(channelName);
			//TODO: send message in chat
		}
		else if(packetId == PacketID.CHANNEL_SEND_MEMBERS) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = 0;
			while((id = ConnectionManager.getConnection().readInt()) != -1) {
				String name = ConnectionManager.getConnection().readString();
				ChannelMgr.addMember(channelName, name, id);
			}
		}
		else if(packetId == PacketID.CHANNEL_MEMBER_JOINED) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			String name = ConnectionManager.getConnection().readString();
			ChannelMgr.addMember(channelName, name, id);
			//TODO: send message
		}
		else if(packetId == PacketID.CHANNEL_MEMBER_LEFT) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			ChannelMgr.removeMember(channelName, id);
			//TODO: remove in channel list and send message
		}
		else if(packetId == PacketID.CHANNEL_SET_LEADER) {
			String channelName = ConnectionManager.getConnection().readString();
			int id = ConnectionManager.getConnection().readInt();
			ChannelMgr.setLeader(channelName, id);
		}
	}
	
	public static void joinChannel(String channelName) {
		joinChannel(channelName, "");
	}
	
	public static void joinChannel(String channelName, String password) {
		ConnectionManager.getConnection().startPacket();
		ConnectionManager.getConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getConnection().writeShort(PacketID.CHANNEL_JOIN);
		ConnectionManager.getConnection().writeString(channelName);
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
