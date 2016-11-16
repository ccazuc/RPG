package com.mideas.rpg.v2.game.guild;

import java.util.ArrayList;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.hud.social.GuildFrame;

public class Guild {

	private int id;
	private int leader_id;
	private String name;
	private String information;
	private String motd;
	private String tempMotd;
	private String numberMembers;
	private int numberOnlineMembers;
	private String numberOnlineMembersString;
	private ArrayList<GuildMember> memberList;
	private ArrayList<GuildRank> rankList;
	
	public Guild(int id, int leader_id, String name, String information, String motd, ArrayList<GuildMember> memberList, ArrayList<GuildRank> rankList) {
		this.information = information;
		this.memberList = memberList;
		this.leader_id = leader_id;
		this.rankList = rankList;
		this.name = name;
		this.motd = motd;
		this.tempMotd = motd;
		this.id = id;
		/*this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "LAST", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));*/
		this.memberList.add(new GuildMember(2, "A", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "D", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "E", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "f", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "z", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "al", 50, this.rankList.get(1), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "df", 50, this.rankList.get(2), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "Lf", 50, this.rankList.get(3), true, "", "", ClassType.ROGUE));
		this.memberList.add(new GuildMember(2, "FS", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE));
		this.numberMembers = String.valueOf(this.memberList.size());
		initNumberOnlineMembers();
	}
	
	public GuildRank getRank(int rank_order) {
		int i = 0;
		while(i < this.rankList.size()) {
			if(this.rankList.get(i).getOrder() == rank_order) {
				return this.rankList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public GuildMember getMember(int id) {
		int i = 0;
		while(i < this.memberList.size()) {
			if(this.memberList.get(i).getId() == id) {
				return this.memberList.get(i);
			}
			i++;
		}
		return null;
	}
	
	public void removeMember(int id) {
		int i = 0;
		while(i < this.memberList.size()) {
			if(this.memberList.get(i).getId() == id) {
				this.memberList.remove(i);
				break;
			}
			i++;
		}
	}
	
	public void resetTempRank() {
		int i = 0;
		while(i < this.rankList.size()) {
			this.rankList.get(i).cancelTempModification();
			i++;
		}
	}
	
	private void initNumberOnlineMembers() {
		int i = 0;
		while(i < this.memberList.size()) {
			if(this.memberList.get(i).isOnline()) {
				this.numberOnlineMembers++;
			}
			i++;
		}
		this.numberOnlineMembersString = String.valueOf(this.numberOnlineMembers);
	}
	
	public void initOnlineMembers() {
		this.numberOnlineMembers = 0;
		int i = 0;
		while(i < this.memberList.size()) {
			if(this.memberList.get(i).isOnline()) {
				this.numberOnlineMembers++;
			}
			i++;
		}
		this.numberOnlineMembersString = String.valueOf(this.numberOnlineMembers);
	}
	
	public String getNumberMember() {
		return this.numberMembers;
	}
	
	public String getNumberOnlineMemberString() {
		return this.numberOnlineMembersString;
	}
	
	public int getNumberOnlineMember() {
		return this.numberOnlineMembers;
	}
	
	public int getLeaderId() {
		return this.leader_id;
	}
	
	public boolean isLeader(int id) {
		return this.leader_id == id;
	}
	
	public void memberLoggedIn() {
		this.numberOnlineMembers++;
		this.numberOnlineMembersString = String.valueOf(this.numberOnlineMembers);
	}
	
	public void memberLoggedOut() {
		this.numberOnlineMembers++;
		this.numberOnlineMembersString = String.valueOf(this.numberOnlineMembers);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getInformation() {
		return this.information;
	}
	
	public void setInformation(String information) {
		this.information = information;
		GuildFrame.getInformationInput().setText(this.information);
	}
	
	public String getMotd() {
		return this.motd;
	}
	
	public void setMotd(String motd) {
		this.motd = motd;
		this.tempMotd = motd;
	}
	
	public String getTempMotd() {
		return this.tempMotd;
	}
	
	public void setTempMotd(String tempMotd) {
		this.tempMotd = tempMotd;
	}
	 
	public void addMember(GuildMember guildMember) {
		this.memberList.add(guildMember);
		this.numberMembers = String.valueOf(this.memberList.size());
		if(guildMember.isOnline()) {
			this.numberOnlineMembers++;
			this.numberOnlineMembersString = String.valueOf(this.numberOnlineMembers);
		}
	}
	
	public ArrayList<GuildMember> getMemberList() {
		return this.memberList;
	}
	
	public ArrayList<GuildRank> getRankList() {
		return this.rankList;
	}
	
	public void sortMemberByNameAscending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getName().compareTo(this.memberList.get(j).getName()) > 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByNameDescending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getName().compareTo(this.memberList.get(j).getName()) < 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByRankAscending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getRank().getOrder() > this.memberList.get(i).getRank().getOrder()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByRankDescending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getRank().getOrder() < this.memberList.get(i).getRank().getOrder()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByLevelAscending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getLevel() > this.memberList.get(i).getLevel()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByLevelDescending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getLevel() < this.memberList.get(i).getLevel()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByNoteAscending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getNote().compareTo(this.memberList.get(j).getNote()) > 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByNoteDescending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if(this.memberList.get(i).getNote().compareTo(this.memberList.get(j).getNote()) < 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByOnlineAscending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if((!this.memberList.get(i).isOnline() && this.memberList.get(j).isOnline())) { //TODO: add last online timer
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
	
	public void sortMemberByOnlineDescending() {
		int i = 0;
		int j = 0;
		GuildMember temp;
		while(i < this.memberList.size()) {
			j = i;
			while(j < this.memberList.size()) {
				if((this.memberList.get(i).isOnline() && !this.memberList.get(j).isOnline())) { //TODO: add last online timer
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
				j++;
			}
			i++;
		}
	}
}
