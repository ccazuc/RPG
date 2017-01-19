package com.mideas.rpg.v2.chat.channel;

import java.util.ArrayList;

public class ChannelCategory {

	private final ArrayList<ChatChannel> channelList;
	private final String name;
	private boolean isExpanded;
	
	public ChannelCategory(String name) {
		this.channelList = new ArrayList<ChatChannel>();
		this.name = name;
	}
	
	public ArrayList<ChatChannel> getChannelList() {
		return this.channelList;
	}
	
	public boolean isExpanded() {
		return this.isExpanded;
	}
	
	public void addChannel(ChatChannel channel) {
		this.channelList.add(channel);
	}
	
	public void removeChannel(ChatChannel channel) {
		int i = this.channelList.size();
		while(--i >= 0) {
			if(this.channelList.get(i) == channel) {
				this.channelList.remove(i);
				return;
			}
		}
	}
	
	public String getName() {
		return this.name;
	}
}
 