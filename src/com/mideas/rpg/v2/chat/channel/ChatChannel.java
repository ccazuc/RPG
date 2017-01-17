package com.mideas.rpg.v2.chat.channel;

import java.util.ArrayList;

public class ChatChannel {

	private final String name;
	private final String password;
	private int value;
	private final ArrayList<ChannelMember> playerList;
	private int leaderID;
	
	public ChatChannel(String name, String password, int value) {
		this.name = name;
		this.password = password;
		this.value = value;
		this.playerList = new ArrayList<ChannelMember>();
	}
	
	public void addMember(ChannelMember member) {
		this.playerList.add(member);
	}
	
	public void removeMember(int id) {
		int i = this.playerList.size();
		while(--i >= 0) {
			if(this.playerList.get(i).getID() == id) {
				this.playerList.remove(i);
				return;
			}
		}
	}
	
	public void setLeader(int unitID) {
		this.leaderID = unitID;
	}
	
	public int getLeaderID() {
		return this.leaderID;
	}
	
	public ArrayList<ChannelMember> getPlayerList() {
		return this.playerList;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getPassword() {
		return this.password;
	}
}
