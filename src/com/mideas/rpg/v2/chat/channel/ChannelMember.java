package com.mideas.rpg.v2.chat.channel;

public class ChannelMember {

	private final String name;
	private final int id;
	
	public ChannelMember(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
}
