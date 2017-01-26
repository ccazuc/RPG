package com.mideas.rpg.v2.chat.channel;

public class ChannelMember {

	private final String name;
	private final int id;
	private boolean isModerator;
	
	public ChannelMember(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public void setModerator(boolean we) {
		this.isModerator = we;
	}
	
	public boolean isModerator() {
		return this.isModerator;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
}
