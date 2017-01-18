package com.mideas.rpg.v2.chat.channel;

import java.util.ArrayList;
import java.util.HashMap;

public class ChannelMgr {

	private final static HashMap<String, ChatChannel> channelMap = new HashMap<String, ChatChannel>();
	
	public static ArrayList<ChannelMember> getMemberList(String channelName) {
		if(channelMap.containsKey(channelName)) {
			return channelMap.get(channelName).getPlayerList();
		}
		return null;
	}
	
	public static void addChannel(String channelName, String password) {
		if(channelMap.containsKey(channelName)) {
			System.out.println("ChannelMgr.addChannel error, channel already exists");
			return;
		}
		int id = generateChannelID();
		channelMap.put(channelName, new ChatChannel(channelName, password, id));
	}
	
	public static void leaveChannel(String channelName) {
		channelMap.remove(channelName);
	}
	
	public static void addMember(String channelName, String name, int id) {
		channelMap.get(channelName).addMember(new ChannelMember(name, id));
	}
	
	public static int getChannelIndex(String channelName) {
		return channelMap.get(channelName).getValue();
	}
	
	public static String getMemberName(String channelName, int id) {
		return channelMap.get(channelName).getMemberName(id);
	}
	
	public static void removeMember(String channelName, int id) {
		channelMap.get(channelName).removeMember(id);
	}
	
	public static void setLeader(String channelName, int id) {
		channelMap.get(channelName).setLeader(id);
	}
	
	public static String getLeaderName(String channelName) {
		return channelMap.get(channelName).getLeaderName();
	}
	
	public static String getChannelHeader(String channelName) {
		return channelMap.get(channelName).getMessageHeader();
	}
	
	public static int getChannelHeaderWidth(String channelName) {
		return channelMap.get(channelName).getMessageHeaderWidth();
	}
	
	public static ChatChannel getChannelByValue(int value) {
		for(ChatChannel channel : channelMap.values()) {
			if(channel.getValue() == value) {
				return channel;
			}
		}
		return null;
	}
	
	private static int generateChannelID() {
		int i = 1;
		while(i < 10) {
			if(!isIDUsed(i)) {
				break;
			}
			i++;
		}
		return i;
	}
	
	private static boolean isIDUsed(int id) {
		for(ChatChannel channel : channelMap.values()) {
			if(channel.getValue() == id) {
				return true;
			}
		}
		return false;
	}
}
