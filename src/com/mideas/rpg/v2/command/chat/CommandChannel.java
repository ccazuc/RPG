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
		short packetId = ConnectionManager.getWorldServerConnection().readShort();
		if(packetId == PacketID.CHANNEL_JOIN) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			String channelID = ConnectionManager.getWorldServerConnection().readString();
			int channelValue = ConnectionManager.getWorldServerConnection().readInt();
			String password = ConnectionManager.getWorldServerConnection().readString();
			ChannelMgr.addChannel(channelName, channelID, channelValue, password);
			ChatFrame.addMessage(new Message("Joined channel : ["+channelValue+". "+channelName+']', false, MessageType.SELF, MessageType.CHANNEL.getColor()));
		}
		else if(packetId == PacketID.CHANNEL_LEAVE) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			ChatFrame.addMessage(new Message("Left channel : ["+ChannelMgr.getChannelIndex(channelName)+". "+channelName+']', false, MessageType.SELF, MessageType.CHANNEL.getColor()));
			ChannelMgr.leaveChannel(channelName);
		}
		else if(packetId == PacketID.CHANNEL_SEND_MEMBERS) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			int leaderID = ConnectionManager.getWorldServerConnection().readInt();
			int id = 0;
			while((id = ConnectionManager.getWorldServerConnection().readInt()) != -1) {
				String name = ConnectionManager.getWorldServerConnection().readString();
				ChannelMgr.addMember(channelName, name, id);
			}
			ChannelMgr.setLeader(channelName, leaderID);
		}
		else if(packetId == PacketID.CHANNEL_MEMBER_JOINED) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String name = ConnectionManager.getWorldServerConnection().readString();
			ChannelMgr.addMember(channelName, name, id);
			ChatFrame.addMessage(new Message(" joined the channel.", channelName, name, false, false, false, true));
		}
		else if(packetId == PacketID.CHANNEL_MEMBER_LEFT) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String memberName = ChannelMgr.getMemberName(channelName, id);
			if(ChannelMgr.removeMember(channelName, id)) {
				ChatFrame.addMessage(new Message(" left the channel.", channelName, memberName, false, false, false, true));
			}
		}
		else if(packetId == PacketID.CHANNEL_SET_LEADER) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			ChannelMgr.setLeader(channelName, id);
			ChatFrame.addMessage(new Message(ChannelMgr.getLeaderName(channelName)+" is now the leader.", channelName, "", false, false, false, true));
		}
		else if(packetId == PacketID.CHANNEL_KICK_PLAYER) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			String kickerName = ConnectionManager.getWorldServerConnection().readString();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			String memberName = ChannelMgr.getMemberName(channelName, id);
			if(ChannelMgr.removeMember(channelName, id)) {
				ChatFrame.addMessage(new Message(ChannelMgr.getChannelHeader(channelName)+" "+kickerName+" kicked "+memberName, false, MessageType.SELF, MessageType.CHANNEL.getColor()));
			}
		}
		else if(packetId == PacketID.CHANNEL_SET_MODERATOR) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			int id = ConnectionManager.getWorldServerConnection().readInt();
			boolean isModerator = ConnectionManager.getWorldServerConnection().readBoolean();
			ChannelMgr.setModerator(channelName, id, isModerator);
		}
		else if(packetId == PacketID.CHANNEL_CHANGE_PASSWORD) {
			String channelName = ConnectionManager.getWorldServerConnection().readString();
			int playerID = ConnectionManager.getWorldServerConnection().readInt();
			String playerName = ChannelMgr.getMemberName(channelName, playerID);
			ChatFrame.addMessage(new Message("Password changed by "+playerName, channelName, "", false, false, false, true));
		}
	}
	
	public static void joinChannel(String channelName, int value) {
		joinChannel(channelName, value, "");
	}
	
	public static void joinChannel(String channelName, int value, String password) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_JOIN);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(value);
		ConnectionManager.getWorldServerConnection().writeString(password);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void banPlayer(String channelName, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_BAN_PLAYER);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void unbanPlayer(String channelName, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_UNBAN_PLAYER);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void kickPlayer(String channelName, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_KICK_PLAYER);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void mutePlayer(String channelName, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_MUTE_PLAYER);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void unmutePlayer(String channelName, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_UNMUTE_PLAYER);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setModerator(String channelName, int playerID, boolean b) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_SET_MODERATOR);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().writeBoolean(b);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void setLeader(String channelName, int playerID) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_SET_LEADER);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().writeInt(playerID);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
	
	public static void leaveChannel(String channelName) {
		ConnectionManager.getWorldServerConnection().startPacket();
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL);
		ConnectionManager.getWorldServerConnection().writeShort(PacketID.CHANNEL_LEAVE);
		ConnectionManager.getWorldServerConnection().writeString(channelName);
		ConnectionManager.getWorldServerConnection().endPacket();
		ConnectionManager.getWorldServerConnection().send();
	}
}
