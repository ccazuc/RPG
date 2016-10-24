package com.mideas.rpg.v2.game;

public class Party {

	private Unit[] partyList;
	private Unit leader;
	
	public Party(Unit leader, Unit member) {
		this.partyList = new Unit[4];
		this.partyList[0] = member;
		this.leader = leader;
	}
	
	public boolean isPartyLeader(Unit unit) {
		return this.leader.getId() == unit.getId();
	}
	
	public void setPartyLeader(Unit unit) {
		this.leader = unit;
	}
	
	public Unit getPartyMember(int i) {
		if(i >= 0 && i < this.partyList.length) {
			return this.partyList[i];
		}
		return null;
	}
	
	public void addMember(Unit unit) {
		int i = 0;
		while(i < this.partyList.length) {
			if(this.partyList[i] == null) {
				this.partyList[i] = unit;
				return;
			}
			i++;
		}
	}
	
	public void removeMember(int id) {
		int i = 0;
		while(i < this.partyList.length) {
			if(this.partyList[i] != null && this.partyList[i].getId() == id) {
				this.partyList[i] = null;
			}
			i++;
		}
	}
	
	public void updateMemberPosition() {
		int i = 0;
		int j = 0;
		while(i < this.partyList.length) {
			if(this.partyList[i] == null) {
				j = i;
				while(j < this.partyList.length) {
					if(j != i && this.partyList[j] != null) {
						this.partyList[i] = this.partyList[j];
						this.partyList[j] = null;
					}
					j++;
				}
			}
			i++;
		}
	}
	
	public Unit[] getMemberList() {
		return this.partyList;
	}
}
