package com.mideas.rpg.v2.game.guild;

import java.util.ArrayList;
import java.util.Collections;

import com.mideas.rpg.v2.game.unit.ClassType;
import com.mideas.rpg.v2.hud.social.guild.GuildFrame;

public class Guild {

	private final int id;
	private int leader_id;
	private final String name;
	private String information;
	private String motd;
	private String tempMotd;
	private String numberMembers;
	private int numberOnlineMembers;
	private String numberOnlineMembersString;
	private final ArrayList<GuildMember> memberList;
	private final ArrayList<GuildRank> rankList;
	private final ArrayList<GuildEvent> eventList;
	
	public final static int MEMBER_NOTE_MAX_LENGTH = 50;
	public final static int MEMBER_OFFICER_NOTE_MAX_LENGTH = 50;
	public final static int LAST_ONLINE_TIMER_UPDATE_FREQUENCE = 60000;
	
	public Guild(int id, int leader_id, String name, String information, String motd, ArrayList<GuildMember> memberList, ArrayList<GuildRank> rankList) {
		this.information = information;
		this.memberList = memberList;
		this.leader_id = leader_id;
		this.rankList = rankList;
		this.name = name;
		this.motd = motd;
		this.tempMotd = motd;
		this.id = id;
		this.eventList = new ArrayList<GuildEvent>();
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
		this.memberList.add(new GuildMember(2, "A", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "D", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "E", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "f", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "z", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "al", 50, this.rankList.get(1), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "df", 50, this.rankList.get(2), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "Lf", 50, this.rankList.get(3), true, "", "", ClassType.ROGUE, 0));
		this.memberList.add(new GuildMember(2, "FS", 50, this.rankList.get(0), true, "", "", ClassType.ROGUE, 0));
		this.numberMembers = String.valueOf(this.memberList.size());
		initNumberOnlineMembers();
	}
	
	public ArrayList<GuildEvent> getGuildEventList() {
		return this.eventList;
	}
	
	public void addEvent(GuildEvent event) {
		this.eventList.add(event);
	}
	
	public void updateGuildTimer() {
		int i = 0;
		while(i < this.memberList.size()) {
			this.memberList.get(i).updateLastLoginTimerString();
			i++;
		}
		i = 0;
		while(i < this.eventList.size()) {
			this.eventList.get(i).updateTimerString();
			i++;
		}
	}
	
	public void updateMemberNote() {
		int i = 0;
		while(i < this.memberList.size()) {
			GuildMember member = this.memberList.get(i);
			member.setNoteDisplayed(GuildMember.updateNote(member.getNoteSave(), false));
			member.setOfficerNoteDisplayed(GuildMember.updateNote(member.getOfficerNoteSave(), true));
			i++;
		}
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
				if(this.memberList.get(i).isOnline()) {
					memberLoggedOut();
				}
				this.memberList.remove(i);
				break;
			}
			i++;
		}
		this.numberMembers = String.valueOf(this.memberList.size());
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
	
	public void setLeaderId(int id) {
		this.leader_id = id;
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
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getName().compareTo(this.memberList.get(j).getName()) >= 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByNameDescending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getName().compareTo(this.memberList.get(j).getName()) <= 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByRankAscending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getRank().getOrder() >= this.memberList.get(j).getRank().getOrder()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByRankDescending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getRank().getOrder() <= this.memberList.get(j).getRank().getOrder()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByLevelAscending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getLevel() >= this.memberList.get(j).getLevel()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByLevelDescending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getLevel() <= this.memberList.get(j).getLevel()) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByNoteAscending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getNoteDisplayed().compareTo(this.memberList.get(j).getNoteDisplayed()) >= 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByNoteDescending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if(this.memberList.get(i).getNoteSave().compareTo(this.memberList.get(j).getNoteSave()) <= 0) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByOnlineAscending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if((!this.memberList.get(i).isOnline() && this.memberList.get(j).isOnline()) || (!this.memberList.get(i).isOnline() && !this.memberList.get(j).isOnline() && this.memberList.get(i).getLastLoginTimer() >= this.memberList.get(j).getLastLoginTimer())) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
	
	public void sortMemberByOnlineDescending() {
		int i = -1;
		int j = -1;
		GuildMember temp;
		while(++i < this.memberList.size()) {
			j = i;
			while(++j < this.memberList.size()) {
				if((this.memberList.get(i).isOnline() && !this.memberList.get(j).isOnline()) || (!this.memberList.get(i).isOnline() && !this.memberList.get(j).isOnline() && this.memberList.get(i).getLastLoginTimer() <= this.memberList.get(j).getLastLoginTimer())) {
					temp = this.memberList.get(j);
					this.memberList.set(j, this.memberList.get(i));
					this.memberList.set(i, temp);
				}
			}
		}
	}
}
