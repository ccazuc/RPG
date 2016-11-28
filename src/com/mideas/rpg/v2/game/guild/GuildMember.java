package com.mideas.rpg.v2.game.guild;

import org.newdawn.slick.Color;

import com.mideas.rpg.v2.game.ClassType;
import com.mideas.rpg.v2.game.Joueur;

public class GuildMember {

	private int id;
	private String name;
	private int level;
	private GuildRank rank;
	private boolean isOnline;
	private String note = "";
	private String officer_note = "";
	private ClassType classType;
	private String classTypeString;
	private String levelString;
	private Color color;
	private String informationString;
	private long lastLoginTimer;
	private String lastLoginTimerString;
	
	private final static long MS_IN_A_YEAR = 31536000000l;
	private final static long MS_IN_A_MONTH = 1036800000l;
	private final static long MS_IN_A_WEEK = 604800000l;
	private final static long MS_IN_A_DAY = 86400000l;
	private final static long MS_IN_AN_HOUR = 3600000l;
	private final static long MS_IN_A_MINUTE = 60000l;
	
	public GuildMember(int id, String name, int level, GuildRank rank, boolean isOnline, String note, String officer_note, ClassType classType, long lastLoginTimer) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.levelString = String.valueOf(this.level);
		this.rank = rank;
		this.isOnline = isOnline;
		this.note = note;
		this.officer_note = officer_note;
		this.classType = classType;
		this.color = Joueur.convClassTypeToColor(this.classType);
		this.classTypeString = Joueur.convClassTypeToString(this.classType);
		this.informationString = this.classTypeString+" level "+this.levelString;
		this.lastLoginTimer = lastLoginTimer;
		updateLastLoginTimerString();
	}
	
	public void updateLastLoginTimerString() {
		if(!this.isOnline) {
			long delta = System.currentTimeMillis()-this.lastLoginTimer;
			if(delta >= MS_IN_A_YEAR) {
				this.lastLoginTimerString = (delta/MS_IN_A_YEAR)+" year";
			}
			else if(delta >= MS_IN_A_MONTH) {
				this.lastLoginTimerString = (delta/MS_IN_A_MONTH)+" month";
			}
			else if(delta >= MS_IN_A_WEEK) {
				this.lastLoginTimerString = (delta/MS_IN_A_WEEK)+" week";
			}
			else if(delta >= MS_IN_A_DAY) {
				this.lastLoginTimerString = (delta/MS_IN_A_DAY)+" day";
			}
			else if(delta >= MS_IN_AN_HOUR) {
				this.lastLoginTimerString = (delta/MS_IN_AN_HOUR)+" hour";
			}
			else if(delta >= MS_IN_A_MINUTE) {
				this.lastLoginTimerString = (delta/MS_IN_A_MINUTE)+" minute";
			}
		}
		else {
			this.lastLoginTimerString = "Online";
		}
 	}
	
	public int getId() {
		return this.id;
	}
	
	public ClassType getClassType() {
		return this.classType;
	}
	
	public long getLastLoginTimer() {
		return this.lastLoginTimer;
	}
	
	public void setLastLoginTimer(long lastLoginTimer) {
		this.lastLoginTimer = lastLoginTimer;
	}
	
	public String getLastLoginTimerString() {
		return this.lastLoginTimerString;
	}
	
	public void setLastLoginTimerString(String lastLoginTimerString) {
		this.lastLoginTimerString = lastLoginTimerString;
	}
	
	public String getClassTypeString() {
		return this.classTypeString;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getInformationString() {
		return this.informationString;
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.levelString = String.valueOf(this.level);
		this.informationString = this.classTypeString+" niveau "+this.levelString;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getLevelString() {
		return this.levelString;
	}
	
	public void setRank(GuildRank rank) {
		this.rank = rank;
	}
	
	public GuildRank getRank() {
		return this.rank;
	}
	
	public void setOnlineStatus(boolean we) {
		this.isOnline = we;
	}
	
	public boolean isOnline() {
		return this.isOnline;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public void setOfficerNote(String officer_note) {
		this.officer_note = officer_note;
	}
	
	public String getOfficerNote() {
		return this.officer_note;
	}
}
