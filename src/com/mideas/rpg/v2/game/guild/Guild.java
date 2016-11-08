package com.mideas.rpg.v2.game.guild;

import java.util.ArrayList;

public class Guild {

	private int id;
	private int leader_id;
	private String name;
	private String information;
	private String motd;
	private ArrayList<GuildMember> memberList;
	private ArrayList<GuildRank> rankList;
	
	public Guild(int id, int leader_id, String name, String information, String motd, ArrayList<GuildMember> memberList, ArrayList<GuildRank> rankList) {
		this.information = information;
		this.memberList = memberList;
		this.leader_id = leader_id;
		this.rankList = rankList;
		this.name = name;
		this.motd = motd;
		this.id = id;
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
	
	public int getLeaderId() {
		return this.leader_id;
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
	
	public String getMotd() {
		return this.motd;
	}
	
	public ArrayList<GuildMember> getMemberList() {
		return this.memberList;
	}
	
	public ArrayList<GuildRank> getRankList() {
		return this.rankList;
	}
	
	public void sortMemberByName() {
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
	
	public void sortMemberByRank() {
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
	
	public void sortMemberByLevel() {
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
	
	public void sortMemberByNote() {
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
}
