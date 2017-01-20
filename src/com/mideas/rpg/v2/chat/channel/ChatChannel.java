package com.mideas.rpg.v2.chat.channel;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatChannel {

	private final String name;
	private final String password;
	private int value;
	private final ArrayList<ChannelMember> playerList;
	private int leaderID;
	private String messageHeader;
	private int messageHeaderWidth;
	private String channelNameDisplayed;
	
	public ChatChannel(String name, String password, int value) {
		this.name = name;
		this.password = password;
		this.value = value;
		this.playerList = new ArrayList<ChannelMember>();
		this.messageHeader = "["+value+". "+this.name+"]";
		this.messageHeaderWidth = FontManager.chat.getWidth(this.messageHeader);
		this.channelNameDisplayed = StringUtils.value[this.value]+". "+this.name+" (0)";
	}
	
	public void addMember(ChannelMember member) {
		this.channelNameDisplayed = StringUtils.value[this.value]+". "+this.name+" ("+StringUtils.value[this.playerList.size()+1]+")";
		if(this.playerList.size() == 0) {
			this.playerList.add(member);
			return;
		}
		if(this.playerList.get(0).getName().compareTo(member.getName()) < 0) {
			this.playerList.add(0, member);
			return;
		}
		int i = 0;
		while(i < this.playerList.size()) {
			if(this.playerList.get(i).getName().compareTo(member.getName()) > 0) {
				this.playerList.add(i, member);
				return;
			}
			i++;
		}
		this.playerList.add(member);
	}
	
	public void removeMember(int id) {
		int i = this.playerList.size();
		while(--i >= 0) {
			if(this.playerList.get(i).getID() == id) {
				this.playerList.remove(i);
				this.channelNameDisplayed = StringUtils.value[this.value]+". "+this.name+" ("+StringUtils.value[this.playerList.size()]+")";
				return;
			}
		}
	}
	
	public void sortMemberList() {
		int i = 0;
		int j = 0;
		ChannelMember tmp;
		while(i < this.playerList.size()) {
			j = i;
			while(j < this.playerList.size()) {
				if(this.playerList.get(i).getName().compareTo(this.playerList.get(j).getName()) >= 0) {
					tmp = this.playerList.get(j);
					this.playerList.set(j, this.playerList.get(i));
					this.playerList.set(i, tmp);
				}
				j++;
			}
			i++;
		}
	}
	
	public String getChannelNameDisplayed() {
		return this.channelNameDisplayed;
	}
	
	public String getMessageHeader() {
		return this.messageHeader;
	}
	
	public int getMessageHeaderWidth() {
		return this.messageHeaderWidth;
	}
	
	public void setLeader(int unitID) {
		this.leaderID = unitID;
	}
	
	public int getLeaderID() {
		return this.leaderID;
	}
	
	public String getLeaderName() {
		return getMemberName(this.leaderID);
	}
	
	public String getMemberName(int unitID) {
		int i = this.playerList.size();
		while(--i >= 0) {
			if(this.playerList.get(i).getID() == unitID) {
				return this.playerList.get(i).getName();
			}
		}
		return null;
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
