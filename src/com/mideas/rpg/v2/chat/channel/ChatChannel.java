package com.mideas.rpg.v2.chat.channel;

import java.util.ArrayList;

import com.mideas.rpg.v2.FontManager;
import com.mideas.rpg.v2.utils.StringUtils;

public class ChatChannel {

	private final String name;
	private final String password;
	private final String ID;
	private final int value;
	private final ArrayList<ChannelMember> playerList;
	private int leaderID;
	private String messageHeader;
	private int messageHeaderWidth;
	private String channelNameDisplayed;
	private int numberModerator;
	private final static StringBuilder builder = new StringBuilder();
	
	public ChatChannel(String name, String ID, String password, int value) {
		this.name = name;
		this.ID = ID;
		this.password = password;
		this.value = value;
		this.playerList = new ArrayList<ChannelMember>();
		this.messageHeader = "["+value+". "+this.name+"]";
		this.messageHeaderWidth = FontManager.chat.getWidth(this.messageHeader);
		this.channelNameDisplayed = StringUtils.value[this.value]+". "+this.name+" (0)";
	}
	
	public void addMember(ChannelMember member) {
		builder.setLength(0);
		this.channelNameDisplayed = builder.append(StringUtils.value[this.value]).append(". ").append(this.name).append(" (").append(StringUtils.value[this.playerList.size()+1]).append(")").toString();
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
	
	public boolean removeMember(int id) {
		int i = this.playerList.size();
		while(--i >= 0) {
			if(this.playerList.get(i).getID() == id) {
				this.playerList.remove(i);
				builder.setLength(0);
				this.channelNameDisplayed = builder.append(StringUtils.value[this.value]).append(". ").append(this.name).append(" (").append(StringUtils.value[this.playerList.size()]).append(")").toString();
				return true;
			}
		}
		return false;
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
	
	public void setModerator(int unitID, boolean isModerator) {
		int i = this.playerList.size();
		while(--i >= 0) {
			if(this.playerList.get(i).getID() == unitID) {
				if(this.leaderID == unitID) {
					System.out.println("ERROR in ChatChannel.setModerator, member is already leader, channelName : ".concat(this.name));
					return;
				}
				this.playerList.get(i).setModerator(isModerator);
				if(isModerator) {
					++this.numberModerator;
				}
				else {
					--this.numberModerator;
				}
			}
		}
	}
	
	public int getNumberModerator() {
		return this.numberModerator;
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
	
	public String getID() {
		return this.ID;
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
	
	public int getMemberID(String name) {
		name = StringUtils.formatPlayerName(name);
		int i = this.playerList.size();
		while(--i >= 0) {
			if(this.playerList.get(i).getName().equals(name)) {
				return this.playerList.get(i).getID();
			}
		}
		return 0;
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
